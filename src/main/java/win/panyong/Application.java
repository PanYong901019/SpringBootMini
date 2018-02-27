package win.panyong;

import com.easyond.utils.DateUtil;
import com.easyond.utils.ObjectUtil;
import com.easyond.utils.StringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import win.panyong.model.User;
import win.panyong.utils.AppCache;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@ServletComponentScan
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@MapperScan("win.panyong.dao")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AppCache appCache() {
        return new AppCache();
    }
}

@WebListener
class ApplicationListener implements ServletContextListener {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    AppCache appCache;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("=================================================================ServletContex初始化");
        List<String> requestMappingList = new ArrayList<>();
        AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping = (AbstractHandlerMethodMapping<RequestMappingInfo>) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
        for (RequestMappingInfo requestMappingInfo : mapRet.keySet()) {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            requestMappingList.addAll(patterns);
        }
        appCache.setRequestMappingList(requestMappingList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("===================================================================ServletContex销毁");
    }
}

@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
class SessionFilter implements Filter {
    @Autowired
    AppCache appCache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> requestParameter = new HashMap<>();
        for (String s : parameterMap.keySet()) {
            requestParameter.put(s, parameterMap.get(s)[0]);
        }
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        String date = DateUtil.getDateString(new Date(), "【yyyy-MM-dd HH:mm:ss】");
        String[] whitelist = {"/apiError", "/webError", "/"};
        String[] api = {"/api/test", "/api/login"};
        String[] web = {"/logout"};
        String uri = request.getRequestURI().substring(request.getContextPath().length());
        if (uri.startsWith("/static")) {
            filterChain.doFilter(request, response);
        } else if (loginUser != null) {
            if (appCache.getRequestMappingList().contains(uri)) {
                System.out.println(date + "request：=====【" + loginUser.getName() + "】=====|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.mapToJsonString(requestParameter));
                filterChain.doFilter(request, response);
            } else {
                System.out.println(date + "request：=====【" + loginUser.getName() + "】=====|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.mapToJsonString(requestParameter));
                if (uri.startsWith("/api")) {
                    Map<String, Object> result = new LinkedHashMap<String, Object>() {{
                        put("rspCode", 0);
                        put("rspInfo", "Permission denied");
                    }};
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write(ObjectUtil.mapToJsonString(result));
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            }
        } else if (StringUtil.isHave(whitelist, uri)) {
            System.out.println(date + "request：=====whitelist=====|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.mapToJsonString(requestParameter));
            filterChain.doFilter(request, response);
        } else if (StringUtil.isHave(api, uri)) {
            System.out.println(date + "request：========api========|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.mapToJsonString(requestParameter));
            filterChain.doFilter(request, response);
        } else if (StringUtil.isHave(web, uri)) {
            System.out.println(date + "request：========web========|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.mapToJsonString(requestParameter));
            filterChain.doFilter(request, response);
        } else if (uri.equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
        } else {
            System.out.println(date + "request：======没有权限======|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.mapToJsonString(requestParameter));
            if (uri.startsWith("/api")) {
                Map<String, Object> result = new LinkedHashMap<String, Object>() {{
                    put("rspCode", 0);
                    put("rspInfo", "Permission denied");
                }};
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(ObjectUtil.mapToJsonString(result));
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

    @Override
    public void destroy() {

    }

}


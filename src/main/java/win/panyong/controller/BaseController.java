package win.panyong.controller;

import com.easyond.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;
import win.panyong.model.User;
import win.panyong.utils.AppCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class BaseController {
    protected static Integer FAIL = 0;
    protected static Integer OK = 1;

    protected Integer rspCode = FAIL;
    protected String rspInfo = "fail";
    protected LinkedHashMap<String, Object> rspResult = new LinkedHashMap<>();
    @Autowired
    protected AppCache appCache;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected HttpServletRequest request;

    protected String getParameter(String parameterKey) {
        return WebUtils.findParameterValue(request, parameterKey);
    }

    protected User getLoginUser() {
        return (User) session.getAttribute("loginUser");
    }

    protected Integer getLoginUserId() {
        return getLoginUser() == null ? null : getLoginUser().getId();
    }

    protected Boolean isLogin() {
        return getLoginUser() != null;
    }

    protected Map<String, Object> getResult() {
        Map<String, Object> result = new LinkedHashMap<String, Object>() {{
            put("rspCode", rspCode);
            put("rspInfo", rspInfo);
            put("rspResult", rspResult);
        }};
        return result;
    }

    protected String getResultJsonString() {
        Map<String, Object> result = new LinkedHashMap<String, Object>() {{
            put("rspCode", rspCode);
            put("rspInfo", rspInfo);
            put("rspResult", rspResult);
        }};
        return ObjectUtil.mapToJsonString(result);
    }
}

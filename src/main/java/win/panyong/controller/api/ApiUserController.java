package win.panyong.controller.api;

import com.easyond.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.panyong.controller.BaseController;
import win.panyong.model.User;
import win.panyong.service.UserService;
import win.panyong.utils.AppException;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ApiUserController extends BaseController {

    @Autowired
    protected Map<String, Object> appCache;
    @Autowired
    private UserService userServiceImpl;


    @RequestMapping(value = "login")
    String login() {
        String account = getParameter("account");
        String password = getParameter("password");
        if (StringUtil.invalid(account) || StringUtil.invalid(password)) {
            rspCode = FAIL;
            rspInfo = "The account ro password can not be empty";
        } else {
            try {
                User user = userServiceImpl.doLogin(account, password);
                session.setAttribute("loginUser", user);
                rspCode = OK;
                rspInfo = "success";
                rspResult.put("user", user);
            } catch (AppException e) {
                rspCode = e.getErrorCode();
                rspInfo = e.getMessage();
            }
        }
        return getResultJsonString();
    }

    @RequestMapping(value = "register")
    String register() {
        String account = getParameter("account");
        String password = getParameter("password");
        String name = getParameter("name");
        if (StringUtil.invalid(account) || StringUtil.invalid(password) || StringUtil.invalid(name)) {
            rspCode = 0;
            rspInfo = "The parameter can not be empty";
        } else {
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);
            user.setName(name);
            userServiceImpl.createUser(user);
            rspCode = OK;
            rspInfo = "用户创建成功";
            rspResult.put("user", user);
        }
        return getResultJsonString();
    }

    @RequestMapping(value = "changePassword")
    String changePassword() {
        String userId = getLoginUserId().toString();
        String oldPassword = getParameter("oldPassword");
        String newPassword = getParameter("newPassword");
        if (StringUtil.invalid(userId) || StringUtil.invalid(oldPassword) || StringUtil.invalid(newPassword)) {
            rspCode = FAIL;
            rspInfo = "The parameter can not be empty";
        } else {
            try {
                userServiceImpl.changePassword(userId, oldPassword, newPassword);
                rspCode = OK;
                rspInfo = "success";
            } catch (AppException e) {
                rspCode = e.getErrorCode();
                rspInfo = e.getMessage();
            }
        }
        return getResultJsonString();
    }
}

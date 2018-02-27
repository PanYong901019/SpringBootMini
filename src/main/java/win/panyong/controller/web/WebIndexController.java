package win.panyong.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import win.panyong.controller.BaseController;
import win.panyong.model.User;
import win.panyong.service.UserService;

import java.util.List;

@Controller
public class WebIndexController extends BaseController {
    @Autowired
    private UserService userServiceImpl;

    @RequestMapping(value = {"/", "/index", "/loginPage"})
    ModelAndView index() {
        if (isLogin()) {
            ModelAndView modelAndView = new ModelAndView("main");
            modelAndView.addObject("loginUser", getLoginUser());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("index");
            return modelAndView;
        }
    }

    @RequestMapping("/logout")
    ModelAndView logout() {
        session.invalidate();
        ModelAndView modelAndView = new ModelAndView("redirect:/index");
        return modelAndView;
    }

    @RequestMapping("/userManagment")
    ModelAndView userManagmentPage() {
        ModelAndView modelAndView = new ModelAndView("userManagment");
        List<User> userList = userServiceImpl.getAllUser();
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }

}

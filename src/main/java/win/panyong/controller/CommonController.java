package win.panyong.controller;

import com.easyond.utils.ObjectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController extends BaseController {


    @ResponseBody
    @RequestMapping(value = "/apiError")
    String error() {
        String type = getParameter("type");
        switch (type) {
            case "1":
                rspInfo = "没有权限";
                break;
            case "2":
                rspInfo = "访问异常";
                break;
        }
        return ObjectUtil.objectToJsonString(getResult());
    }

    @RequestMapping(value = "/webError")
    String errorPage() {
        return "errorPage";
    }
}

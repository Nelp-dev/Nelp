package us.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jihun on 2017. 2. 24..
 */

@Controller
public class HomeController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String hello() {
        return "home";
    }
}

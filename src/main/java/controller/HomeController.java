package main.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jihun on 2017. 2. 14..
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {

        return "home";
    }
}

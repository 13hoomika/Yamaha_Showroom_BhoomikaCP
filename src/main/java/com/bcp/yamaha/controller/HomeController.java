package com.bcp.yamaha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc

public class HomeController {
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")  // or @RequestMapping(method = RequestMethod.GET, value = "/home")
    public String home() {
        return "home"; // name of your view template
    }
}

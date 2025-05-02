package com.bcp.yamaha.controller;

import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {
    public UserController() {
        System.out.println("user controller started");
    }

    @Autowired
    private UserService userService;

    @Autowired
    ShowroomService showroomService;

}

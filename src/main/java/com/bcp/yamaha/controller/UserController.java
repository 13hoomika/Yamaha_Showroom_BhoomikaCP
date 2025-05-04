package com.bcp.yamaha.controller;

import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    public UserController() {
        System.out.println("user controller started");
    }

    @Autowired
    private UserService userService;

    @Autowired
    ShowroomService showroomService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/user-login";
    }

    @PostMapping("/loginWithOtp")
    public String loginWithOtp(@RequestParam("userEmail") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        System.out.println("===== controller: loginWithOtp() ======");
        Boolean returnedValue = userService.validateAndLogIn(email, password);
        if (!returnedValue) {
            return "user/login";
        }
        return "user/user-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out.");
        return "redirect:/";
    }

}

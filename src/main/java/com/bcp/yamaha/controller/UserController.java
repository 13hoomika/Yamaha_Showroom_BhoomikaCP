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
    public String loginWithOtp(
            @RequestParam("userEmail") String email,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {

        System.out.println("===== controller: loginWithOtp() ======");
        Boolean isValid = userService.validateAndLogIn(email, password);
        if (!isValid) {
            model.addAttribute("error", "Invalid email or password. Please try again.");
            return "user/user-login";
        }

        // You can store the user in session if needed
        session.setAttribute("userEmail", email);

        return "user/user-dashboard";
    }


    @GetMapping("/resetPassword")
    public String showResetPasswordForm() {
        return "user/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(
            @RequestParam("userEmail") String email,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword,
            RedirectAttributes redirectAttributes) {
        try {
            // Validate if new password and confirm password match
            if (!newPassword.equals(confirmNewPassword)) {
                redirectAttributes.addFlashAttribute("error", "New password and confirm password do not match.");
                return "redirect:/user/resetPassword";
            }

            // Update the password in the database
            boolean isUpdated = userService.resetPassword(email, newPassword);
            if (isUpdated) {
                redirectAttributes.addFlashAttribute("message", "Password reset successful! You can now log in with your new password");
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to update password, Invalid Password Must be at least 8 characters long, include one uppercase letter, one lowercase letter, one digit, and one special character.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred: " + e.getMessage());
        }
        return "redirect:/user/resetPassword";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out.");
        return "redirect:/";
    }

}

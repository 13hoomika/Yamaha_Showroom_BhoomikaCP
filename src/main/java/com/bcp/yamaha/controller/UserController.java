package com.bcp.yamaha.controller;

import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.service.bike.BikeService;
import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

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

    @Autowired
    BikeService bikeService;

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

        UserDto userDto = userService.getUserByEmail(email);
        if (userDto == null) {
            model.addAttribute("error", "User not found for email: " + email);
            log.warn("User not found for email: {}", email);
            return "user/user-login";
        }
        // Store user in session
        session.setAttribute("loggedInUser", userDto);
        log.info("User signed in successfully: {}", email);

        // Redirect to dashboard
        return "redirect:/user/dashboard";
    }

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("error", "Session expired. Please log in again.");
            return "user/user-login";
        }

        String email = (String) session.getAttribute("userEmail");

        /*if (email == null) {
            model.addAttribute("error", "Session expired. Please log in again.");
            return "user/user-login";
        }*/

        // Optionally load user details for the dashboard
        UserDto user = userService.getUserByEmail(email);
        model.addAttribute("user", user);

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

    @GetMapping("/showrooms")
    public String viewAllShowrooms(Model model) {
        model.addAttribute("showroomList", showroomService.getAllShowroom());
        return "user/view-showrooms";
    }

    @GetMapping("/bikes")
    public String viewAllBikes(Model model) {
        model.addAttribute("bikeList", bikeService.getAllBikes());
        return "user/view-bikes";
    }

    @GetMapping("bikeImage")
    public void downloadBikeImage(HttpServletResponse response, @RequestParam String imageName) {
        response.setContentType("image/jpg");
        File file = new File("D:\\06 GO19ROM Aug19\\Project Phase\\BikeShowroom Project draft\\Yamaha_Showroom_BhoomikaCP\\src\\main\\webapp\\static\\images\\bike-images\\" + imageName);
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            response.flushBuffer();
        } catch (IOException e) {
            log.error("IOException{}", e.getMessage());
        }
    }

    /*@GetMapping("/updateProfile")
    public String updateProfile(UserDto uerDto,HttpSession session, RedirectAttributes redirectAttributes) {
        UserDto existingUser = userService.getUserByEmail(uerDto.getUserEmail());
        if (existingUser == null) {
            redirectAttributes.addAttribute("error", "User not found!");
            System.out.println("No user found: " + null);
        }else {
            System.out.println("-----> user found: " + existingUser);
        }
        boolean userUpdated = userService.updateUserData(uerDto);
        if (userUpdated) {
            redirectAttributes.addAttribute("success", "User details updated successfully");
            session.setAttribute("user", uerDto);
            System.out.println("-----> user updated for: " + existingUser);
        } else {
            redirectAttributes.addAttribute("error", "User details update failed");
        }
        return "user/update-profile";
    }*/

    @GetMapping("getProfile")
    public String getProfile(Model model, HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("error", "No user is logged in. Please sign in.");
            return "user/update-profile";
        }

        model.addAttribute("profile", loggedInUser);
        log.info("Returning profile data for user: {}", loggedInUser.getUserEmail());
        return "user/update-profile"; // Redirect to profile update page
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("userDto") UserDto userDto,
                                RedirectAttributes redirectAttributes,HttpSession session) {

        boolean updated = userService.updateProfile(userDto);
        if (updated) {
            redirectAttributes.addAttribute("success", "Updated user profile successfully");
            session.setAttribute("loggedInUser", userDto);
            System.out.println("isUpdated in controller: " + true);
            return "user/user-dashboard";
        }else {
            redirectAttributes.addAttribute("error", "Failed to update profile. Please try again.");
            System.out.println("isUpdated in controller: " + false);
            return "user/update-profile";
        }

    }



    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out.");
        return "redirect:/";
    }

}

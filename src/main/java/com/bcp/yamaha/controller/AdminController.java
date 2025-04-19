package com.bcp.yamaha.controller;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.constants.ShowroomEnum;
import com.bcp.yamaha.dto.BikeDto;
import com.bcp.yamaha.dto.FollowUpDto;
import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.entity.AdminEntity;
import com.bcp.yamaha.entity.BikeEntity;
import com.bcp.yamaha.service.admin.AdminService;
import com.bcp.yamaha.service.bike.BikeService;
import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.service.user.FollowUpService;
import com.bcp.yamaha.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ShowroomService showroomService;

    @Autowired
    private BikeService bikeService;

    @Autowired
    private UserService userService;

    @Autowired
    FollowUpService followUpService;

    @PostConstruct
    public void setupAdmin() {
        adminService.initializeAdmin(); // runs once when the server starts
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "redirect:/";
    }

    // Step 1: Handle login form submit (admin name only)
    @PostMapping("/login")
    public String handleLogin(@RequestParam String adminName,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        AdminEntity adminEntity = adminService.findByName(adminName);

        if (adminEntity == null || !adminEntity.getAdminName().equals(adminName)) {
            redirectAttributes.addFlashAttribute("adminLoginError", "Admin not found. Please check your credentials.");
            redirectAttributes.addFlashAttribute("showAdminModal", true);
            redirectAttributes.addFlashAttribute("adminName", adminName);
            return "redirect:/?adminLoginError=Admin+not+found&showAdminModal=true&adminName=" + adminName;
        }

        boolean otpSent = adminService.sendOtpToAdmin(adminEntity.getAdminEmail());

        if (otpSent) {
            session.setAttribute("adminEmail", adminEntity.getAdminEmail());
            return "redirect:/admin/otp";
        } else {
            redirectAttributes.addFlashAttribute("adminLoginError", "Failed to send OTP. Please try again.");
            return "redirect:/";
        }
    }

    @GetMapping("/otp")
    public String showOtpForm(HttpSession session, Model model) {
        model.addAttribute("otpRequestTime", LocalDateTime.now());
        return "admin/admin-otp";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String otp,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        String adminEmail = (String) session.getAttribute("adminEmail");

        if (adminEmail == null) {
            redirectAttributes.addFlashAttribute("otpError", "Session expired. Please login again.");
            return "redirect:/";
        }

        boolean isValid = adminService.validateAdminOtp(otp);

        if (isValid) {
            AdminEntity admin = adminService.findByEmail(adminEmail);
            session.setAttribute("loggedInAdminId", admin.getAdminId());
            redirectAttributes.addFlashAttribute("adminName", admin.getAdminName());
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("otpError", "Invalid or expired OTP. Please try again.");
            return "redirect:/admin/otp";
        }
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Integer adminId = (Integer) session.getAttribute("loggedInAdminId");
        if (adminId == null) {
            return "redirect:/admin/login";
        }

        List<ShowroomEnum> showroomEnumList = Arrays.asList(ShowroomEnum.values());
        model.addAttribute("showroomList", showroomEnumList);

        List<ShowroomDto> showroomList = showroomService.getAllShowroom();
        model.addAttribute("showroomLocationList", showroomList);

        model.addAttribute("bikeCount", bikeService.getTotalBikeCount());
        model.addAttribute("showroomCount", showroomService.getTotalShowroomCount());
        model.addAttribute("usersCount", userService.getTotalUserCount());
        model.addAttribute("admin", adminService.findById(adminId));

        return "admin/dashboard";
    }

    // Bike Management
    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("bikeTypes", Arrays.asList(BikeType.values()));

        return "admin/add-bike";
    }

    @PostMapping("/add-bike")
    public String addBike(@ModelAttribute BikeDto bikeDto,
                          RedirectAttributes redirectAttributes,
                          HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        if (bikeDto.getBikeImages() == null || bikeDto.getBikeImages().isEmpty() || bikeDto.getBikeImages().size() > 5) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please upload between 1 and 5 images.");
            return "redirect:/admin/add-bike";
        }

        boolean isAdded = bikeService.addBike(bikeDto);
        if (isAdded) {
            redirectAttributes.addFlashAttribute("successMessage", "Bike added successfully!");
            return "redirect:/admin/manage-bikes";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong while adding the bike.");
            return "redirect:/admin/add-bike";
        }
    }

    @GetMapping("/manage-bikes")
    public String manageBikes(@RequestParam(required = false) String showroomLocation,
                              Model model,
                              HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("allShowroomLocations", Arrays.asList(ShowroomEnum.values()));

        // Get bikes filtered by showroom if specified
        List<BikeDto> bikes;
        if (showroomLocation != null && !showroomLocation.isEmpty()) {
            ShowroomEnum locationEnum = ShowroomEnum.valueOf(showroomLocation);
            bikes = bikeService.getBikesByShowroomLocation(locationEnum);
            model.addAttribute("selectedShowroomId", showroomLocation);
        } else {
            bikes = bikeService.getAllBikes();
        }
        model.addAttribute("bikeList", bikes);

        return "admin/manage-bikes";
    }

    @GetMapping("/assign-bikes")
    public String showAssignmentForm(Model model) {
        List<BikeEntity> unassignedBikes = bikeService.getUnassignedBikes();
        List<ShowroomDto> showrooms = showroomService.getAllShowroom();

        model.addAttribute("unassignedBikes", unassignedBikes);
        model.addAttribute("showrooms", showrooms);

        return "admin/assign-bikes";
    }

    @PostMapping("/assign-bike")
    public String assignBikeToShowroom( @RequestParam Integer bikeId,
                                        @RequestParam Integer showroomId,
                                       RedirectAttributes redirectAttributes) {
        try {
            boolean isAssigned =  bikeService.assignBikeToShowroom(bikeId,showroomId);
            if (isAssigned){
                redirectAttributes.addFlashAttribute("success", "Bike assigned successfully!");
            }else {
                redirectAttributes.addFlashAttribute("error", "Failed to assign bike. Showroom may be at full capacity.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/assign-bikes";
    }



    // Showroom Management
    @GetMapping("/add-showroom")
    public String showAddShowroomForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("showroomList", Arrays.asList(ShowroomEnum.values()));
        model.addAttribute("showroomDto", new ShowroomDto());

        return "admin/add-showroom";
    }

    @PostMapping("/add-showroom")
    public String addShowroom(@ModelAttribute ShowroomDto showroomDto,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        boolean isAdded = showroomService.addShowroom(showroomDto);
        if (isAdded) {
            redirectAttributes.addFlashAttribute("showroomSuccessMessage", "Showroom added successfully!");
            return "redirect:/admin/manage-showrooms";
        } else {
            redirectAttributes.addFlashAttribute("showroomErrorMessage", "Something went wrong while adding the showroom.");
            return "redirect:/admin/add-showroom";
        }
    }

    @GetMapping("/manage-showrooms")
    public String manageShowrooms(Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("activePage", "showrooms");
        model.addAttribute("showroomLocationList", showroomService.getAllShowroom());
        return "admin/manage-showrooms";
    }

    // User Management
    @GetMapping("/manage-users")
    public String manageUsers(Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("users", userService.getAllUsers());
        return "admin/manage-users";
    }

    // Show follow-up page for a specific user
    @GetMapping("/followup-user")
    public String showFollowUpPage(@RequestParam("id") int userId, Model model) {
        // Get user details
        UserDto user = userService.getUserById(userId);
        model.addAttribute("user", user);

        // Get existing follow-up logs
        List<FollowUpDto> logs = followUpService.getFollowUpLogsByUserId(userId);
        model.addAttribute("logs", logs);

        return "admin/followup-user";
    }

    @PostMapping("/followup-user")
    public String addFollowUpLog(
            @RequestParam("id") int userId,
            @RequestParam String callStatus,
            @RequestParam String notes,
//            @RequestParam LocalDateTime rescheduleDate,
            RedirectAttributes redirectAttributes) {

        FollowUpDto followUpLogDto = new FollowUpDto();
        followUpLogDto.setUserId(userId);
        followUpLogDto.setFollowupDate(LocalDateTime.now());
        followUpLogDto.setCallStatus(callStatus);
        followUpLogDto.setNotes(notes);
//        followUpLogDto.setRescheduleDate(rescheduleDate);

        boolean saveFollowUp = followUpService.saveFollowUp(followUpLogDto);
        if (saveFollowUp){
            redirectAttributes.addFlashAttribute("success", "Follow-up saved successfully!");
        }else {
            redirectAttributes.addFlashAttribute("error", "Failed to save Follow-up details!!");
        }

        return "redirect:/admin/followup-user?id=" + userId;
    }

    @GetMapping("/manage-followup")
    public String manageFollowup(Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("allFollowUps", followUpService.getAllFollowups());
        return "admin/manage-followup";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login?logout";
    }
}
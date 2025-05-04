package com.bcp.yamaha.controller;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.constants.ScheduleType;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@Slf4j
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

    // ========== ADMIN LOGIN & OTP ==========
    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/admin-login";
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    @PostMapping("/sendOtp")
    public String sendOtp(
            @RequestParam("adminEmail") String email,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        // Validate email format
        if (!isValidEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Invalid email format");
            return "redirect:/admin/login";
        }

        Optional<AdminEntity> admin = adminService.findByEmail(email);
        if (!admin.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email not registered");
            return "redirect:/admin/login";
        }

        try {
            boolean otpSent = adminService.sendOtpToAdmin(email);
            if (otpSent) {
                session.setAttribute("otpSent", true);
                session.setAttribute("otpStartTime", System.currentTimeMillis());
                session.setAttribute("adminEmail", email);
                redirectAttributes.addFlashAttribute("message", "OTP sent successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to send OTP");
            }
        } catch (Exception e) {
            log.error("OTP sending failed: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "System error. Please try again.");
        }
        return "redirect:/admin/login";
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(
            @RequestParam("otp") String otp,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        // Validate OTP format
        if (otp == null || !otp.matches("\\d{6}")) {
            redirectAttributes.addFlashAttribute("error", "Invalid OTP format");
            return "redirect:/admin/login";
        }

        String email = (String) session.getAttribute("adminEmail");
        Long otpStartTime = (Long) session.getAttribute("otpStartTime");

        if (email == null || otpStartTime == null) {
            redirectAttributes.addFlashAttribute("error", "Session expired! Please try again.");
            return "redirect:/admin/login";
        }

        // Calculate elapsed time (in milliseconds)
        long elapsed = System.currentTimeMillis() - otpStartTime;
        // Check if OTP has expired (2 minutes limit)
        if (elapsed > 2 * 60 * 1000) { // 2 minutes
            session.removeAttribute("otpSent");
            redirectAttributes.addFlashAttribute("error", "OTP expired. Please request a new one.");
            return "redirect:/admin/login";
        }

        try {
            if (adminService.verifyOtp(email, otp)) {
                AdminEntity admin = adminService.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Admin not found"));
                session.setAttribute("loggedInAdminId", admin.getAdminId());
                session.setAttribute("adminName", admin.getAdminName());
                return "redirect:/admin/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid OTP");
            }
        } catch (Exception e) {
            log.error("OTP verification failed: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Verification failed");
        }

        return "redirect:/admin/login";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Integer adminId = (Integer) session.getAttribute("loggedInAdminId");
        if (adminId == null) return "redirect:/";

        model.addAttribute("showroomLocationList", showroomService.getAllShowroom());
        model.addAttribute("bikeCount", bikeService.getTotalBikeCount());
        model.addAttribute("showroomCount", showroomService.getTotalShowroomCount());
        model.addAttribute("usersCount", userService.getTotalUserCount());
        model.addAttribute("admin", adminService.findById(adminId));

        return "admin/dashboard";
    }


    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInAdminId") == null;
    }

    // ========== USER REGISTER ==========
    @GetMapping("/userRegister")
    public String showUserRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());

        List<BikeType> bikeTypeList = Arrays.asList(BikeType.values());
        model.addAttribute("bikeTypes", bikeTypeList);

        List<ScheduleType> scheduleTypeList = Arrays.asList(ScheduleType.values());
        model.addAttribute("scheduleTypeList", scheduleTypeList);

        model.addAttribute("showrooms", showroomService.getAllShowroom());

        return "admin/userRegister"; // JSP page name
    }

    @PostMapping("/registerUser")//form action
    public String processUserRegister( @Valid
                                       @ModelAttribute("userDto") UserDto userDto,
                                       RedirectAttributes redirectAttributes) {
        // Save user to database
        try {
            userService.registerUser(userDto);
            System.out.println("User added = "+ userDto);
            redirectAttributes.addFlashAttribute("successMessage", "User registered successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong!!");
            throw new RuntimeException(e);

        }
        return "redirect:/admin/userRegister";
    }

    // ========== BIKE MANAGEMENT ==========
    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model, HttpSession session) {
        /*if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }*/
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("bikeTypes", Arrays.asList(BikeType.values()));
        return "admin/add-bike";
    }

    @PostMapping("/add-bike")
    public String addBike(
            @ModelAttribute BikeDto bikeDto,
            @RequestParam("frontImage") MultipartFile frontImage,
            @RequestParam("backImage") MultipartFile backImage,
            @RequestParam("leftImage") MultipartFile leftImage,
            @RequestParam("rightImage") MultipartFile rightImage,
            RedirectAttributes redirectAttributes) {

        try {
            // Set images to DTO
            bikeDto.setFrontImage(frontImage);
            bikeDto.setBackImage(backImage);
            bikeDto.setLeftImage(leftImage);
            bikeDto.setRightImage(rightImage);

            // Add bike
            bikeService.addBike(bikeDto);

            redirectAttributes.addFlashAttribute("success", "Bike added successfully!");
            return "redirect:/admin/manage-bikes";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add bike: " + e.getMessage());
            return "redirect:/admin/add-bike";
        }
    }


    @GetMapping("/manage-bikes")
    public String manageBikes(@RequestParam(required = false) String bikeType,
                              Model model,
                              HttpSession session) {
        /*if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }*/
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }

        model.addAttribute("allBikeTypes", Arrays.asList(BikeType.values()));

        // Get bikes filtered by showroom if specified
        List<BikeDto> bikes;
        if (bikeType != null && !bikeType.isEmpty()) {
            BikeType type = BikeType.valueOf(bikeType);
            bikes = bikeService.getBikesByBikeType(type);
            model.addAttribute("selectedBikeType", bikeType);
        } else {
            bikes = bikeService.getAllBikes();
        }
        model.addAttribute("bikeList", bikes);

        return "admin/manage-bikes";
    }

    @GetMapping("/view-allBikes")
    public String viewAllBikes(@RequestParam(required = false) String bikeType,
                              Model model,
                              HttpSession session) {
        /*if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }*/
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }

        model.addAttribute("allBikeTypes", Arrays.asList(BikeType.values()));

        // Get bikes filtered by showroom if specified
        List<BikeDto> bikes;
        if (bikeType != null && !bikeType.isEmpty()) {
            BikeType type = BikeType.valueOf(bikeType);
            bikes = bikeService.getBikesByBikeType(type);
            model.addAttribute("selectedBikeType", bikeType);
        } else {
            bikes = bikeService.getAllBikes();
        }
        model.addAttribute("bikeList", bikes);

        return "admin/view-allBikes";
    }
//    String UPLOAD_FOLDER = "D:\\06 GO19ROM Aug19\\Project Phase\\BikeShowroom Project draft\\Yamaha_Showroom_BhoomikaCP- upload and download multiple images\\src\\main\\webapp\\static\\upload\\";
private static final String UPLOAD_FOLDER = "src/main/webapp/static/upload/";

    @GetMapping({
            "/bikes/image/{fileName:.+}",
            "/bikes/image/uploads/{fileName:.+}"
    })
    public void getBikeImage(
            @PathVariable String fileName,
            HttpServletResponse response) throws IOException {

        // Security check
        if (fileName.contains("..")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid file path");
            return;
        }

        // Handle both cases:
        // 1. Direct filenames (Yamaha_FZ_S_Hybrid_1746256317935.webp)
        // 2. Files from uploads folder (uploads/Yamaha_Ray_ZR_125_1744690143109.webp)
        String actualFileName = fileName.startsWith("uploads/")
                ? fileName.substring("uploads/".length())
                : fileName;

        Path filePath = Paths.get(UPLOAD_FOLDER, actualFileName);

        if (!Files.exists(filePath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Set proper content type
        String contentType = Files.probeContentType(filePath);
        response.setContentType(contentType != null ? contentType : "application/octet-stream");

        // Add caching headers
        response.setHeader("Cache-Control", "public, max-age=31536000");

        // Serve the file
        Files.copy(filePath, response.getOutputStream());
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



    // ========== SHOWROOM MANAGEMENT ==========
    @GetMapping("/add-showroom")
    public String showAddShowroomForm(Model model, HttpSession session) {
        /*if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }*/
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }

        model.addAttribute("showroomDto", new ShowroomDto());

        return "admin/add-showroom";
    }

    @PostMapping("/add-showroom")
    public String addShowroom(@ModelAttribute ShowroomDto showroomDto,
                              RedirectAttributes redirectAttributes,
                              HttpSession session,
                              @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {

        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }

//        String uploadDir = "src" + File.separator + "main" + File.separator + "webapp" +
//                File.separator + "static" + File.separator + "images" +
//                File.separator + "showroom-Images" + File.separator;

        String uploadDir = "D:\\06 GO19ROM Aug19\\Project Phase\\BikeShowroom Project draft\\Yamaha_Showroom_BhoomikaCP\\src\\main\\webapp\\static\\images\\showroom-Images\\";


        Files.createDirectories(Paths.get(uploadDir)); // Ensure upload dir exists

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();

            if (originalFilename != null && originalFilename.contains(".")) {
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = showroomDto.getShowroomName().replaceAll("[^a-zA-Z0-9]", "_") +
                        "_" + System.currentTimeMillis() + extension;
                Path path = Paths.get(uploadDir + newFileName);
                Files.write(path, multipartFile.getBytes());
                showroomDto.setShowroomImg(newFileName);
                System.out.println("✔️ Image uploaded: " + newFileName);
            } else {
                System.out.println("❌ Invalid image file.");
            }
        } else {
            System.out.println("❌ Image not provided.");
        }

        boolean isAdded = showroomService.addShowroom(showroomDto);
        if (isAdded) {
            System.out.println("added showroom dto: " + showroomDto);
            redirectAttributes.addFlashAttribute("showroomSuccessMessage", "Showroom added successfully!");
            return "redirect:/admin/manage-showrooms";
        } else {
            redirectAttributes.addFlashAttribute("showroomErrorMessage", "Something went wrong while adding the showroom.");
            return "redirect:/admin/add-showroom";
        }
    }


    @GetMapping("/manage-showrooms")
    public String manageShowrooms(Model model, HttpSession session) {
        /*if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }*/
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }

        model.addAttribute("activePage", "showrooms");
        model.addAttribute("showroomsList", showroomService.getAllShowroom());
        return "admin/manage-showrooms";
    }

    @GetMapping("/view-showrooms")
    public String viewAllShowrooms(Model model, HttpSession session) {
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        List<ShowroomDto> showroomList = showroomService.getAllShowroom();
        System.out.println("All showroom list: " + showroomList); // Just once
        model.addAttribute("showroomList", showroomList);
        return "admin/view-allShowroom";
    }

    // ========== USER MANAGEMENT ==========
    @GetMapping("/manage-users")
    public String manageUsers(@RequestParam(value = "scheduleType",required = false) String scheduleType, Model model, HttpSession session) {
        /*if (session.getAttribute("loggedInAdminId") == null) {
            return "redirect:/admin/login";
        }*/
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }

        model.addAttribute("SelectScheduleType", Arrays.asList(ScheduleType.values()));
        // Get user filtered by scheduleType if specified
        List<UserDto> users;
        if (scheduleType != null && !scheduleType.isEmpty()) {
            ScheduleType scheduleTypeEnum = ScheduleType.valueOf(scheduleType);
            users = userService.getUsersByScheduleType(scheduleTypeEnum);
            model.addAttribute("selectedSchedule", scheduleType);
        } else {
            users = userService.getAllUsers();
        }
        model.addAttribute("usersList", users);

        model.addAttribute("users", userService.getAllUsers());
        return "admin/manage-users";
    }

    // ========== FOLLOW-UP ==========
    // Show follow-up page for a specific user
    @GetMapping("/followup-user")
    public String showFollowUpPage(@RequestParam("id") int userId, Model model) {
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
        followUpLogDto.setFollowupDate(LocalDate.now());
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

    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes redirectAttributes) {
        session.invalidate();
//        return "redirect:/admin/login?logout";
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out.");
        return "redirect:/";

    }
}
package com.bcp.yamaha.controller;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.*;
import com.bcp.yamaha.service.admin.AdminService;
import com.bcp.yamaha.service.bike.BikeService;
import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.service.followup.FollowUpService;
import com.bcp.yamaha.service.user.UserService;
import com.bcp.yamaha.util.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Autowired
    ServletContext servletContext;

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

        Optional<AdminDto> admin = adminService.findByEmail(email);
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
                redirectAttributes.addFlashAttribute("success", "OTP sent successfully!");
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
                AdminDto admin = adminService.findByEmail(email)
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
    public String processUserRegister(
            @ModelAttribute UserDto userDto,
            RedirectAttributes redirectAttributes) {

        /*// capitalizeWords user name
        if (userDto.getUserName() != null) {
            String formattedUserName = FormatUtil.capitalizeWords(userDto.getUserName());
            userDto.setUserName(formattedUserName);
        }*/

        Optional.ofNullable(userDto.getUserName())
                .map(FormatUtil::capitalizeWords)
                .ifPresent(userDto::setUserName);

        try {
            UserDto savedUser = userService.registerUser(userDto);
            System.out.println("User added = "+ userDto);

            FollowUpDto followUpDto = new FollowUpDto();
            followUpDto.setUserId(savedUser.getUserId());
            followUpDto.setCallStatus("Registered");
            followUpDto.setNotes("User Registered");
            followUpDto.setFollowupDate(LocalDate.now());
            followUpService.saveFollowUp(followUpDto);

            redirectAttributes.addFlashAttribute("success", "Registration successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed");
            log.error("Registration error", e);
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

    /*@PostMapping("/add-bike")
    public String addBike(
            @ModelAttribute BikeDto bikeDto,
            HttpSession session,
            HttpServletRequest request) {

        List<MultipartFile> multipartFile = bikeDto.getMultipartFileList();
        List<String> images = new ArrayList<>();
        multipartFile.forEach(System.out::println);

        // Get the real path to the webapp's static/images/bike-images directory
        String uploadDir = request.getServletContext().getRealPath("/static/images/bike-images/");

        try {
            Files.createDirectories(Paths.get(uploadDir)); // Ensure directory exists
        } catch (IOException e) {
            log.error("Could not create upload directory", e);
        }

        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                images.add(originalFilename);
                Path path = Paths.get(uploadDir + originalFilename);
                try {
                    Files.write(path, file.getBytes());
                } catch (IOException e) {
                    log.error("Error adding image: {}", e.getMessage(), e);
                }
            }
        }
            bikeDto.setImages(images);

            String formattedModel = FormatUtil.capitalizeWords(bikeDto.getBikeModel());
            bikeDto.setBikeModel(formattedModel);

            try {
                bikeService.addBike(bikeDto);
                session.setAttribute("success", "Bike added successfully!");
                return "redirect:/admin/manage-bikes";
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Failed to save bike: " + e.getMessage());
            return "redirect:/admin/add-bike";
        }
    }*/
    @PostMapping("/add-bike")
    public String addBike(
            @ModelAttribute BikeDto bikeDto,
            HttpSession session) {

        List<MultipartFile> multipartFile = bikeDto.getMultipartFileList();
        List<String> images = new ArrayList<>();

        // Get the real path using ServletContext
        String uploadDir = servletContext.getRealPath("/static/images/bike-images/");

        try {
            Files.createDirectories(Paths.get(uploadDir)); // Ensure directory exists
        } catch (IOException e) {
            log.error("Could not create upload directory", e);
        }

        // Get current date for filename
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDate = LocalDate.now().format(dateFormatter);

        // Clean model name for filename (remove special chars, replace spaces)
        String cleanModelName = bikeDto.getBikeModel()
                .replaceAll("[^a-zA-Z0-9]", " ") // Replace special chars with space
                .trim()
                .replaceAll(" +", "_"); // Replace multiple spaces with single underscore

        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();

                // Extract file extension
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }

                // Generate new filename: model_date_originalName
                String newFilename = String.format("%s_%s_%s%s",
                        cleanModelName,
                        currentDate,
                        // Keep original name but remove extension
                        originalFilename != null ? originalFilename.replace(extension, "") : "image",
                        extension);

                images.add(newFilename);
                Path path = Paths.get(uploadDir + newFilename);

                try {
                    Files.write(path, file.getBytes());
                    log.info("Saved image: {}", newFilename);
                } catch (IOException e) {
                    log.error("Error saving image: {}", e.getMessage(), e);
                }
            }
        }

        bikeDto.setImages(images);
        String formattedModel = FormatUtil.capitalizeWords(bikeDto.getBikeModel());
        bikeDto.setBikeModel(formattedModel);

        try {
            bikeService.addBike(bikeDto);
            session.setAttribute("success", "Bike added successfully!");
            return "redirect:/admin/manage-bikes";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Failed to save bike: " + e.getMessage());
            return "redirect:/admin/add-bike";
        }
    }

    @GetMapping("bikeImage")
    public void downloadBikeImage(@RequestParam String imageName, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("image/jpg");
        // Get the real path to the image
        String imagePath = request.getServletContext().getRealPath("/static/images/bike-images/" + imageName);
        File file = new File(imagePath);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             ServletOutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
            response.flushBuffer();
        } catch (IOException e) {
            log.error("Error saving image: {}", e.getMessage(), e);
        }
    }

    @GetMapping("/view-allBikes")
    public String viewAllBikes(@RequestParam(required = false) String bikeType,
                               Model model,
                               HttpSession session) {

        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }

        // Get bikes filtered by type if specified
        List<BikeDto> bikeDtoList;

        if (bikeType != null && !bikeType.isEmpty()) {
            BikeType type = BikeType.valueOf(bikeType);
            bikeDtoList = bikeService.getBikesByBikeType(type);
            model.addAttribute("selectedBikeType", bikeType);
        } else {
            bikeDtoList = bikeService.getAllBikes();
        }
        // Debug output
        System.out.println("Bikes retrieved: " + bikeDtoList);

        // Add attributes to model
        model.addAttribute("allBikeTypes", Arrays.asList(BikeType.values()));
        model.addAttribute("bikeList", bikeDtoList);

        return "admin/view-allBikes";
    }

    @GetMapping("/manage-bikes")
    public String manageBikes(@RequestParam(required = false) String bikeType,
                              Model model,
                              HttpSession session) {
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

    @GetMapping("/assign-bikes")
    public String showAssignmentForm(Model model, HttpSession session) {
        if (isAdminLoggedIn(session)) return "redirect:/admin/login";
        
        List<BikeDto> unassignedBikes = bikeService.getUnassignedBikes();
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

        // Get the real path to upload directory
        String uploadDir = session.getServletContext().getRealPath("/static/images/showroom-Images/");
        Files.createDirectories(Paths.get(uploadDir)); // Ensure upload dir exists

        // Format for filename date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String currentDateTime = LocalDateTime.now().format(formatter);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename != null && originalFilename.contains(".")) {
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = showroomDto.getShowroomName().replaceAll("[^a-zA-Z0-9]", "_") +
                        "_" + currentDateTime + extension;

                Path path = Paths.get(uploadDir + newFileName);
                Files.write(path, multipartFile.getBytes());

                showroomDto.setShowroomImg(newFileName);
                log.info("Image uploaded: {}", newFileName);
            } else {
                log.warn("Invalid image file.");
            }
        } else {
            log.warn("No image file provided.");
        }

        // capitalizeWords showroom name if applicable
        showroomDto.setShowroomName(FormatUtil.capitalizeWords(showroomDto.getShowroomName()));
        showroomDto.setShowroomManager(FormatUtil.capitalizeWords(showroomDto.getShowroomManager()));
        showroomDto.setShowroomAddress(FormatUtil.capitalizeWords(showroomDto.getShowroomAddress()));

        //save
        boolean isAdded = showroomService.addShowroom(showroomDto);
        if (isAdded) {
            log.info("added showroom dto: {}", showroomDto);
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
        System.out.println("\nAll showroom from Controller: " + showroomList); // Just once
        model.addAttribute("showroomList", showroomList);
        return "admin/view-allShowroom";
    }

    // ========== USER MANAGEMENT ==========
    @GetMapping("/manage-users")
    public String manageUsers(@RequestParam(value = "scheduleType",required = false) String scheduleType, Model model, HttpSession session) {
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
            RedirectAttributes redirectAttributes) {

        FollowUpDto followUpLogDto = new FollowUpDto();
        followUpLogDto.setUserId(userId);
        followUpLogDto.setFollowupDate(LocalDate.now());
        followUpLogDto.setCallStatus(callStatus);
        followUpLogDto.setNotes(notes);

        boolean saveFollowUp = followUpService.saveFollowUp(followUpLogDto);
        if (saveFollowUp){
            redirectAttributes.addFlashAttribute("success", "Follow-up saved successfully!");
        }else {
            redirectAttributes.addFlashAttribute("error", "Failed to save Follow-up details!!");
        }

        return "redirect:/admin/followup-user?id=" + userId;
    }

    // ========== DELETE BIKES / SHOWROOM / USER ==========
    @GetMapping("delete/{type}/{id}")
    public RedirectView deleteById(
            @PathVariable("type") String type,
            @PathVariable("id") int id,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {

        String contextPath = req.getContextPath();

        switch (type.toLowerCase()) {
            case "user":
                userService.deleteUserAndFollowups(id);
                log.info("Successfully deleted user ID {}", id);
                redirectAttributes.addFlashAttribute("success", "User deleted successfully");
                return new RedirectView(contextPath + "/admin/manage-users");
            case "bike":
//                bikeService.deleteById(id);
                return new RedirectView(contextPath + "/admin/manage-bikes");
            case "showroom":
//                showroomService.deleteById(id);
                return new RedirectView(contextPath + "/admin/manage-showrooms");
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes redirectAttributes) {
        session.invalidate();
//        return "redirect:/admin/login?logout";
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out.");
        return "redirect:/";

    }

    /*@GetMapping("delete/{type}/{id}")
    public RedirectView deleteById(
            @PathVariable("type") String type,
            @PathVariable("id") int id,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {

        try {
            switch (type.toLowerCase()) {
                case "user":
                    userService.deleteById(id);
                    redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
                    return new RedirectView(req.getContextPath() + "/admin/manage-users");
                case "bike":
                    bikeService.deleteById(id);
                    redirectAttributes.addFlashAttribute("successMessage", "Bike deleted successfully.");
                    return new RedirectView(req.getContextPath() + "/admin/manage-bikes");
                case "showroom":
                    showroomService.deleteById(id);
                    redirectAttributes.addFlashAttribute("successMessage", "Showroom deleted successfully.");
                    return new RedirectView(req.getContextPath() + "/admin/manage-showrooms");
                default:
                    redirectAttributes.addFlashAttribute("errorMessage", "Invalid delete type.");
                    return new RedirectView(req.getContextPath() + "/admin/dashboard");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred while deleting " + type + ".");
            return new RedirectView(req.getContextPath() + "/admin/dashboard");
        }
    }*/

}
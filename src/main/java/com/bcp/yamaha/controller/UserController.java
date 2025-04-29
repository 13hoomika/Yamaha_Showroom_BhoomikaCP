package com.bcp.yamaha.controller;


import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.constants.ShowroomEnum;
import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.entity.ShowroomEntity;
import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    /*@GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());

        List<BikeType> bikeTypeList = Arrays.asList(BikeType.values());
        model.addAttribute("bikeTypes", bikeTypeList);

        List<ScheduleType> scheduleTypeList = Arrays.asList(ScheduleType.values());
        model.addAttribute("scheduleTypeList", scheduleTypeList);

        List<ShowroomEnum> showroomEnumList = Arrays.asList(ShowroomEnum.values());
        model.addAttribute("showroomEnum", showroomEnumList);

        List<ShowroomDto> showrooms = showroomService.getAllShowroom();
        model.addAttribute("showrooms", showrooms);

        return "user/userRegister"; // JSP page name
    }

    @PostMapping("/registerUser")//form action
    public String processUserRegister( @Valid
            @ModelAttribute("userDto") UserDto userDto,
            Model model, RedirectAttributes redirectAttributes) {

        // Save user to database
        try {
            userService.registerUser(userDto);
            System.out.println("User added = "+ userDto);
            redirectAttributes.addFlashAttribute("successMessage", "User registered successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong!!");
            throw new RuntimeException(e);

        }
        return "redirect:/user/register";
    }*/
}

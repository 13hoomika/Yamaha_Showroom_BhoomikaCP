package com.bcp.yamaha.restcontroller;

import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/showroom")
public class ShowroomRestController {
    @Autowired
    ShowroomService showroomService;

    @GetMapping("/checkShowroomName/{showroomName}")
    @ResponseBody
    public String validateShowroomName(@PathVariable String showroomName) {
        System.out.println("Received showroomName in RestController: [" + showroomName + "]");

        if (!ValidationUtil.isValidShowroomName(showroomName)) {
            return "Invalid format. Each word must start with uppercase followed by lowercase letters (e.g., Yamaha Motors)";
        }

        boolean isShowroomExist = showroomService.existByName(showroomName);
        System.out.println("isShowroomExist: " + isShowroomExist);

        return isShowroomExist ? "Showroom already exists" : "";
    }

    @GetMapping(value = "/checkEmailValue/{email:.+}")
    public String getEmailCountAndValidate(@PathVariable String email) {
        System.out.println("Received showroom email in RestController: [" + email + "]");
        if (!ValidationUtil.isValidEmail(email)){
            return "Invalid email format";
        }

        boolean isEmailExist = showroomService.existByEmail(email);
        System.out.println("isEmailExist: " + isEmailExist);

        return isEmailExist ? "email already exists" : "";
    }

    @GetMapping("/checkManagerName/{showroomManager}")
    public String validateShowroomManagere(@PathVariable String showroomManager) {
        System.out.println("Received showroomName in RestController: [" + showroomManager + "]");

        if (!ValidationUtil.isValidShowroomName(showroomManager)) {
            return "Invalid format. Each word must start with uppercase followed by lowercase letters (e.g., Mahesh Gupta)";
        }

        return "";
    }

}

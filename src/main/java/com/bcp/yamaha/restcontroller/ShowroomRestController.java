package com.bcp.yamaha.restcontroller;

import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
            return "Invalid email";
        }

        boolean isEmailExist = showroomService.existByEmail(email);
        System.out.println("isEmailExist: " + isEmailExist);

        return isEmailExist ? "email already exists" : "";
    }

    @GetMapping("/checkManagerName/{showroomManager}")
    public String validateShowroomManagere(@PathVariable String showroomManager) {
        System.out.println("Received showroomName in RestController: [" + showroomManager + "]");

        if (!ValidationUtil.isValidShowroomName(showroomManager)) {
            return "Invalid Name. Each word must start with uppercase followed by lowercase letters (e.g., Mahesh Gupta)";
        }

        return "";
    }

    @GetMapping(value = "/checkPhValue/{showroomPhone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhCountForRegister(@PathVariable String showroomPhone){
        System.out.println("Received phNo in RestController: [" + showroomPhone + "]");

        if (ValidationUtil.isValidPhoneNumber(showroomPhone)){
            return "Phone number must be 10 digits and start with 9, 8, 7, or 6";
        }
        boolean isPhExist = showroomService.existByPhNumber(showroomPhone);
        System.out.println("isPhExist: "+ isPhExist);
        if(isPhExist){
            return "Contact number already exists";
        }
        return "";
    }
}

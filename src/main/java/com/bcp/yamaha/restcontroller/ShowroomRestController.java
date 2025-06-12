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

        String validationError = ValidationUtil.validShowroomName(showroomName);
        if (validationError != null){
            return validationError;
        }

        boolean isShowroomExist = showroomService.existByName(showroomName);
        System.out.println("isShowroomExist: " + isShowroomExist);

        return isShowroomExist ? "Showroom already exists" : "";
    }

    @GetMapping(value = "/checkEmailValue/{email:.+}")
    public String getEmailCountAndValidate(@PathVariable String email) {
        System.out.println("Received showroom email in RestController: [" + email + "]");

        String validationError = ValidationUtil.validateEmail(email);
        if (validationError != null){
            return validationError;
        }

        boolean isEmailExist = showroomService.existByEmail(email);
        System.out.println("isEmailExist: " + isEmailExist);

        return isEmailExist ? "email already exists" : "";
    }

    @GetMapping("/checkManagerName/{showroomManager}")
    public String validateShowroomManager(@PathVariable String showroomManager) {
        System.out.println("Received showroomName in RestController: [" + showroomManager + "]");

        String validationError = ValidationUtil.validateName(showroomManager);
        if (validationError != null){
            return validationError;
        }

        return "";
    }

    @GetMapping(value = "/checkPhValue/{showroomPhone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhCountForRegister(@PathVariable String showroomPhone){
        System.out.println("Received phNo in RestController: [" + showroomPhone + "]");

        String validationError = ValidationUtil.validPhoneNumber(showroomPhone);
        if (validationError != null) {
            return validationError;
        }

        boolean isPhExist = showroomService.existByPhNumber(showroomPhone);
        System.out.println("isPhExist: "+ isPhExist);
        if(isPhExist){
            return "Contact number already exists";
        }
        return "";
    }
}

package com.bcp.yamaha.restcontroller;

import com.bcp.yamaha.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/checkEmailValue/{email:.+}")
    public String getEmailCount(@PathVariable String email) {
        System.out.println("Received email in Controller: [" + email + "]");

        boolean isEmailExist = userService.existByEmail(email);
        System.out.println("isEmailExist: " + isEmailExist);

        return isEmailExist ? "email already exists" : "";
    }


    @GetMapping(value = "/checkPhValue/{phNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhCount(@PathVariable String phNo){
        System.out.println("Received phNo in Controller: [" + phNo + "]");
        boolean isPhExist = userService.existByPhNumber(phNo);
        System.out.println("isPhExist: "+isPhExist);
        if(isPhExist){
            return "phone number already exists";
        }
        return "";
    }
}

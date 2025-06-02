package com.bcp.yamaha.restcontroller;

import com.bcp.yamaha.service.user.UserService;
import com.bcp.yamaha.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.web.bind.annotation.RestController
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping("/checkUserName/{userName}")
    public String validateUserName(@PathVariable String userName) {
        System.out.println("Received userName: [" + userName + "]");

        if (!ValidationUtil.isValidUserName(userName)) {
            return "Invalid name format. Each word must start with uppercase followed by lowercase letters (e.g., John Doe)";
        }
        return "";
    }


    @GetMapping(value = "/checkEmailValue/{email:.+}")
    public String getEmailCount(@PathVariable String email) {
        System.out.println("Received email in Controller: [" + email + "]");
        if (!ValidationUtil.isValidEmail(email)){
            return "Invalid email format";
        }

        boolean isEmailExist = userService.existByEmail(email);
        System.out.println("isEmailExist: " + isEmailExist);

        return isEmailExist ? "email already exists" : "";
    }

    @GetMapping(value = "/checkPhValue/{phNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhCountForRegister(@PathVariable String phNo){
        System.out.println("Received phNo in Controller: [" + phNo + "]");

        if (ValidationUtil.isValidPhoneNumber(phNo)){
            return "Invalid phone number format. phone number must be 10 digits and start with 9, 8, 7, or 6";
        }
        boolean isPhExist = userService.existByPhNumber(phNo);
        System.out.println("isPhExist: "+ isPhExist);
        if(isPhExist){
            return "phone number already exists";
        }
        return "";
    }

    @GetMapping(value = "/checkDlNumber/{dlNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String checkDrivingLicenseNumberForRegister(@PathVariable String dlNo) {
        System.out.println("Received DL Number: [" + dlNo + "]");

        // Validate format using regex (KA0120231234567 format)
        if (ValidationUtil.isValidDl(dlNo)) {
            return "Invalid DL number format (e.g., KA0120231234567)";
        }

        boolean exists = userService.existsByDrivingLicenseNumber(dlNo);
        if (exists){
            return "Driving License number already exists";
        }

        return "";
    }

    /* ========================== Update User ========================== */
    @GetMapping(value = "/checkPhValue/{phNo}/{currentPh}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhCountForUpdate(@PathVariable String phNo, @PathVariable String currentPh) {
        System.out.println("Received phNo: [" + phNo + "], CurrentPh: [" + currentPh + "]");

        if (ValidationUtil.isValidPhoneNumber(phNo)) {
            return "Invalid phone number, must be 10 digits and start with 9, 8, 7, or 6";
        }

        if (phNo.equals(currentPh)) {
            // User hasn't changed their phone number — no validation error
            return "";
        }

        boolean isPhExist = userService.existByPhNumber(phNo);
        return isPhExist ? "phone number already exists" : "";
    }

    @GetMapping(value = "/checkDlNumber/{dlNo}/{currentDlNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String checkDrivingLicenseNumberForUpdate(@PathVariable String dlNo, @PathVariable String currentDlNo) {
        System.out.println("Received DL Number: [" + dlNo + "], Current DL Number: [" + currentDlNo + "]");

        // Format validation (e.g., KA0120231234567)
        if (ValidationUtil.isValidDl(dlNo)) {
            return "Invalid DL number format (e.g., KA0120231234567)";
        }

        // If no change in DL number, it's valid
        if (dlNo.equalsIgnoreCase(currentDlNo)) {
            return "";
        }

        // Check if DL already exists
        boolean exists = userService.existsByDrivingLicenseNumber(dlNo);
        return exists ? "Driving License number already exists" : "";
    }
}

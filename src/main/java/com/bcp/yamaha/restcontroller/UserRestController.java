package com.bcp.yamaha.restcontroller;

import com.bcp.yamaha.service.user.UserService;
import com.bcp.yamaha.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping("/checkUserName/{userName}")
    public ResponseEntity<String> validateUserName(@PathVariable String userName) {
        log.debug("Received userName: [{}]", userName);

        String validationError = ValidationUtil.validateName(userName);
        if (validationError != null){
            return ResponseEntity.badRequest().body(validationError);
        }

        return ResponseEntity.ok("");
    }

    @GetMapping(value = "/checkEmailValue/{email:.+}")
    public ResponseEntity<String> checkEmailValue(@PathVariable String email) {
        log.debug("Received email in Controller: [{}]", email);

        String validationError = ValidationUtil.validateEmail(email);
        if (validationError != null) {
            // For an invalid format, it's better to return a 400 Bad Request
            return ResponseEntity.badRequest().body(validationError);
        }

        boolean isEmailExist = userService.existByEmail(email);
        log.debug("isEmailExist: {}", isEmailExist);

        // Return 200 OK with a message indicating the email already exists
        return ResponseEntity.ok(isEmailExist ? "Email already exists" : "");
    }

    @GetMapping(value = "/checkPhValue/{phNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPhCountForRegister(@PathVariable String phNo){
        log.debug("Received phNo in Controller: [{}]", phNo);

        String validationError = ValidationUtil.validPhoneNumber(phNo);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        boolean isPhExist = userService.existByPhNumber(phNo);
        log.debug("isPhExist: {}", isPhExist);

        return ResponseEntity.ok(isPhExist? "phone number already exists" : "");

    }

    @GetMapping(value = "/checkDlNumber/{dlNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkDrivingLicenseNumberForRegister(@PathVariable String dlNo) {
        log.debug("Received DL Number: [{}]", dlNo);

        String validationError = ValidationUtil.validateDl(dlNo);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        boolean dlExists = userService.existsByDrivingLicenseNumber(dlNo);
        log.debug("dlExists: {}", dlExists);

        return ResponseEntity.ok(dlExists? "Driving License number already exists" : "");
    }

    @GetMapping(value = "/checkAge/{age}") //"URI template" using curly braces {} to denote a path variable.
    public ResponseEntity<String> validateUserAge(@PathVariable int age) {
        log.debug("Received age: [{}]", age);

        if (age < 18 || age > 100) {
            return ResponseEntity.badRequest().body("Age must be between 18 and 100");// Return 400 Bad Request
        }

        return ResponseEntity.ok("");// Return 200 OK
    }

    /* ========================== Update User ========================== */
    @GetMapping(value = "/checkPhValue/{phNo}/{currentPh}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPhCountForUpdate(@PathVariable String phNo, @PathVariable String currentPh) {
        log.debug("Received phNo: [{}], CurrentPh: [{}]", phNo, currentPh);

        String validationError = ValidationUtil.validPhoneNumber(phNo);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        if (phNo.equals(currentPh)) {
            // User hasn't changed their phone number — no validation error
            return ResponseEntity.ok("");
        }

        boolean isPhExist = userService.existByPhNumber(phNo);

        return ResponseEntity.ok(isPhExist ? "Phone number already exists" : "");
    }

    @GetMapping(value = "/checkDlNumber/{dlNo}/{currentDlNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkDrivingLicenseNumberForUpdate(@PathVariable String dlNo, @PathVariable String currentDlNo) {
        log.debug("Received DL Number: [{}], Current DL Number: [{}]", dlNo, currentDlNo);

        // Format validation (e.g., KA0120231234567)
        String validationError = ValidationUtil.validateDl(dlNo);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        // If no change in DL number, it's valid
        if (dlNo.equalsIgnoreCase(currentDlNo)) {
            return ResponseEntity.ok("");
        }

        // Check if DL already exists
        boolean exists = userService.existsByDrivingLicenseNumber(dlNo);
        return ResponseEntity.ok(exists ? "Driving License number already exists" : "");
    }
}

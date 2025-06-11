package com.bcp.yamaha.restcontroller;

import com.bcp.yamaha.service.bike.BikeService;
import com.bcp.yamaha.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bike")
public class BikeRestController {
    @Autowired
    BikeService bikeService;

    @GetMapping("/checkBikeModel/{bikeModel}")
    @ResponseBody
    public String validateBikeModel(@PathVariable String bikeModel) {
        System.out.println("Received bikeModel in RestController: [" + bikeModel + "]");

        /*if (!ValidationUtil.isValidBikeModel(bikeModel)) {
            return "Invalid Model name format";
        }*/

        // First validate the model
        String validationError = ValidationUtil.validateBikeModel(bikeModel);
        if (validationError != null) {
            return validationError;
        }

        boolean isBikeModelExist = bikeService.existByBikeModel(bikeModel);
        System.out.println("isBikeModelExist: " + isBikeModelExist);

        return isBikeModelExist ? "Bike already exists" : "";
    }

    @GetMapping("/checkBikeColor/{bikeColor}")
    @ResponseBody
    public String validateBikeColor(@PathVariable String bikeColor) {
        System.out.println("Received bikeColor in RestController: [" + bikeColor + "]");

        String validationError = ValidationUtil.validateBikeColor(bikeColor);
        if (validationError != null) {
            return validationError;
        }

        return "";
    }

}
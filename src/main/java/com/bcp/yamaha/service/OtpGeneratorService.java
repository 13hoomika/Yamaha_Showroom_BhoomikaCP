package com.bcp.yamaha.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpGeneratorService {

    public String generateRandomPassword() {
        int otp =  new Random().nextInt(900000) + 100000;
        return String.valueOf(otp);
    }
}

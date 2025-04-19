package com.bcp.yamaha.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OtpDetails {
    private String otp;
    private LocalDateTime expiry;

    public OtpDetails(String otp, LocalDateTime expiry) {
        this.otp = otp;
        this.expiry = expiry;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }
}

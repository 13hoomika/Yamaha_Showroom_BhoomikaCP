package com.bcp.yamaha.service.admin;

import com.bcp.yamaha.entity.AdminEntity;

import java.util.Optional;

public interface AdminService {
    Optional<AdminEntity> findByEmail(String email);
    AdminEntity findById(int id);

    Boolean initializeAdmin();
    boolean sendOtpToAdmin(String email);
    String generateRandomPassword();

    boolean verifyOtp(String email, String otp);
}

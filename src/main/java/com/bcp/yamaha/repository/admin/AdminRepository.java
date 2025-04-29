package com.bcp.yamaha.repository.admin;

import com.bcp.yamaha.entity.AdminEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AdminRepository {
    Boolean adminSave(AdminEntity adminEntity);
    AdminEntity findByName(String adminName);
    AdminEntity findById(int adminId);
//    AdminEntity findByEmail(String email);

    Optional<AdminEntity> findByEmail(String email);
    void updateOtp(String email, String otp, LocalDateTime currentTime);


}

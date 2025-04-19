package com.bcp.yamaha.service.admin;

import com.bcp.yamaha.constants.ShowroomEnum;
import com.bcp.yamaha.dto.BikeDto;
import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.entity.AdminEntity;
import com.bcp.yamaha.entity.BikeEntity;

import java.util.List;

public interface AdminService {
    AdminEntity findByName(String adminName);
    AdminEntity findByEmail(String email);
    AdminEntity findById(int id);
    Boolean initializeAdmin();
    boolean sendOtpToAdmin(String email);
    boolean validateAdminOtp(String otp);
    String generateRandomPassword();
}

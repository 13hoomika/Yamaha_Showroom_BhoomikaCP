package com.bcp.yamaha.service.admin;

import com.bcp.yamaha.dto.OtpDetails;
import com.bcp.yamaha.entity.AdminEntity;
import com.bcp.yamaha.repository.admin.AdminRepository;
import com.bcp.yamaha.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private OtpDetails otpDetails;

    @Override
    public String generateRandomPassword() {
//        int length = 6;
//        String characters = "0123456789";
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return sb.toString();
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Transactional
    @Override
    public Boolean initializeAdmin() {
        AdminEntity existingAdmin = adminRepository.findByName("Admin");
        if (existingAdmin != null) {
            System.out.println("Admin already initialized.");
            return false;
        }

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminName("Admin");
        adminEntity.setAdminEmail("bhoomikacp24@gmail.com");

        return adminRepository.adminSave(adminEntity);
    }

    /*@Override
    public boolean sendOtpToAdmin(String email) {
        AdminEntity admin = adminRepository.findByEmail(email);
        if (admin == null) {
            System.out.println("❌ Admin not found for email: " + email);
            return false;
        }

        // Generate OTP
        String otp = generateRandomPassword();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(2); // 2 minutes expiration

        // Store in OtpDetails object
        otpDetails = new OtpDetails(otp, expiry);

        // Send OTP to email
        boolean emailSent = emailService.sendEmail(email,
                "Your Admin Login OTP",
                "Your OTP is: " + otp + "\nValid for 2 minutes");

        if (emailSent) {
            System.out.println("✅ OTP sent to email: " + email);
            System.out.println("otp: " + otp);
            return true;
        } else {
            System.out.println("❌ Email sending failed.");
            return false;
        }
    }*/
    @Override
    public boolean sendOtpToAdmin(String email) {
        Optional<AdminEntity> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()){
            String otp = generateRandomPassword();
            adminRepository.updateOtp(email, otp, LocalDateTime.now());
            boolean emailSent = emailService.sendEmail(email, otp);
            if (emailSent) {
                System.out.println("✅ OTP sent to email: " + email);
                System.out.println("otp: " + otp);
                return true;
            } else {
                System.out.println("❌ Email sending failed.");
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        Optional<AdminEntity> admin = adminRepository.findByEmail(email);

        if (admin.isPresent() && admin.get().getAdminOtp().equals(otp)) {
            return true;
        }
        return false;
    }

//    @Override
//    public boolean validateAdminOtp(String otp) {
//        if (otpDetails == null) {
//            return false;
//        }
//
//        boolean isValid = otpDetails.getOtp().equals(otp)
//                && LocalDateTime.now().isBefore(otpDetails.getExpiry());
//
//        // Clear OTP after successful validation
//        if (isValid) {
//            otpDetails = null; // Clear after successful validation
//        }
//
//        return isValid;
//    }

    @Override
    public AdminEntity findByName(String adminName) {
        return adminRepository.findByName(adminName);
    }

    @Override
    public Optional<AdminEntity> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public AdminEntity findById(int id) {
        return adminRepository.findById(id);
    }
}

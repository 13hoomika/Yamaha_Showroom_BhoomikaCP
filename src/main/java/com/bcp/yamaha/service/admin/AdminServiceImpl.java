package com.bcp.yamaha.service.admin;

import com.bcp.yamaha.dto.AdminDto;
import com.bcp.yamaha.entity.AdminEntity;
import com.bcp.yamaha.repository.admin.AdminRepository;
import com.bcp.yamaha.service.EmailService;
import org.springframework.beans.BeanUtils;
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

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String generateRandomPassword() {
        int otp =  new Random().nextInt(900000) + 100000;
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
        return admin.isPresent() && admin.get().getAdminOtp().equals(otp);
    }

    @Override
    public Optional<AdminDto> findByEmail(String email) {
        Optional<AdminEntity> adminEntityOpt = adminRepository.findByEmail(email);

        return adminEntityOpt.map(entity -> {
            AdminDto dto = new AdminDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        });

    }

    @Override
    public AdminEntity findById(int id) {
        return adminRepository.findById(id);
    }
}

package com.bcp.yamaha.service.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.entity.UserEntity;
import com.bcp.yamaha.repository.user.UserRepository;
import com.bcp.yamaha.service.showroom.ShowroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ShowroomService showroomService;

   /* private static final int OTP_EXPIRY_MINUTES = 2;
    private static final int MAX_LOGIN_ATTEMPTS = 3;*/

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    @Override
    public void registerUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        if (userDto.getShowroomId() != null) {
            ShowroomDto showroom = showroomService.getShowroomById(userDto.getShowroomId());
            if (showroom != null) {
                userEntity.setShowroomName(showroom.getShowroomName()); // Set name from ID
            }
        }
        userRepository.saveUser(userEntity);
        log.info("User registered: {}", userEntity.getUserEmail());
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> entityList = userRepository.findAllUser();
        List<UserDto> dtoList = new ArrayList<>();

        if (entityList != null){
            for(UserEntity entity : entityList){
                UserDto dto = new UserDto();

                BeanUtils.copyProperties(entity,dto);
                dtoList.add(dto);
            }
        }
        return  dtoList;
    }

    @Override
    public UserDto getUserByName(String name) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findUserByName(name);

        if (userEntity != null){
            BeanUtils.copyProperties(userEntity,userDto);
            return userDto;
        }else {
            System.out.println("UserEntity is null for name:" + name);
            return null;
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findUserByEmail(email);

        if (userEntity != null){
            BeanUtils.copyProperties(userEntity,userDto);
            return userDto;
        }else {
            System.out.println("UserEntity is null for email:" + email);
            return null;
        }
    }

    @Override
    public UserDto getUserById(int userId) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findById(userId);
        if (userEntity != null){
            BeanUtils.copyProperties(userEntity,userDto);
            return userDto;
        }else {
            System.out.println(("User entity is null for userId: " + userId));
            return null;
        }
    }

    @Override
    public Long getTotalUserCount() {
        return userRepository.countAllUsers();
    }

    @Override
    public List<UserDto> getUsersByScheduleType(ScheduleType scheduleType) {
        List<UserEntity> userEntityList = userRepository.findByScheduleType(scheduleType);
        List<UserDto> userDtoList = new ArrayList<>();

        for (UserEntity entity : userEntityList){
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(entity,dto);

            userDtoList.add(dto);
        }
        return userDtoList;
    }


    /*@Override
    public String generateRandomOtp() {
        int otp = new Random().nextInt(9000) + 1000;
        return String.valueOf(otp);
    }

    @Override
    @Transactional
    public boolean sendLoginOtp(String email) {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.warn("Login attempt for non-existent email: {}", email);
            return false;
        }

        String otp = generateRandomOtp();
        String encryptedOtp = passwordEncoder.encode(otp);

        // Store OTP with expiration
        user.setOtp(encryptedOtp);
        user.setOtpGenerated(LocalDateTime.now());
        user.setOtpExpired(false);

        try {
            userRepository.updateUser(user);
            System.out.println("otp sent " + otp);
            return sendEmail(email, otp);
        } catch (Exception e) {
            log.error("Failed to update user OTP", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean validateLoginOtp(String email, String otp) {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null){
            return false;
        }

        if (userEntity.getOtpGenerated().isBefore(LocalDateTime.now().minusMinutes(2))) {
            userEntity.setOtpExpired(true);
            userRepository.updateUser(userEntity);
            return false;
        }

        return passwordEncoder.matches(otp, userEntity.getOtp());
    }

    @Override
    public LocalDateTime getOtpExpiryTime(String email) {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null || user.getOtpGenerated() == null) {
            return null; // Or throw specific exception
        }
        return user.getOtpGenerated().plusMinutes(OTP_EXPIRY_MINUTES);
    }

    public boolean sendEmail(String recipientEmail, String generatedPassword) {
        final String username = "bhoomikacp.xworkz@gmail.com";
        final String password = "zilw thew euxr dmsr";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your Generated OTP");
            message.setText("Dear User,\n\nYour Login OTP is: " + generatedPassword + "\n\nUse this OTP to login.\n\nBest regards,\n Yamaha Motor Team");

            Transport.send(message);

            log.info("Email sent successfully to: {}", recipientEmail);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }*/
}

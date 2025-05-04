package com.bcp.yamaha.service.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.entity.UserEntity;
import com.bcp.yamaha.repository.user.UserRepository;
import com.bcp.yamaha.service.EmailService;
import com.bcp.yamaha.service.showroom.ShowroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ShowroomService showroomService;

//    @Autowired
//    OtpGeneratorService otpGeneratorService;

    @Autowired
    EmailService emailService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String generateRandomPassword() {
        int otp =  new Random().nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

    @Transactional
    @Override
    public void registerUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
//        userEntity.setInvalidLogInCount(-1);

        if (userDto.getShowroomId() != null) {
            ShowroomDto showroom = showroomService.getShowroomById(userDto.getShowroomId());
            if (showroom != null) {
                userEntity.setShowroomName(showroom.getShowroomName()); // Set name from ID
            }
        }
        Boolean isSaved = userRepository.saveUser(userEntity);
        log.info("User registered: {}", userEntity.getUserEmail());

        if (isSaved){
            String otp = generateRandomPassword();
            userRepository.updatePassword(userEntity.getUserEmail(), otp);
            boolean emailSent = emailService.sendEmail(userEntity.getUserEmail(), otp);
            if (emailSent) {
                System.out.println("✅ OTP sent to email: " + userEntity.getUserEmail());
                System.out.println("otp: " + otp);
            } else {
                System.out.println("❌ Email sending failed.");
            }
        }
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
    public UserDto getUserByEmail(String email) {
        UserDto userDto = new UserDto();
        Optional<UserEntity> userByEmail = userRepository.findUserByEmail(email);

        if (userByEmail == null){
            System.out.println("UserEntity is null for email:" + email);
            return null;
        }else {
            BeanUtils.copyProperties(userByEmail,userDto);
            return userDto;
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

    @Override
    public Boolean validateAndLogIn(String email, String password) {
        Optional<UserEntity> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            UserEntity u = user.get();

            if (passwordEncoder.matches(password,u.getPassword())) {
                System.out.println("User authenticated: " + u.getUserEmail());
                return true;
            }else {
                System.out.println("Invalid password for user: " + u.getUserEmail());
            }
        }else {
            System.out.println("No user found with email: " + email);
        }
        return false;
    }

    /*@Override
    public Boolean validateAndLogIn(String email, String password) {
        Optional<UserEntity> user = userRepository.findUserByEmail(email);

        if (user.isPresent()) {
            UserEntity u = user.get();

            if (u.getAccountLocked() != null && u.getAccountLocked()) {
                LocalDateTime lastFailed = u.getLastLogIn();

                if (lastFailed == null || Duration.between(lastFailed, LocalDateTime.now()).toMillis() >= 60000) {
                    // Unlock account after timeout or if no timestamp exists
                    u.setAccountLocked(false);
                    u.setInvalidLogInCount(0); // reset login count
                    userRepository.updateAccountLockStatus(u); // persist changes
                    System.out.println("🔓 Account unlocked: " + u.getUserEmail());
                } else {
                    System.out.println("⏳ Account still locked: " + u.getUserEmail());
                    return false;
                }
            }


            if (passwordEncoder.matches(password, u.getPassword())) {
                System.out.println("✅ User authenticated: " + u.getUserEmail());
                return true;
            } else {
                System.out.println("❌ Invalid password for user: " + u.getUserEmail());

                // update failed login count and lock if necessary
                int attempts = (u.getInvalidLogInCount() == null ? 0 : u.getInvalidLogInCount()) + 1;
                u.setInvalidLogInCount(attempts);
                u.setLastLogIn(LocalDateTime.now());

                int remainingAttempts = 3 - attempts;

                if (attempts >= 3) { // Lock after 3 attempts
                    u.setAccountLocked(true);
                    System.out.println("🔒 Account locked: " + u.getUserEmail());
                }  else {
                    System.out.println("❌ Invalid password for user: " + u.getUserEmail() +
                            " | Attempts left: " + remainingAttempts);
                }

                userRepository.updateLoginAttemptData(u); // persist changes
            }
        } else {
            System.out.println("❌ No user found with email: " + email);
        }
        return false;
    }*/

    @Override
    public boolean resetPassword(String email, String newPassword) {
        UserEntity user = userRepository.findUserByEmail(email).get();
        if (user == null) {
            log.warn("User not found with email: {}", email);
            return false; // User not found
        }
        // Validate the new password
        String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (newPassword == null || !Pattern.matches(PASSWORD_REGEX, newPassword)) {
            log.warn("Invalid New Password: {}", newPassword);
            log.warn("Invalid Password. Must be at least 8 characters long, include one uppercase letter, one lowercase letter, one digit, and one special character.");
            return false;
        }

        String hashedPassword = passwordEncoder.encode(newPassword);

        try {
            boolean isUpdated = userRepository.updatePassword(email, hashedPassword);
            if (!isUpdated) {
                log.error("Failed to update user for email: {}", email);
                return false;
            }
            log.info("Password successfully reset for user with email: {}", email);
            return true;
        } catch (Exception e) {
            log.error("Error updating user profile for email: {}", email, e);
            return false;
        }
    }
}

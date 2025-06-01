package com.bcp.yamaha.service.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.entity.UserEntity;
import com.bcp.yamaha.exception.UserNotFoundException;
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
import java.util.*;
import java.util.regex.Pattern;

@Service
@Slf4j
@Transactional
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
            String hashedOtp = passwordEncoder.encode(otp);

            userRepository.updatePassword(userEntity.getUserEmail(), hashedOtp);
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
        System.out.println("============= UserService : getUserByEmail() ===================");
        UserDto userDto = new UserDto();
        Optional<UserEntity> userByEmail = userRepository.findUserByEmail(email);

        /*if (userByEmail.isPresent()){
            BeanUtils.copyProperties(userByEmail.get(),userDto);
            return userDto;
        }else {
            System.out.println("UserEntity is null for email:" + email);
            return null;
        }*/

        //cleaner version
        return userByEmail.map(userEntity -> {
            BeanUtils.copyProperties(userEntity, userDto);
            return userDto;
        }).orElseGet(() -> {
            System.out.println("UserEntity is null for email: " + email);
            return null;
        });

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
    public UserDto validateAndLogIn(String email, String password) {
        System.out.println("============= UserService : validateAndLogIn() ===================");
        Optional<UserEntity> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            UserEntity u = user.get();

            if (passwordEncoder.matches(password,u.getPassword())) {
                System.out.println("User authenticated: " + u.getUserEmail());
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(u,userDto);
                return userDto;
            }else {
                System.out.println("Invalid password for user: " + u.getUserEmail());
            }
        }else {
            log.error("No user found with email: {}", email);
        }
        return null;
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
            System.out.println("No user found : " + email);
        }
        return false;
    }*/

    @Override
    public boolean resetPassword(String email, String newPassword) {
        Optional<UserEntity> optionalUser = userRepository.findUserByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found with email: " + email);
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

    @Override
    public boolean updateProfile(UserDto userDto) {
        boolean isUpdated = false;
        try {
            Optional<UserEntity> userOpt = userRepository.findUserByEmail(userDto.getUserEmail());

            if (userOpt.isPresent()) {
                UserEntity existingUser = userOpt.get();

                existingUser.setUserName(userDto.getUserName());
                existingUser.setUserAddress(userDto.getUserAddress());
                existingUser.setDrivingLicenseNumber(userDto.getDrivingLicenseNumber());
                existingUser.setUserAge(userDto.getUserAge());
                existingUser.setUserPhoneNumber(userDto.getUserPhoneNumber());
                existingUser.setLastModifiedDateTime(LocalDateTime.now());

                isUpdated = userRepository.updateProfile(existingUser);
                System.out.println("isUpdated in service: " + isUpdated);
            } else {
                System.out.println("Could not find user with email: " + userDto.getUserEmail());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return isUpdated;
    }


    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
        System.out.println("user deleted successfully");
    }

    @Override
    public boolean existByEmail(String email) {
        System.out.println("invoking existByEmail in service..........");
        boolean emailExist = userRepository.existByEmail(email);
        System.out.println("is emailExist in service: " + emailExist);
        return emailExist;
    }

    @Override
    public boolean existByPhNumber(String phNumber) {
        return userRepository.existByPhNumber(phNumber);
    }

    @Override
    public boolean existsByDrivingLicenseNumber(String dlNo) {
        return userRepository.existsByDrivingLicenseNumber(dlNo);
    }

    @Override
    public boolean updateUserProfileImage(int userId, String profileImagePath) {
        UserEntity user = userRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        user.setProfileImage(profileImagePath);
        user.setLastModifiedDateTime(LocalDateTime.now());

        boolean saved = userRepository.saveUser(user);

        if (!saved) {
            throw new RuntimeException("Failed to save user profile image for id: " + userId);
        }

        System.out.println("Profile image updated successfully for user ID: " + userId);
        return true;
    }

}

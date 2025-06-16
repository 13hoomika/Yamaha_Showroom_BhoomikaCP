package com.bcp.yamaha.service.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.entity.UserEntity;
import com.bcp.yamaha.exception.InvalidPasswordException;
import com.bcp.yamaha.exception.NotFoundException;
import com.bcp.yamaha.repository.followup.FollowUpRepository;
import com.bcp.yamaha.repository.user.UserRepository;
import com.bcp.yamaha.service.EmailService;
import com.bcp.yamaha.service.OtpGeneratorService;
import com.bcp.yamaha.service.showroom.ShowroomService;
import com.bcp.yamaha.util.FormatUtil;
import com.bcp.yamaha.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ShowroomService showroomService;

    @Autowired
    OtpGeneratorService otpGeneratorService;

    @Autowired
    EmailService emailService;

    @Autowired
    FollowUpRepository followUpRepository;

    @Autowired // Inject the service into itself for @Transactional(REQUIRES_NEW) to work
    private UserService userServiceProxy; // This is a common pattern for self-invocation of transactional methods

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /*public String generateRandomPassword() {
        int otp =  new Random().nextInt(900000) + 100000;
        return String.valueOf(otp);
    }*/

    @Transactional
    @Override
    public UserDto  registerUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setInvalidLogInCount(-1);
        userEntity.setAccountLocked(false);

        if (userDto.getShowroomId() != null) {
            ShowroomDto showroom = showroomService.getShowroomById(userDto.getShowroomId());
            if (showroom != null) {
                userEntity.setShowroomName(showroom.getShowroomName()); // Set name from ID
            }
        }
        Boolean isSaved = userRepository.saveUser(userEntity);
        log.info("User registered: {}", userEntity.getUserEmail());

        if (isSaved){
            String otp = otpGeneratorService.generateRandomPassword();
            String hashedOtp = passwordEncoder.encode(otp);

            userRepository.updatePassword(userEntity.getUserEmail(), hashedOtp);
            boolean emailSent = emailService.sendEmail(userEntity.getUserEmail(), otp);
            if (emailSent) {
                System.out.println("OTP sent to email: " + userEntity.getUserEmail());
                System.out.println("otp: " + otp);
            } else {
                System.out.println("Email sending failed.");
            }
        }
        // Convert back to DTO before returning
        UserDto responseDto = new UserDto();
        BeanUtils.copyProperties(userEntity, responseDto);
        return responseDto;
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

        if (userByEmail.isPresent()){
            BeanUtils.copyProperties(userByEmail.get(),userDto);
            return userDto;
        }else {
            log.error("UserEntity is null for email:{}", email);
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
            log.error("User entity is null for userId: {}", userId);
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
    }*/

    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final Duration LOCK_DURATION = Duration.ofMinutes(3);

    @Transactional// The main login method is transactional
    @Override
    public UserDto validateAndLogIn(String email, String password) {
        System.out.println("============= UserService : validateAndLogIn() ===================");

        UserEntity user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Login attempt failed: No user with email {}", email);
                    return new NotFoundException("User not found. Please check your email or register first.");
                });

        log.debug("Initial user state - Count: {}, Locked: {}, LastLogin: {}",
                user.getInvalidLogInCount(), user.isAccountLocked(), user.getLastLogIn());

        // 2. Check if account is locked
        if (user.isAccountLocked()) {
            Instant now = Instant.now();
            Instant unlockTime = user.getLastLogIn();

            if (unlockTime != null && now.isBefore(unlockTime)) {
                Duration remainingTime = Duration.between(now, unlockTime);
                String remainingTimeFormatted = FormatUtil.formatDuration(remainingTime);

                log.warn("Locked account attempted login: {}", email);
                throw new InvalidPasswordException("Account is locked. Try again after " + remainingTimeFormatted);
            } else {
                // Unlock the account immediately and commit this change
                user.setAccountLocked(false);
                user.setInvalidLogInCount(0);
                user.setLastLogIn(null);
                // Call a method that will manage its own transaction to persist this unlock
                // The current transaction (from validateAndLogIn) will then proceed.
                userServiceProxy.updateLoginStatusAndCommit(user); // Call the new method
                log.info("Account unlocked automatically: {}", email);
            }
        }

        // 3. Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            int newCount = user.getInvalidLogInCount() + 1;
            user.setInvalidLogInCount(newCount);

            if (newCount >= MAX_LOGIN_ATTEMPTS) {
                user.setAccountLocked(true);
                user.setLastLogIn(Instant.now().plus(LOCK_DURATION)); // Lock for 3 minutes
                log.warn("Account locked due to {} failed attempts: {}", newCount, email);
            }

            // --- IMPORTANT: Persist the failed login attempt *before* throwing the exception ---
            // This will commit the changes to invalidLogInCount, accountLocked, and lastLogIn
            userServiceProxy.updateLoginStatusAndCommit(user); // Call the new method

            throw new InvalidPasswordException(
                    newCount < MAX_LOGIN_ATTEMPTS
                            ? "Invalid password. " + (MAX_LOGIN_ATTEMPTS - newCount) + " attempt(s) remaining."
                            : "Account locked due to 3 failed attempts. Try again later."
            );
        }

        // Successful login: reset counters and commit
        user.setInvalidLogInCount(0);
        user.setAccountLocked(false); // Ensure it's not locked on successful login
        user.setLastLogIn(null);

        // Since validateAndLogIn is @Transactional, these changes will be committed when it returns successfully
        log.info("User authenticated: {}", user.getUserEmail());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    /**
     * This method is responsible for updating the login status (count, locked, last login)
     * and committing these changes in a NEW, independent transaction.
     * This prevents rollback if the calling method (e.g., validateAndLogIn) later throws an exception.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateLoginStatusAndCommit(UserEntity user) {
        log.debug("Committing login status update for user: {}", user.getUserEmail());
        userRepository.saveUser(user);
    }


    @Override
    public boolean resetPassword(String email, String newPassword) {
        userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User not found. Please check your email or register first.")
                );

        // Validate the new password
        String passwordValidationError = ValidationUtil.validatePassword(newPassword);
        if (passwordValidationError != null) { // If validationError is not null, it means there's an error
            log.warn("Password validation failed for user: {}. Reason: {}", email, passwordValidationError);
            throw new InvalidPasswordException(passwordValidationError);
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

    @Override
    public void deleteUserAndFollowups(int userId){
        followUpRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
        System.out.println("UserService: user ID "+ userId +" deleted successfully");
    }
}

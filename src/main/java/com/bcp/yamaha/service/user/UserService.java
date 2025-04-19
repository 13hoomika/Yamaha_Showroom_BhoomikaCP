package com.bcp.yamaha.service.user;

import com.bcp.yamaha.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;


public interface UserService {
    void registerUser(UserDto userDto);
    List<UserDto> getAllUsers();

    UserDto getUserByName(String name);
    UserDto getUserByEmail(String email);

    UserDto getUserById(int userId);

    Long getTotalUserCount();


    /*String generateRandomOtp();
    boolean sendLoginOtp(String email);
    boolean validateLoginOtp(String name, String otp);
    LocalDateTime getOtpExpiryTime(String email);*/


}

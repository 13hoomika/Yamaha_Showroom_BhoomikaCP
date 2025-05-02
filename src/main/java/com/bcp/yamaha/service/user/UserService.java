package com.bcp.yamaha.service.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.BikeDto;
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

    List<UserDto> getUsersByScheduleType(ScheduleType scheduleType);



    /*String generateRandomOtp();
    boolean sendLoginOtp(String email);
    boolean validateLoginOtp(String name, String otp);
    LocalDateTime getOtpExpiryTime(String email);*/


}

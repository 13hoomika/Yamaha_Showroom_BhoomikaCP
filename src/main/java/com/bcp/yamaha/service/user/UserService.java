package com.bcp.yamaha.service.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.UserDto;
import java.util.List;


public interface UserService {
    UserDto registerUser(UserDto userDto);
    List<UserDto> getAllUsers();

    UserDto getUserByEmail(String email);
    UserDto getUserById(int userId);
    Long getTotalUserCount();

    List<UserDto> getUsersByScheduleType(ScheduleType scheduleType);

    UserDto validateAndLogIn(String email, String password);
    boolean resetPassword(String email, String newPassword);

    boolean updateProfile(UserDto uerDto);

    void deleteById(int id);

    boolean existByEmail(String email);
    boolean existByPhNumber(String phNumber);
    boolean existsByDrivingLicenseNumber(String dlNo);

    boolean updateUserProfileImage(int userId, String profileImagePath);
}

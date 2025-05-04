package com.bcp.yamaha.service.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.dto.UserDto;
import java.util.List;


public interface UserService {
    void registerUser(UserDto userDto);
    List<UserDto> getAllUsers();

//    UserDto getUserByEmail(String email);
    UserDto getUserById(int userId);
    Long getTotalUserCount();

    List<UserDto> getUsersByScheduleType(ScheduleType scheduleType);

    Boolean validateAndLogIn(String email, String password);
}

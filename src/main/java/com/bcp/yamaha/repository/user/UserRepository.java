package com.bcp.yamaha.repository.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository{
    Boolean saveUser(UserEntity userEntity);
    Optional<UserEntity> findUserByEmail(String email);
//    UserEntity findUserByName(String name);
    UserEntity findById(int userId);

    List<UserEntity> findAllUser();
    Long countAllUsers();

    List<UserEntity> findByScheduleType(ScheduleType scheduleType);
    boolean updatePassword(String email, String hashedPassword);

    boolean updateProfile(UserEntity updatedEntity);

    void deleteById(int id);

    /*void updateAccountLockStatus(UserEntity user);
    void updateLoginAttemptData(UserEntity user);

    boolean updateAccountLockData(UserEntity u);*/
}

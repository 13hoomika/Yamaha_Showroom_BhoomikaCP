package com.bcp.yamaha.repository.user;

import com.bcp.yamaha.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository{
    Boolean saveUser(UserEntity userEntity);
    UserEntity findUserByEmail(String email);
    UserEntity findUserByName(String name);
    UserEntity findById(int userId);

    List<UserEntity> findAllUser();
    Long countAllUsers();

//    void updateUser(UserEntity user);

}

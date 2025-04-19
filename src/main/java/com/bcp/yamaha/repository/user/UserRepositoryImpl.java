package com.bcp.yamaha.repository.user;

import com.bcp.yamaha.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean saveUser(UserEntity userEntity) {
        try {
            em.persist(userEntity);
            return true;
        } catch (Exception e) {
            System.out.println("Error in saving user details " + e.getMessage());

            return false;
        }
    }
    @Override
    public UserEntity findUserByEmail(String email) {
        try {
            return (UserEntity) em.createNamedQuery("findUserByEmail")
                    .setParameter("userEmail", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("no user found with email: " + email);
            return null;
        }
    }

    @Override
    public UserEntity findUserByName(String name) {
        try {
            return (UserEntity) em.createNamedQuery("findUserByName")
                    .setParameter("userName", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("no user found with userName: " + name);
            return null;
        }
    }

    @Override
    public List<UserEntity> findAllUser() {
        return em.createNamedQuery("findAllUsers", UserEntity.class).getResultList();
    }

    @Override
    public Long countAllUsers() {
        return em.createNamedQuery("countAllUsers", Long.class)
                .getSingleResult();
    }

    @Override
    public UserEntity findById(int userId) {
        return em.find(UserEntity.class,userId);
    }

    /*@Override
    public void updateUser(UserEntity userEmail) {
        try {
            UserEntity managedUser = em.find(UserEntity.class, userEmail.getUserId());
            if (managedUser != null) {
                // Only update OTP-related fields
                managedUser.setOtp(userEmail.getOtp());
                managedUser.setOtpGenerated(userEmail.getOtpGenerated());
                managedUser.setOtpExpired(userEmail.isOtpExpired());
                em.merge(managedUser);
            }
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
            throw new RuntimeException("Failed to update user");
        }
    }*/

}

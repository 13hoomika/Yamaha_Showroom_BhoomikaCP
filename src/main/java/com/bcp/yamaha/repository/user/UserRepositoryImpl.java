package com.bcp.yamaha.repository.user;

import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
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
    public Optional<UserEntity> findUserByEmail(String email) {
        System.out.println("============= UserRepo : findUserByEmail() ===================");
        try {
            UserEntity user = (UserEntity) em.createNamedQuery("findUserByEmail")
                    .setParameter("userEmail", email)
                    .getSingleResult();

            return Optional.of(user);

        } catch (NoResultException e) {
            log.error("no user found with email: {}", email);
            return Optional.empty();
        }
    }

    /*@Override
    public UserEntity findUserByName(String name) {
        try {
            return (UserEntity) em.createNamedQuery("findUserByName")
                    .setParameter("userName", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("no user found with userName: " + name);
            return null;
        }
    }*/

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
    public List<UserEntity> findByScheduleType(ScheduleType scheduleType) {
        return em.createNamedQuery(
                        "findByScheduleType", UserEntity.class)
                .setParameter("scheduleType", scheduleType)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean updatePassword(String email, String hashedPassword) {
        try {
            int updated = em.createNamedQuery("updatePassword")
                    .setParameter("password", hashedPassword)
                    .setParameter("email", email)
                    .executeUpdate();
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProfile(UserEntity updatedEntity) {
        boolean isUpdated = false;
        try {
            // Fetch the existing entity
            UserEntity existingEntity = em.find(UserEntity.class, updatedEntity.getUserId());
            if (existingEntity != null) {
                // Update all fields
                existingEntity.setUserName(updatedEntity.getUserName());
                existingEntity.setUserAge(updatedEntity.getUserAge());
                existingEntity.setUserPhoneNumber(updatedEntity.getUserPhoneNumber());
                existingEntity.setUserAddress(updatedEntity.getUserAddress());
                existingEntity.setDrivingLicenseNumber(updatedEntity.getDrivingLicenseNumber());

                /*existingEntity.setInvalidLogInCount(updatedEntity.getInvalidLogInCount());
                existingEntity.setAccountLocked(updatedEntity.isAccountLocked());
                existingEntity.setLastLogIn(updatedEntity.getLastLogIn());*/

                em.merge(existingEntity);
                /*//Set updatedBy and updatedTime AFTER the merge is successful
                existingEntity.setUpdateBy(updatedEntity.getUserName());
                existingEntity.setUpdatedTime(LocalDateTime.now());

                // Merge again to save updatedBy and updatedTime
                em.merge(existingEntity);*/
                isUpdated = true;
            }
        } catch (Exception e) {
            log.warn("Error updating profile: {}", e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return isUpdated;
    }

    /*@Transactional
    @Override
    public void updateAccountLockStatus(UserEntity user) {
        em.createNamedQuery("updateAccountLockStatus")
                .setParameter("accountLocked", user.getAccountLocked())  // true to lock the account
                .setParameter("userEmail", user.getUserEmail())
                .executeUpdate();
        System.out.println("🔒 Account lock status updated for user: " + user.getUserEmail());
    }

    @Transactional
    @Override
    public void updateLoginAttemptData(UserEntity user) {
        em.createNamedQuery("updateLoginAttemptData")
                .setParameter("invalidLogInCount", user.getInvalidLogInCount())
                .setParameter("lastLogIn", user.getLastLogIn())
                .setParameter("userEmail", user.getUserEmail())
                .executeUpdate();
        System.out.println("🔄 Login attempt data updated for user: " + user.getUserEmail());
    }

    @Override
    public boolean updateAccountLockData(UserEntity u) {
        boolean isUpdated = false;
        try {
            em.getTransaction().begin();
            // Fetch the existing entity
            UserEntity existingEntity = em.find(UserEntity.class, u.getUserId());
            if (existingEntity != null) {
                existingEntity.setInvalidLogInCount(u.getInvalidLogInCount());
                existingEntity.setAccountLocked(u.getAccountLocked());
                existingEntity.setLastLogIn(u.getLastLogIn());

                em.merge(existingEntity);
//                //Set updatedBy and updatedTime AFTER the merge is successful
//                existingEntity.setUsetpdateBy(u.getName());
//                existingEntity.setUpdatedTime(LocalDateTime.now());

                // Merge again to save updatedBy and updatedTime
                em.merge(existingEntity);
                isUpdated = true;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error updating profile:" + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
        return isUpdated;
    }*/


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

    @Override
    public void deleteById(int id) {
        em.createNamedQuery("deleteUser").setParameter("id",id).executeUpdate();
    }

}

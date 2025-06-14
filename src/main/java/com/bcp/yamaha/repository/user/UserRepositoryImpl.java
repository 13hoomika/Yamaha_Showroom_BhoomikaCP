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
            if (userEntity.getUserId() == 0) {
                em.persist(userEntity);
            } else {
                em.merge(userEntity);
            }
            return true;
        } catch (Exception e) {
            log.error("Error in saving user details {}", e.getMessage());
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
                return true;
            }
        } catch (Exception e) {
            log.warn("Error updating profile: {}", e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public UserEntity findById(int userId) {
        return em.find(UserEntity.class,userId);
    }

    @Override
    public void deleteById(int id) {
        em.createNamedQuery("deleteUser").setParameter("id",id).executeUpdate();
    }

    @Override
    public boolean existByEmail(String email) {
        try {
            System.out.println("Checking email in repo: [" + email + "]"); // Debugging

            Long count = em.createNamedQuery("emailExist", Long.class)
                    .setParameter("email", email.trim()) // Ensure no spaces
                    .getSingleResult();

            System.out.println("Email count from repo: " + count);
            return count > 0;
        } catch (Exception e) {
            log.warn("Error checking email existence: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existByPhNumber(String phNumber) {
        try {
            Long count = em.createNamedQuery("phExist", Long.class)
                    .setParameter("phNumber", phNumber)
                    .getSingleResult();
            return count > 0; // Return true if phone number exists, false otherwise
        } catch (Exception e) {
            log.warn("Error checking phone number existence:{}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existsByDrivingLicenseNumber(String dlNo) {
        try {
            Long count = em.createNamedQuery("dlExist", Long.class)
                    .setParameter("dlNo", dlNo)
                    .getSingleResult();
            System.out.println("count :" + count);
            return count > 0; // Return true if phone number exists, false otherwise
        } catch (Exception e) {
            log.warn("Error checking Driving License number existence:{}", e.getMessage());
            return false;
        }
    }

/*    @Override
    public void updateUserProfileImage(int userId, String profileImagePath) {
        UserEntity user = em.find(UserEntity.class, userId);
        if (user != null) {
            user.setProfileImage(profileImagePath);
            em.merge(user); // update the entity
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }*/

}

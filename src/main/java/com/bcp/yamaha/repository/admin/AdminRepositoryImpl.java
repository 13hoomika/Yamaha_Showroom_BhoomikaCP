package com.bcp.yamaha.repository.admin;

import com.bcp.yamaha.entity.AdminEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    /*@Autowired
    EntityManagerFactory emf;
    Error adding showroom: No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
    */

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean adminSave(AdminEntity adminEntity) {
        try {
            em.persist(adminEntity);
            return true;
        } catch (Exception e) {
            System.out.println("Error saving admin: " + e.getMessage());
            return false;
        }
    }

    @Override
    public AdminEntity findByName(String adminName) {
        try {
            return em.createNamedQuery("findByName", AdminEntity.class)
                    .setParameter("adminName", adminName)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No admin found for name: " + adminName);
            return null;
        }
    }

    @Override
    public AdminEntity findById(int adminId) {
        return em.find(AdminEntity.class,adminId);
    }

    @Override
    public Optional<AdminEntity> findByEmail(String email) {
        try {
            return Optional.of(em.createNamedQuery("findByEmail", AdminEntity.class)
                    .setParameter("adminEmail", email)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public void updateOtp(String email, String otp, LocalDateTime currentTime) {
        try {
            em.createQuery(
                            "UPDATE AdminEntity a SET a.adminOtp = :otp, a.otpGeneratedTime = :currentTime WHERE a.adminEmail = :email")
                    .setParameter("otp", otp)
                    .setParameter("currentTime", currentTime)
                    .setParameter("email", email)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

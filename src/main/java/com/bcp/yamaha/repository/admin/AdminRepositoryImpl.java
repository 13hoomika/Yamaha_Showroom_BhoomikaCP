package com.bcp.yamaha.repository.admin;

import com.bcp.yamaha.entity.AdminEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

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
    public AdminEntity findByEmail(String email) {
        try {
            return em.createNamedQuery("findByEmail", AdminEntity.class)
                    .setParameter("adminEmail", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }







}

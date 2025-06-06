package com.bcp.yamaha.repository.showroom;

import com.bcp.yamaha.entity.ShowroomEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
public class ShowroomRepositoryImpl implements ShowroomRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean addShowroom(ShowroomEntity showroomEntity) {
        try {
            em.persist(showroomEntity);
            return true;
        } catch (Exception e) {
            System.out.println("Error adding showroom: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ShowroomEntity> findAllShowroom() {
        List<ShowroomEntity> allShowrooms = em.createNamedQuery("findAllShowroom", ShowroomEntity.class).getResultList();
//        System.out.println("\nAll Showroom from Repo: " + allShowrooms);
        return allShowrooms;
    }

    @Override
    public Long countAllShowroom() {
        return em.createNamedQuery("countAllShowroom", Long.class)
                .getSingleResult();
    }

    @Override
    public ShowroomEntity findById(Integer showroomId) {
        ShowroomEntity showroom = em.find(ShowroomEntity.class, showroomId);
        if (showroom == null) {
            throw new RuntimeException("Showroom not found");
        }
        return showroom;
    }

    @Override
    public boolean existByName(String showroomName) {
        try {
            String trimmedName = showroomName.trim();
            log.debug("Checking showroom name in repo: [{}]", trimmedName);

            Long count = em.createNamedQuery("showroomNameExist", Long.class)
                    .setParameter("name", trimmedName)
                    .getSingleResult();

            log.debug("Showroom name count from repo: {}", count);
            return count > 0;
        } catch (Exception e) {
            log.error("Database error while checking showroom name existence", e);
            throw e;
        }
    }

    @Override
    public boolean existByEmail(String email) {
        try {
            System.out.println("Checking email in repo: [" + email + "]"); // Debugging

            Long count = em.createNamedQuery("showroomEmailExist", Long.class)
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
    public boolean existByPhno(String showroomPhone) {
        try {
            Long count = em.createNamedQuery("showroomPhNoExist", Long.class)
                    .setParameter("ph", showroomPhone)
                    .getSingleResult();
            return count > 0; // Return true if phone number exists, false otherwise
        } catch (Exception e) {
            log.warn("Error checking phone number existence:{}", e.getMessage());
            return false;
        }
    }

    /*@Override
    public void deleteShowroom(int id) {
        em.createNamedQuery("deleteShowroom").setParameter("id",id).executeUpdate();
    }*/
}

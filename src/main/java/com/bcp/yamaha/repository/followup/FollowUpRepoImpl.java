package com.bcp.yamaha.repository.followup;

import com.bcp.yamaha.entity.FollowUpEntity;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class FollowUpRepoImpl implements FollowUpRepository{
    @PersistenceContext
    EntityManager em;
    @Override
    public boolean save(FollowUpEntity followUpEntity) {
        try {
            if (followUpEntity.getFollowUpId() == 0) {
                em.persist(followUpEntity); // Insert new
                return true;
            } else {
                em.merge(followUpEntity); // Update existing
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error in saving followup details " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FollowUpEntity> findByUserId(int userId) {
        return em.createNamedQuery("findByUserId", FollowUpEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<FollowUpEntity> findAllFollowUps() {
        return em.createNamedQuery("findAllFollowUps", FollowUpEntity.class).getResultList();
    }

}

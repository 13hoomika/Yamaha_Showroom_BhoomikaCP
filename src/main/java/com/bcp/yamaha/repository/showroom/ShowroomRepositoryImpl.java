package com.bcp.yamaha.repository.showroom;

import com.bcp.yamaha.entity.ShowroomEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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
        return em.createNamedQuery("findAllShowroom",ShowroomEntity.class).getResultList();
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

    /*@Override
    public void deleteShowroom(int id) {
        em.createNamedQuery("deleteShowroom").setParameter("id",id).executeUpdate();
    }*/
}

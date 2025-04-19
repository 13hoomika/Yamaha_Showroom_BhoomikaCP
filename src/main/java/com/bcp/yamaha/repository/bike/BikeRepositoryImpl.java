package com.bcp.yamaha.repository.bike;

import com.bcp.yamaha.constants.ShowroomEnum;
import com.bcp.yamaha.entity.BikeEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BikeRepositoryImpl implements BikeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean addBike(BikeEntity bikeEntity) {
        try {
            em.persist(bikeEntity);
            return true;
        } catch (Exception e) {
            System.out.println("Error adding bike: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BikeEntity> findAllBikes() {
        return em.createNamedQuery("findAllBikes",BikeEntity.class).getResultList();
    }

    public Long countAllBikes() {
        return em.createNamedQuery("countAllBikes", Long.class)
                .getSingleResult();
    }

    @Override
    public List<BikeEntity> findByShowroomLocation(ShowroomEnum location) {
        return em.createNamedQuery(
                        "findByShowroomLocation", BikeEntity.class)
                .setParameter("location", location)
                .getResultList();
    }

    @Override
    public List<BikeEntity> findUnassignedBikes() {
        return em.createNamedQuery("findUnassignedBikes", BikeEntity.class).getResultList();
    }

    @Override
    public long countByShowroomId(Integer showroomId) {
        return em.createQuery("countByShowroomId", Long.class)
                .setParameter("showroomId", showroomId)
                .getSingleResult();
    }

    @Override
    public BikeEntity findById(Integer bikeId) {
        return em.find(BikeEntity.class,bikeId);
    }


}

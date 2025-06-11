package com.bcp.yamaha.repository.bike;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.entity.BikeEntity;
import com.bcp.yamaha.entity.ShowroomEntity;
import com.bcp.yamaha.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
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
    public List<BikeEntity> findByBikeType(BikeType bikeType) {
        return em.createNamedQuery(
                        "findByBikeType", BikeEntity.class)
                .setParameter("type", bikeType)
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

    @Override
    public boolean existByName(String bikeModel) {
        System.out.println("------------> inside repo: existByName()");
        try {
            String trimmedName = bikeModel.trim();
            log.debug("Checking bikeModel in repo: [{}]", trimmedName);

            Long count = em.createNamedQuery("bikeModelExist", Long.class)
                    .setParameter("bikeModel", trimmedName)
                    .getSingleResult();

            log.debug("Bike Model count from repo: {}", count);
            return count > 0;
        } catch (Exception e) {
            log.error("Database error while checking bike model existence", e);
            throw e;
        }
    }

    @Override
    public void removeBikeById(Integer bikeId) {
        BikeEntity bike = em.find(BikeEntity.class, bikeId);
        if (bike == null)
            throw new NotFoundException("Bike with ID: " + bikeId + " not found ");

        ShowroomEntity showroom = bike.getShowroomEntity();
        if (showroom != null){
            int currentCount = showroom.getBikeCount();
            showroom.setBikeCount(Math.max(0,currentCount - 1));
            em.merge(showroom);
        }
        em.remove(bike);
    }
}

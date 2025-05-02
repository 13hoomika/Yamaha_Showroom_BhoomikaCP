package com.bcp.yamaha.repository.bike;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.entity.BikeEntity;

import java.util.List;

public interface BikeRepository {

    Boolean addBike(BikeEntity bikeEntity);
    List<BikeEntity> findAllBikes();
    Long countAllBikes();

    List<BikeEntity> findByBikeType(BikeType bikeType);

    List<BikeEntity> findUnassignedBikes();
    long countByShowroomId(Integer showroomId);

    BikeEntity findById(Integer bikeId);
}

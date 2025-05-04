package com.bcp.yamaha.service.bike;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.dto.BikeDto;
import com.bcp.yamaha.entity.BikeEntity;

import java.util.List;

public interface BikeService {
    Boolean addBike(BikeDto bikeDto);
    BikeEntity getBikeById(Integer bikeId);
    List<BikeDto> getAllBikes();

    List<BikeDto> getBikesByBikeType(BikeType bikeType);
    List<BikeEntity> getUnassignedBikes();

    Boolean assignBikeToShowroom(Integer bikeId, Integer showroomId);
    Long getTotalBikeCount();
}

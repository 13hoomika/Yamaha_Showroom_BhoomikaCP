package com.bcp.yamaha.service.bike;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.dto.BikeDto;

import java.util.List;

public interface BikeService {
    Boolean addBike(BikeDto bikeDto);
//    BikeEntity getBikeById(Integer bikeId);
    List<BikeDto> getAllBikes();

    List<BikeDto> getBikesByBikeType(BikeType bikeType);
    List<BikeDto> getUnassignedBikes();

    Boolean assignBikeToShowroom(Integer bikeId, Integer showroomId);
    Long getTotalBikeCount();

    void deleteBikeById(Integer bikeId, String uploadPath);
}

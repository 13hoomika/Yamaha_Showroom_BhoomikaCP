package com.bcp.yamaha.service.bike;

import com.bcp.yamaha.constants.ShowroomEnum;
import com.bcp.yamaha.dto.BikeDto;
import com.bcp.yamaha.entity.BikeEntity;

import java.util.List;

public interface BikeService {
    Boolean addBike(BikeDto bikeDto);
    List<BikeDto> getAllBikes();
    Long getTotalBikeCount();

    List<BikeDto> getBikesByShowroomLocation(ShowroomEnum showroomLocation);
//    List<BikeDto> getBikesByShowroomName(String showroomName);
    List<BikeEntity> getUnassignedBikes();

    Boolean assignBikeToShowroom(Integer bikeId, Integer showroomId);
}

package com.bcp.yamaha.service.bike;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.dto.BikeDto;
import com.bcp.yamaha.entity.BikeEntity;
import com.bcp.yamaha.entity.ShowroomEntity;
import com.bcp.yamaha.repository.bike.BikeRepository;
import com.bcp.yamaha.repository.showroom.ShowroomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BikeServiceImpl implements BikeService{

    @Autowired
    ShowroomRepository showroomRepository;

    @Autowired
    BikeRepository bikeRepository;

    @Transactional
    @Override
    public Boolean addBike(BikeDto bikeDto) {

        try {
            // Convert DTO to Entity
            BikeEntity bikeEntity = new BikeEntity();
            BeanUtils.copyProperties(bikeDto, bikeEntity);

            /*ShowroomEntity showroom= showroomRepository.findById(bikeDto.getShowroomId());
            System.out.println("In service layer:  showroom = "+showroom );
            if(showroom == null){
                System.out.println("showroom is null!");
            }else {
                bikeEntity.setShowroomEntity(showroom);
            }*/
            // Save to database
            bikeRepository.addBike(bikeEntity);
            return true;

        } catch (Exception e) {
            log.error("Error adding bike: {}", e.getMessage());
            throw new RuntimeException("Failed to add bike", e);
        }
    }

//    @Override
//    public BikeEntity getBikeById(Integer bikeId) {
//        return bikeRepository.findById(bikeId);
//    }

    @Override
    public List<BikeDto> getAllBikes() {
        List<BikeEntity> bikeEntityList = bikeRepository.findAllBikes();
        List<BikeDto> bikeDtoList = new ArrayList<>();

        for (BikeEntity entity : bikeEntityList){
            BikeDto dto = new BikeDto();
            BeanUtils.copyProperties(entity,dto);

            if (entity.getShowroomEntity() != null) {
                dto.setAvailableInShowroom(entity.getShowroomEntity().getShowroomName());
            }

            System.out.println("dto: " + dto);
            bikeDtoList.add(dto);
        }
        return bikeDtoList;
    }

    @Override
    public List<BikeDto> getBikesByBikeType(BikeType bikeType) {
        List<BikeEntity> bikeEntityList = bikeRepository.findByBikeType(bikeType);
        List<BikeDto> bikeDtoList = new ArrayList<>();

        for (BikeEntity entity : bikeEntityList) {
            BikeDto dto = new BikeDto();
            BeanUtils.copyProperties(entity, dto);

            if (entity.getShowroomEntity() != null) {
                dto.setAvailableInShowroom(entity.getShowroomEntity().getShowroomName());
            }
            bikeDtoList.add(dto);
        }
        return bikeDtoList;
    }


    @Override
    public List<BikeEntity> getUnassignedBikes() {
        return bikeRepository.findUnassignedBikes();
    }

    @Override
    @Transactional
    public Boolean assignBikeToShowroom(Integer bikeId, Integer showroomId) {
        // Validate input parameters
        if (bikeId == null || showroomId == null) {
            System.out.println("Error: Bike ID or Showroom ID cannot be null");
            return false;
        }

        try {
            // Get bike entity with null check
            BikeEntity bike = bikeRepository.findById(bikeId);
            if (bike == null) {
                System.out.println("Error: Bike not found with ID: " + bikeId);
                return false;
            }
            System.out.println("Found bike: " + bike.getBikeModel());

            // Get showroom entity with null check
            ShowroomEntity showroom = showroomRepository.findById(showroomId);
            if (showroom == null) {
                System.out.println("Error: Showroom not found with ID: " + showroomId);
                return false;
            }
            System.out.println("Found showroom: " + showroom.getShowroomName());

            // Check bike count limit
            if (showroom.getBikeCount() >= 5) {
                System.out.println("Error: Showroom " + showroom.getShowroomName() +
                        " already has maximum 5 bikes");
                return false;
            }

            // Assign bike to showroom
//            bike.setAvailableShowroom(showroom);
            bike.setShowroomEntity(showroom);
            bikeRepository.addBike(bike);

            // Update and save bike count
            showroom.setBikeCount(showroom.getBikeCount() + 1);
            showroomRepository.addShowroom(showroom);

            System.out.println("Successfully assigned bike " + bike.getBikeModel() +
                    " to showroom " + showroom.getShowroomName());
            return true;

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            log.error("error !!{}", String.valueOf(e));
            return false;
        }
    }

    public Long getTotalBikeCount() {
        return bikeRepository.countAllBikes();
    }
}

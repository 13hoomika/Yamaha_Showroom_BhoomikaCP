package com.bcp.yamaha.service.bike;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.dto.BikeDto;
import com.bcp.yamaha.entity.BikeEntity;
import com.bcp.yamaha.entity.ShowroomEntity;
import com.bcp.yamaha.exception.NotFoundException;
import com.bcp.yamaha.repository.bike.BikeRepository;
import com.bcp.yamaha.repository.showroom.ShowroomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class BikeServiceImpl implements BikeService{

    @Autowired
    ShowroomRepository showroomRepository;

    @Autowired
    BikeRepository bikeRepository;

    @Override
    public Boolean addBike(BikeDto bikeDto) {

        try {
            // Convert DTO to Entity
            BikeEntity bikeEntity = new BikeEntity();
            BeanUtils.copyProperties(bikeDto, bikeEntity);

            // Save to database
            bikeRepository.addBike(bikeEntity);
            return true;

        } catch (Exception e) {
            log.error("Error adding bike: {}", e.getMessage());
            throw new RuntimeException("Failed to add bike", e);
        }
    }

    /*@Override
    public BikeEntity getBikeById(Integer bikeId) {
        return bikeRepository.findById(bikeId);
    }*/

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
    public List<BikeDto> getUnassignedBikes() {
        List<BikeEntity> bikeEntityList = bikeRepository.findUnassignedBikes();
        List<BikeDto> bikeDtoList = new ArrayList<>();
        for (BikeEntity entity : bikeEntityList) {
            BikeDto dto = new BikeDto();
            BeanUtils.copyProperties(entity, dto);
            bikeDtoList.add(dto);
        }
        return bikeDtoList;
    }

    @Override
    public Boolean assignBikeToShowroom(Integer bikeId, Integer showroomId) {
        // Validate input parameters
        if (bikeId == null || showroomId == null) {
            log.error("Bike ID or Showroom ID cannot be null");
            return false;
        }

        try {
            // Get bike entity with null check
            BikeEntity bike = bikeRepository.findById(bikeId);
            if (bike == null) throw new NotFoundException("Bike with ID: " + bikeId +" not found");

            // Get showroom entity with null check
            ShowroomEntity showroom = showroomRepository.findById(showroomId);
            if (showroom == null) throw new NotFoundException("Showroom with ID: " + showroomId +" not found");

            // Check bike count limit
            if (showroom.getBikeCount() >= 5) {
                System.out.println(showroom.getShowroomName() + "Showroom " +
                        " already has maximum 5 bikes");
                return false;
            }

            // Assign bike to showroom
            bike.setShowroomEntity(showroom);
            bikeRepository.addBike(bike);

            // Update and save bike count
            showroom.setBikeCount(showroom.getBikeCount() + 1);
            showroomRepository.addShowroom(showroom);

            log.info("Successfully assigned bike {} to showroom {}", bike.getBikeModel(), showroom.getShowroomName());
            return true;
        } catch (Exception e) {
            log.error("Unexpected error while assigning bike to showroom {}", e.getMessage());
            return false;
        }
    }

    public Long getTotalBikeCount() {
        return bikeRepository.countAllBikes();
    }

    @Override
    public boolean existByBikeModel(String bikeModel) {
        System.out.println("checking bikeModel existence in service..........");
        boolean exists = bikeRepository.existByName(bikeModel);
        log.info("is existByBikeModel in service: {}", exists);
        return exists;
    }

    @Override
    public void deleteBikeById(Integer bikeId, String uploadPath) {
        BikeEntity bike = bikeRepository.findById(bikeId);
        if (bike == null) throw new NotFoundException("Bike with ID: " + bikeId +" not found");

        if (bike.getImages() != null){
            for (String imageName : bike.getImages()){
                if (imageName == null || imageName.trim().isEmpty()) continue;

                File imageFile = new File(uploadPath, imageName);
                if (imageFile.exists()){
                    boolean deleted = imageFile.delete();
                    if (!deleted){
                        log.warn("Failed to delete image file: {}", imageFile.getAbsolutePath());
                    }
                    log.info("Deleted image: {}", imageFile.getAbsolutePath());
                }else {
                    log.warn("Image file not found: {}", imageFile.getAbsolutePath());
                }
            }
        }
        bikeRepository.removeBikeById(bikeId);
    }

}

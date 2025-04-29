package com.bcp.yamaha.service.bike;

import com.bcp.yamaha.constants.ShowroomEnum;
import com.bcp.yamaha.dto.BikeDto;
import com.bcp.yamaha.entity.BikeEntity;
import com.bcp.yamaha.entity.BikeImageEntity;
import com.bcp.yamaha.entity.ShowroomEntity;
import com.bcp.yamaha.repository.bike.BikeRepository;
import com.bcp.yamaha.repository.showroom.ShowroomRepository;
import com.bcp.yamaha.repository.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class BikeServiceImpl implements BikeService{
    @Autowired
    ShowroomRepository showroomRepository;

    @Autowired
    BikeRepository bikeRepository;

    @Transactional
    @Override
    public Boolean addBike(BikeDto bikeDto) {
        try {
            BikeEntity bikeEntity = new BikeEntity();
            BeanUtils.copyProperties(bikeDto, bikeEntity);

            List<BikeImageEntity> imageEntities = new ArrayList<>();
            for (MultipartFile file : bikeDto.getBikeImages()) {
                String imagePath = saveImageToDisk(file, bikeDto.getBikeModel());
                BikeImageEntity image = new BikeImageEntity();
                image.setImageUrl(imagePath);
                image.setBike(bikeEntity);
                imageEntities.add(image);
            }
            bikeEntity.setBikeImages(imageEntities);

            return bikeRepository.addBike(bikeEntity);
        } catch (Exception e) {
            System.out.println("Error adding bike: " + e.getMessage());
            throw new RuntimeException("Failed to add bike", e);
        }
    }

    private String saveImageToDisk(MultipartFile file, String modelName) {
        try {
            String uploadFolder = "D:/06 GO19ROM Aug19/Project Phase/BikeShowroom Project draft/Yamaha_Showroom_BhoomikaCP/src/main/webapp/static/upload/";
            String extension = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf('.'));
            String cleanModelName = modelName.replaceAll("\\s+", "_");
            String fileName = cleanModelName + "_" + System.currentTimeMillis() + extension;

            Path filePath = Paths.get(uploadFolder + fileName);
            Files.write(filePath, file.getBytes());

            return "/uploads/" + fileName; // relative path stored in DB
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save image: " + file.getOriginalFilename());
        }
    }

    @Override
    public List<BikeDto> getAllBikes() {
        List<BikeEntity> bikeEntityList = bikeRepository.findAllBikes();
        List<BikeDto> bikeDtoList = new ArrayList<>();
        for (BikeEntity entity : bikeEntityList){
            BikeDto dto = new BikeDto();
            BeanUtils.copyProperties(entity,dto);
            /*if (entity.getAvailableShowroom() != null) {
                dto.setShowroomLocation(entity.getAvailableShowroom().getShowroomLocation());
            }*/
            if (entity.getAvailableInShowroom() != null) {
                dto.setAvailableInShowroom(entity.getAvailableInShowroom().getShowroomName());
            }
            bikeDtoList.add(dto);
        }
        return bikeDtoList;
    }

    public Long getTotalBikeCount() {
        return bikeRepository.countAllBikes();
    }



    @Override
    public List<BikeDto> getBikesByShowroomLocation(ShowroomEnum location) {
        List<BikeEntity> bikeEntityList = bikeRepository.findByShowroomLocation(location);
        List<BikeDto> bikeDtoList = new ArrayList<>();

        for (BikeEntity entity : bikeEntityList) {
            BikeDto dto = new BikeDto();
            BeanUtils.copyProperties(entity, dto);
            // Manually copy any nested/complex properties
            /*if (entity.getAvailableShowroom() != null) {
                dto.setShowroomLocation(entity.getAvailableShowroom().getShowroomLocation());
            }*/

            if (entity.getAvailableInShowroom() != null) {
                dto.setAvailableInShowroom(entity.getAvailableInShowroom().getShowroomName());
            }
            bikeDtoList.add(dto);
        }

        return bikeDtoList;
    }

   /* @Override
    public List<BikeDto> getBikesByShowroomLocation(ShowroomEnum location) {
        return bikeRepository.findByShowroomLocation(location).stream()
                .map(entity -> {
                    BikeDto dto = new BikeDto();
                    // Manual property mapping
                    dto.setBikeId(entity.getBikeId());
                    dto.setBikeModel(entity.getBikeModel());
                    // ... other properties
                    return dto;
                })
                .collect(Collectors.toList());
    }*/

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
            bike.setAvailableInShowroom(showroom);
            bikeRepository.addBike(bike);

            // Update and save bike count
            showroom.setBikeCount(showroom.getBikeCount() + 1);
            showroomRepository.addShowroom(showroom);

            System.out.println("Successfully assigned bike " + bike.getBikeModel() +
                    " to showroom " + showroom.getShowroomName());
            return true;

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

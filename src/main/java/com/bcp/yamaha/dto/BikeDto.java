package com.bcp.yamaha.dto;

import com.bcp.yamaha.constants.BikeType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BikeDto {
    private Integer bikeId;
    private String bikeModel;
    private BikeType bikeType;
    private String bikePrice;
    private String bikeYear;
    private String bikeColor;
    private String bikeDescription;

    private String mileage;
    private Double fuelTankCapacity;
    private int engineCapacity;

    /*private List<MultipartFile> bikeImages;
    private List<String> bikeImageUrls;*/

    private int showroomId;
    private String availableInShowroom;

    private List<String> images;
    private List<MultipartFile> multipartFileList; // used in controller

}

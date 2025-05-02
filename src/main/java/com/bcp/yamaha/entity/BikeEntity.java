package com.bcp.yamaha.entity;
import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.constants.ShowroomEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "yamaha_bikes")
@NamedQueries({
//        @NamedQuery(name = "findAllBikes", query = "FROM BikeEntity"),
        @NamedQuery(name = "findAllBikes", query = "SELECT DISTINCT b FROM BikeEntity b LEFT JOIN FETCH b.availableShowroomId LEFT JOIN FETCH b.bikeImages"),
        @NamedQuery(name = "countAllBikes", query = "SELECT COUNT(bikes) FROM BikeEntity bikes"),
        @NamedQuery(name = "findByBikeType",query = "SELECT b FROM BikeEntity b WHERE b.bikeType = :type"),
        @NamedQuery(name = "findUnassignedBikes",query = "SELECT b FROM BikeEntity b WHERE b.availableShowroomId IS NULL"),
        @NamedQuery(name = "countByShowroomId",query = "SELECT COUNT(b) FROM BikeEntity b WHERE b.availableShowroomId.showroomId = :showroomId")
})


public class BikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bikeId;
    private String bikeModel;

    @Enumerated(EnumType.STRING)
    private BikeType bikeType;

    private String bikePrice;
    private String bikeYear;
    private String bikeColor;

    @Column(name = "bikeDescription", length = 2000)
    private String bikeDescription;

    private String mileage;
    private Double fuelTankCapacity;
    private int engineCapacity;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BikeImageEntity> bikeImages;

    @ManyToOne
    @JoinColumn(name = "showroom_id")
    private ShowroomEntity availableShowroomId;


}

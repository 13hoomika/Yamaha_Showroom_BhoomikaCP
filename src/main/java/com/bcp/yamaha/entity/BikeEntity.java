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
        @NamedQuery(name = "findAllBikes", query = "SELECT DISTINCT b FROM BikeEntity b LEFT JOIN FETCH b.availableShowroom LEFT JOIN FETCH b.bikeImages"),
        @NamedQuery(name = "countAllBikes", query = "SELECT COUNT(bikes) FROM BikeEntity bikes"),
        @NamedQuery(name = "findByShowroomLocation",query = "SELECT b FROM BikeEntity b WHERE b.availableShowroom.showroomLocation = :location"),
        @NamedQuery(name = "findUnassignedBikes",query = "SELECT b FROM BikeEntity b WHERE b.availableShowroom IS NULL"),
        @NamedQuery(name = "countByShowroomId",query = "SELECT COUNT(b) FROM BikeEntity b WHERE b.availableShowroom.showroomId = :showroomId")
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

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BikeImageEntity> bikeImages;

    @ManyToOne
    @JoinColumn(name = "showroom_id")
    private ShowroomEntity availableShowroom;

}

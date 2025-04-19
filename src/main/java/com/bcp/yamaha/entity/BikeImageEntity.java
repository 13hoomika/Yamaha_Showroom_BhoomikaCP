package com.bcp.yamaha.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(exclude = "bike")  // 👈 prevent circular reference
@Table(name = "bike_images")
public class BikeImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imageUrl; // Store path like "/uploads/modelname_timestamp.jpg"

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private BikeEntity bike;
}

/* stack overflow
 * circular references between BikeEntity and BikeImageEntity in your @OneToMany / @ManyToOne relationship,
 * and we i used Lombok’s @ToString on both classes.
 * What’s happens: BikeEntity.toString() calls → BikeImageEntity.toString() → BikeEntity.toString() again  → stack overflow 🔥
 * ✅ To Prevent infinite recursion in @ToString
 * Use the exclude option in Lombok to exclude the back-reference (bike) in BikeImageEntity.
 */



package com.bcp.yamaha.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "bikes")
@Table(name = "yamaha_showroom")
@NamedQueries({
        @NamedQuery(name = "findAllShowroom", query = "SELECT DISTINCT s FROM ShowroomEntity s LEFT JOIN FETCH s.bikes"),
        @NamedQuery(name = "countAllShowroom", query = "SELECT COUNT(showroom) FROM ShowroomEntity showroom"),
//        @NamedQuery(name = "deleteShowroom", query = "DELETE FROM ShowroomEntity s WHERE s.showroomId = :id"),
})
public class ShowroomEntity extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showroomId;
    private String showroomName;

    private String showroomAddress;
    private String showroomPhone;
    private String showroomEmail;
    private String showroomManager;

    /*@OneToMany(mappedBy = "availableShowroom")
    private List<BikeEntity> bikes = new ArrayList<>();*/

    @OneToMany(mappedBy = "showroomEntity")
    private List<BikeEntity> bikes = new ArrayList<>();

    @Column(nullable = false)
    private int bikeCount;

    private String showroomImg;

}

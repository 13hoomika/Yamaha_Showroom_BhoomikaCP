package com.bcp.yamaha.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = "user")
@Table(name = "yamaha_followup")
@NamedQueries({
    @NamedQuery(name = "findByUserId", query = "SELECT f FROM FollowUpEntity f WHERE f.user.userId = :userId ORDER BY f.followupDate DESC"),
    @NamedQuery(name = "findAllFollowUps", query = "SELECT f FROM FollowUpEntity f JOIN FETCH f.user")

})
public class FollowUpEntity extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "followup_id")
    private int followUpId;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToOne(fetch = FetchType.LAZY) // Changed cascade and added fetch type
    @JoinColumn(name = "userId",  referencedColumnName = "userId")
    private UserEntity user;

    @Column(name = "followup_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate followupDate;

    @Column(name = "call_status")
    private String callStatus;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "reschedule_date")
//    private LocalDateTime rescheduleDate;

}
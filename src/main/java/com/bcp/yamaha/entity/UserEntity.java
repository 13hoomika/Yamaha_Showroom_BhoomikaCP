package com.bcp.yamaha.entity;

import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.constants.ScheduleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "yamaha_user")
@NamedQueries({
        @NamedQuery(name = "findAllUsers", query = "FROM UserEntity"),
        @NamedQuery(name = "countAllUsers", query = "SELECT COUNT(users) FROM UserEntity users"),
        @NamedQuery(name = "findUserByName", query = "SELECT u FROM UserEntity u WHERE u.userName =: userName"),
        @NamedQuery(name = "findUserByEmail", query = "SELECT u FROM UserEntity u WHERE u.userEmail =: userEmail"),
        @NamedQuery(name = "findByScheduleType", query = "SELECT u FROM UserEntity u WHERE u.scheduleType =: scheduleType"),
        @NamedQuery(name = "updatePassword", query = "UPDATE UserEntity u SET u.password = :password WHERE u.userEmail = :email"),
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String userEmail;
    private int userAge;
    private String userAddress;
    private String drivingLicenseNumber;
    private String userPhoneNumber;

    @Enumerated(EnumType.STRING)
    private BikeType bikeType;

    @Enumerated(EnumType.ORDINAL)
    private ScheduleType scheduleType ;

    private LocalDate scheduleDate;
    private LocalTime scheduleTime;

    private Integer showroomId;
    private String showroomName;

    @OneToMany(mappedBy = "user")
    private List<FollowUpEntity> followUpLogs = new ArrayList<>();

    private String password;

    /*@Column(name = "invalid_logInCount")
    private int invalidLogInCount;
    @Column(name = "account_locked")
    private boolean accountLocked = false;
    @Column(name="last_logIn", columnDefinition = "TIMESTAMP(6)")
    private Instant lastLogIn;*/
}

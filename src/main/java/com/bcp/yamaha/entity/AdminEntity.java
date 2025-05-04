package com.bcp.yamaha.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "yamaha_admin")
@NamedQueries({
        @NamedQuery(name = "findByName", query = "SELECT a FROM AdminEntity a WHERE a.adminName = :adminName"),
        @NamedQuery(name = "findByEmail", query = "SELECT a FROM AdminEntity a WHERE a.adminEmail = :adminEmail"),
})

public class AdminEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;
    private String adminName;
    private String adminEmail;

    private String adminOtp;
//    @Column(nullable = true)
    private LocalDateTime otpGeneratedTime;

}

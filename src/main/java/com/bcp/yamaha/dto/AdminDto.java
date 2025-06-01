package com.bcp.yamaha.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDto {
    private int adminId;
    private String adminName;
    private String adminEmail;
//    private String adminPassword;
    private String adminOtp;
}

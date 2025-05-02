package com.bcp.yamaha.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowroomDto {
    private Integer showroomId;
    private String showroomName;
    private String showroomAddress;
    private String showroomPhone;
    private String showroomEmail;
    private String showroomManager;
    private int bikeCount;

    private String showroomImg;
}

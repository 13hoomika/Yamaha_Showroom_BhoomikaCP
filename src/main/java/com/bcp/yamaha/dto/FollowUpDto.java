package com.bcp.yamaha.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FollowUpDto {
    private int followUpId;
    private int userId;
    private String userName;
    private LocalDateTime followupDate;
    private String callStatus;
    @Size(max = 1000)
    private String notes;
//    private LocalDateTime rescheduleDate;
}

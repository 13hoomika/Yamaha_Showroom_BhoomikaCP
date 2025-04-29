package com.bcp.yamaha.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd") // format for HTML date input
    private LocalDate followupDate;

    private String callStatus;
    @Size(max = 1000)
    private String notes;
//    private LocalDateTime rescheduleDate;
}

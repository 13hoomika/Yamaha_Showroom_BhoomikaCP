package com.bcp.yamaha.dto;


import com.bcp.yamaha.constants.BikeType;
import com.bcp.yamaha.constants.ScheduleType;
import com.bcp.yamaha.constants.ShowroomEnum;
import com.bcp.yamaha.entity.ShowroomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private int userId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3-50 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String userEmail;

    @Min(value = 18, message = "Must be at least 18 years old")
    @Max(value = 100, message = "Age must be reasonable")
    private int userAge;

    @NotBlank(message = "Address is required")
    private String userAddress;

    @NotNull(message = "Driver's license number is required")
    @Pattern(regexp = "^[A-Z]{2}\\d{2}\\d{4}\\d{7}$", message = "Invalid driver's license number")
    private String drivingLicenseNumber;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number")
    private String userPhoneNumber;

    private BikeType bikeType;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @Enumerated(EnumType.STRING)
    private ShowroomEnum showroomLocation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime scheduleTime;

    private Integer showroomId;
    private String showroomName;
}

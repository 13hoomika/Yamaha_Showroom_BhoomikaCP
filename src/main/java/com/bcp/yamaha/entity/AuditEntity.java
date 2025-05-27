package com.bcp.yamaha.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class AuditEntity {
    @CreationTimestamp
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime lastModifiedDateTime;
}

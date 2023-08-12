package com.tonio.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue
    @Column(length = 36, columnDefinition = "varchar", unique = true, updatable = false, nullable = false)
    private UUID id;
    private String customerName;

    @Version
    private Integer version;
    private LocalDate createdDate;
    private LocalDate lastDateModified;
}

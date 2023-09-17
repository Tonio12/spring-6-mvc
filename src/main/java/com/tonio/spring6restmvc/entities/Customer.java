package com.tonio.spring6restmvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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
    @Column(length = 36, columnDefinition = "varchar(36)", unique = true, updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String customerName;

    @Column
    private String email;

    @Version
    private Integer version;
    private LocalDate createdDate;
    private LocalDate lastDateModified;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private Set<BeerOrder> beerOrders = new HashSet<>();
}

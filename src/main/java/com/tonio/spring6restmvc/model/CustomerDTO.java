package com.tonio.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Builder
@Data
public class CustomerDTO {
    @NotNull
    @NotBlank
    private String customerName;
    private UUID id;
    private String  version;
    private LocalDate createdDate;
    private LocalDate lastDateModified;
}

package com.example.demo.api.dto;

import lombok.*;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO {

    @NonNull
    private Long idManuFact;

    @NonNull
    private String nameManuFact;
}

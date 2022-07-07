package com.example.demo.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO {

    @NonNull
    private Long idManuFact;

    @NonNull
    private String nameManuFact;
}

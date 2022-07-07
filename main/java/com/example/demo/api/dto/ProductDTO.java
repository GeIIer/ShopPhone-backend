package com.example.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NonNull
    private Long idProduct;

    @NonNull
    private String nameProduct;

    private String characteristic;
    private String description;

    @JsonProperty("manufacturer")
    private String manufacturerDTO;
    @JsonProperty("category")
    private String categoryDTO;

    private double price;

}

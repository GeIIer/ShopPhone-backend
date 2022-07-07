package com.example.demo.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @NonNull
    private Long idCategory;

    @NonNull
    private String nameCategory;
}

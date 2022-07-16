package com.example.demo.api.dto;

import com.example.demo.store.entities.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Collection;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NonNull
    private Long idOrder;
    @NonNull
    private Collection<ProductDTO> productEntities;
    @NonNull
    private double totalPrice;

}

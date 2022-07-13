package com.example.demo.api.factory;

import com.example.demo.api.dto.OrderDTO;
import com.example.demo.store.entities.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFactory {
    @Autowired
    private ProductDTOFactory productDTOFactory;

    public OrderDTO makeOrderDTO (OrderEntity entity) {

        return OrderDTO.builder()
                .idOrder(entity.getIdOrder())
                .productEntities(entity.
                        getProductEntities()
                        .stream()
                        .map(productDTOFactory::makeProductDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}

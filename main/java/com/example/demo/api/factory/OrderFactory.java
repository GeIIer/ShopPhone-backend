package com.example.demo.api.factory;

import com.example.demo.api.dto.OrderDTO;
import com.example.demo.store.entities.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderFactory {

    public OrderDTO makeOrderDTO (OrderEntity entity) {

        return OrderDTO.builder()
                .idOrder(entity.getIdOrder())
                .productEntities(entity.getProductEntities())
                .email(entity.getAccount().getEmail())
                .build();
    }
}

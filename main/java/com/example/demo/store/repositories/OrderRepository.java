package com.example.demo.store.repositories;

import com.example.demo.store.entities.OrderEntity;
import com.example.demo.store.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAll();

    OrderEntity save(OrderEntity orderEntity);

    @Override
    long count();
}

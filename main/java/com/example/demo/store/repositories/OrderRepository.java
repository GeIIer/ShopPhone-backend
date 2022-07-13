package com.example.demo.store.repositories;

import com.example.demo.store.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByAccount_Id(Long account);

    boolean existsByIdOrder(Long id);

    OrderEntity save(OrderEntity orderEntity);

    @Override
    long count();
}

package com.example.demo.store.repositories;

import com.example.demo.store.entities.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {
    List<ManufacturerEntity> findAll();
}

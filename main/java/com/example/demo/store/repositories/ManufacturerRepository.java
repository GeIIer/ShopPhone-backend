package com.example.demo.store.repositories;

import com.example.demo.store.entities.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {
    List<ManufacturerEntity> findAll();
}

package com.example.demo.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.store.entities.СategoryEntity;

import java.util.List;

public interface CategoryRepository extends JpaRepository<СategoryEntity, Long>{
    List<СategoryEntity> findAll();
}

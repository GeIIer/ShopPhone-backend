package com.example.demo.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.store.entities.СategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<СategoryEntity, Long>{
    List<СategoryEntity> findAll();
    СategoryEntity findByNameCategory(String name);
}
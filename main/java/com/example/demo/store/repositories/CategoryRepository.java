package com.example.demo.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.store.entities.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
    List<CategoryEntity> findAll();
    CategoryEntity findByNameCategory(String name);

    boolean existsByNameCategory(String name);
}
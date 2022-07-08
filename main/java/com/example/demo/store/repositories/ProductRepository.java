package com.example.demo.store.repositories;

import com.example.demo.store.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();
    List<ProductEntity> findAllByCategory_IdCategory(Long category_idCategory);
}

package com.codewithyugam.ecom.repositories;

import com.codewithyugam.ecom.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(byte categoryId);
}
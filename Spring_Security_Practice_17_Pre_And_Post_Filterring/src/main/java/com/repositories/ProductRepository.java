package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.Product;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:text% AND p.owner=?#{authentication.principal.username}")
    List<Product> findProductByNameContains(String text);
}

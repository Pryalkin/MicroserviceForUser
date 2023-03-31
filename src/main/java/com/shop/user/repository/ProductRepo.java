package com.shop.user.repository;

import com.shop.user.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findByProdId(String productId);
    Optional<Set<Product>> findByRegistered(Boolean flag);
}

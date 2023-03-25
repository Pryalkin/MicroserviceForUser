package com.shop.user.repository;

import com.shop.user.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(String productId);
    Optional<Set<Product>> findByRegistered(Boolean flag);
//    @Transactional(readOnly = true)
//    @Query(nativeQuery = true, value = "select price from product where productId = ?")
//    Double findPrice(String productId);
}

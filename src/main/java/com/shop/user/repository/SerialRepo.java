package com.shop.user.repository;

import com.shop.user.model.product.Serial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SerialRepo extends JpaRepository<Serial, Long> {

    Set<Serial> findByProductProdId(String prodId);
    Optional<Serial> findByProductNumber(String productNumber);
}
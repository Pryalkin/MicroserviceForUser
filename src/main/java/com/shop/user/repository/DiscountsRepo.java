package com.shop.user.repository;

import com.shop.user.model.product.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountsRepo extends JpaRepository<Discounts, Long> {
}

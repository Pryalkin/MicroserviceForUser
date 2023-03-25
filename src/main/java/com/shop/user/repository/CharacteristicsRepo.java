package com.shop.user.repository;

import com.shop.user.model.product.Characteristics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicsRepo extends JpaRepository<Characteristics, Long> {
}

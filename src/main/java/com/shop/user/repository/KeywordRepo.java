package com.shop.user.repository;

import com.shop.user.model.product.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepo extends JpaRepository<Keyword, Long> {
}

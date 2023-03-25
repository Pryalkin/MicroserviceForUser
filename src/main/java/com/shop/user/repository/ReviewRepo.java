package com.shop.user.repository;

import com.shop.user.model.product.Evaluation;
import com.shop.user.model.product.Product;
import com.shop.user.model.product.Review;
import com.shop.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    Optional<Review> findByProductOrganizationUser(User user);
}

package com.shop.user.repository;

import com.shop.user.model.product.Evaluation;
import com.shop.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findByProductOrganizationUser(User user);
}

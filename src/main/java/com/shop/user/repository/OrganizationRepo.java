package com.shop.user.repository;

import com.shop.user.model.user.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {

    Optional<Organization> findByName(String name);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "select * from organization where name = ? and activity = 'ACTIVE'")
    Optional<Organization> findActiveOrganization(String name);
    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "select * from organization where product_id = ?")
    Optional<Organization> findByProductsAndId(Long productId);

}

package com.shop.user.repository;

import com.shop.user.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

    Optional<Set<Notification>> findByUserUsername(String username);
}

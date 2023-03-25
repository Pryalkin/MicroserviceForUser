package com.shop.user.service.impl;

import com.shop.user.exception.model.UserNotFoundException;
import com.shop.user.model.Notification;
import com.shop.user.repository.NotificationRepo;
import com.shop.user.repository.UserRepo;
import com.shop.user.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.shop.user.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepo userRepo;
    private final NotificationRepo notificationRepo;

    @Override
    public Set<Notification> get(String username) {
        return notificationRepo.findByUserUsername(username).get();
    }
}

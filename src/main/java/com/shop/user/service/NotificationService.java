package com.shop.user.service;

import com.shop.user.model.Notification;

import java.util.Set;

public interface NotificationService {
    Set<Notification> get(String username);
}

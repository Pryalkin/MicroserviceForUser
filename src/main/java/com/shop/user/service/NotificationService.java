package com.shop.user.service;

import com.shop.user.dto.NotificationDTO;

import java.util.Set;

public interface NotificationService {
    Set<NotificationDTO> get(String username);
}

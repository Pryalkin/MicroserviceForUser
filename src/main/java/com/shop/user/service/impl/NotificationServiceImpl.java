package com.shop.user.service.impl;

import com.shop.user.dto.NotificationDTO;
import com.shop.user.repository.NotificationRepo;
import com.shop.user.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;

    @Override
    public Set<NotificationDTO> get(String username) {
        return notificationRepo.findByUserUsername(username).orElse(new HashSet<>())
                .stream().map(notification -> {
                    NotificationDTO notificationDto = new NotificationDTO();
                    notificationDto.setHeader(notification.getHeader());
                    notificationDto.setNotification(notification.getNotification());
                    notificationDto.setDateOfCreation(notification.getDateOfCreation());
                    return notificationDto;
                }).collect(Collectors.toSet());
    }
}

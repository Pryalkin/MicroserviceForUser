package com.shop.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {

    private String header;
    private LocalDateTime dateOfCreation;
    private String notification;

}

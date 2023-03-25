package com.shop.user.controller;

import com.shop.user.model.Notification;
import com.shop.user.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/get/{username}")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public ResponseEntity<Set<Notification>> get(@PathVariable String username){
        return new ResponseEntity<>(notificationService.get(username), OK);
    }
}

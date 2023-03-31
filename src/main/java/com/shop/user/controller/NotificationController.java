package com.shop.user.controller;

import com.shop.user.dto.NotificationDTO;
import com.shop.user.exception.ExceptionHandling;
import com.shop.user.exception.model.NoRightException;
import com.shop.user.service.NotificationService;
import com.shop.user.utility.JWTTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.shop.user.controller.security.ValidUsernameSecurity.checkUsernameForValidity;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController extends ExceptionHandling {
    private final NotificationService notificationService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/get/{username}")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public ResponseEntity<Set<NotificationDTO>> get(@PathVariable String username,
                                                    HttpServletRequest request) throws NoRightException {
        checkUsernameForValidity(request, jwtTokenProvider, username);
        return new ResponseEntity<>(notificationService.get(username), OK);
    }
}

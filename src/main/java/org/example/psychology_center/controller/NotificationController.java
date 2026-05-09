package org.example.psychology_center.controller;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.config.CustomUserDetails;
import org.example.psychology_center.dao.entity.Notification;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Page<Notification>> getUserNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                notificationService.getUserNotifications(
                        userDetails.getUser(),
                        pageable
                )
        );
    }

    @GetMapping("/unread")
    public ResponseEntity<Page<Notification>> getUnread(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                notificationService.getUnreadNotifications(userDetails.getUser(), pageable)
        );
    }

}
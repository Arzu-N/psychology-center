package org.example.psychology_center.controller;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Notification;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.service.NotificationService;
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
    private final UserRepository userRepository;

    // test üçün userId ilə
    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return notificationService.getUserNotifications(user);
    }
    @GetMapping("/unread/{userId}")
    public List<Notification> getUnread(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return notificationService.getUnreadNotifications(user);
    }
}
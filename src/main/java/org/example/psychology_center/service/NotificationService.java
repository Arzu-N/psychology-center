package org.example.psychology_center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dao.entity.Notification;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(User user, String message) {

        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
@Transactional
public Page<Notification> getUserNotifications(User user, Pageable pageable) {

    Page<Notification> page =
            notificationRepository.findByUser(user, pageable);

    page.getContent().forEach(n -> {
        if (!n.isRead()) {
            n.setRead(true);
        }
    });

    notificationRepository.saveAll(page.getContent());

    return page;
}
    @Transactional
    public Page<Notification> getUnreadNotifications(User user, Pageable pageable) {

        Page<Notification> page =
                notificationRepository.findByUserAndIsReadFalse(user, pageable);

        List<Long> ids = page.getContent()
                .stream()
                .map(Notification::getId)
                .toList();

        notificationRepository.markAsRead(ids);

        return page;
    }
}
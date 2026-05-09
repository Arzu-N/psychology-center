package org.example.psychology_center.dao.repository;

import org.example.psychology_center.dao.entity.Notification;
import org.example.psychology_center.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUser(User user,Pageable pageable);



    Page<Notification> findByUserAndIsReadFalse(User user, Pageable pageable);
    @Modifying
    @Query("""
UPDATE Notification n
SET n.isRead = true
WHERE n.id IN :ids
""")
    void markAsRead(@Param("ids") List<Long> ids);
}
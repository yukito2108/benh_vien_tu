package com.tuannq.store.dao;

import com.tuannq.store.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select N from Notification N join N.users u where u.id = :usersId and N.isRead=false")
    List<Notification> getAllUnreadNotifications(@Param("usersId") Long usersId);
}

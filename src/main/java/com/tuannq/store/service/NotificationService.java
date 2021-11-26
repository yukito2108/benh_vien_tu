package com.tuannq.store.service;

import com.tuannq.store.entity.*;
import com.tuannq.store.entity.Users;

import java.util.List;

public interface NotificationService {

    void newNotification(String title, String message, String url, Users users);

    void markAsRead(Long notificationId, Long usersId);

    void markAllAsRead(Long usersId);

    Notification getNotificationById(Long notificationId);

    List<Notification> getAll(Long usersId);

    List<Notification> getUnreadNotifications(Long usersId);

    void newAppointmentFinishedNotification(Appointment appointment, boolean sendEmail);

    void newAppointmentRejectionRequestedNotification(Appointment appointment, boolean sendEmail);

    void newNewAppointmentScheduledNotification(Appointment appointment, boolean sendEmail);

    void newAppointmentCanceledByCustomerNotification(Appointment appointment, boolean sendEmail);

    void newAppointmentCanceledByProviderNotification(Appointment appointment, boolean sendEmail);

    void newAppointmentRejectionAcceptedNotification(Appointment appointment, boolean sendEmail);

    void newChatMessageNotification(ChatMessage chatMessage, boolean sendEmail);

    void newInvoice(Invoice invoice, boolean sendEmail);

    void newExchangeRequestedNotification(Appointment oldAppointment, Appointment newAppointment, boolean sendEmail);

    void newExchangeAcceptedNotification(ExchangeRequest exchangeRequest, boolean sendEmail);

    void newExchangeRejectedNotification(ExchangeRequest exchangeRequest, boolean sendEmail);
}

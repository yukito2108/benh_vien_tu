package com.tuannq.store.service.impl;

import com.tuannq.store.dao.NotificationRepository;
import com.tuannq.store.entity.*;
import com.tuannq.store.entity.Users;
import com.tuannq.store.service.EmailService;
import com.tuannq.store.service.NotificationService;
import com.tuannq.store.service.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UsersService usersService;
    private final EmailService emailService;
    private final boolean mailingEnabled;

    public NotificationServiceImpl(@Value("${mailing.enabled}") boolean mailingEnabled, NotificationRepository notificationRepository, UsersService usersService, EmailService emailService) {
        this.mailingEnabled = mailingEnabled;
        this.notificationRepository = notificationRepository;
        this.usersService = usersService;
        this.emailService = emailService;
    }

    @Override
    public void newNotification(String title, String message, String url, Users users) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setUrl(url);
        notification.setCreatedAt(new Date());
        notification.setMessage(message);
        notification.setUsers(users);
        notificationRepository.save(notification);
    }


    @Override
    public void markAsRead(Long notificationId, Long usersId) {
        Notification notification = notificationRepository.getOne(notificationId);
        if (notification.getUsers().getId() == usersId) {
            notification.setRead(true);
            notificationRepository.save(notification);
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }

    @Override
    public void markAllAsRead(Long usersId) {
        List<Notification> notifications = notificationRepository.getAllUnreadNotifications(usersId);
        for (Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.getOne(notificationId);
    }

    @Override
    public List<Notification> getAll(Long usersId) {
        return usersService.getUsersById(usersId).getNotifications();
    }

    @Override
    public List<Notification> getUnreadNotifications(Long usersId) {
        return notificationRepository.getAllUnreadNotifications(usersId);
    }

    @Override
    public void newAppointmentFinishedNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Finished";
        String message = "Appointment finished, you can reject that it took place until " + appointment.getEnd().plusHours(24).toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentFinishedNotification(appointment);
        }

    }

    @Override
    public void newAppointmentRejectionRequestedNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Rejected";
        String message = appointment.getCustomer().getFirstName() + " " + appointment.getCustomer().getLastName() + "rejected an appointment. Your approval is required";
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getProvider());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentRejectionRequestedNotification(appointment);
        }
    }

    @Override
    public void newNewAppointmentScheduledNotification(Appointment appointment, boolean sendEmail) {
        String title = "New appointment scheduled";
        String message = "New appointment scheduled with" + appointment.getCustomer().getFirstName() + " " + appointment.getProvider().getLastName() + " on " + appointment.getStart().toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getProvider());
        if (sendEmail && mailingEnabled) {
            emailService.sendNewAppointmentScheduledNotification(appointment);
        }
    }

    @Override
    public void newAppointmentCanceledByCustomerNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Canceled";
        String message = appointment.getCustomer().getFirstName() + " " + appointment.getCustomer().getLastName() + " cancelled appointment scheduled at " + appointment.getStart().toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getProvider());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentCanceledByCustomerNotification(appointment);
        }
    }

    @Override
    public void newAppointmentCanceledByProviderNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Canceled";
        String message = appointment.getProvider().getFirstName() + " " + appointment.getProvider().getLastName() + " cancelled appointment scheduled at " + appointment.getStart().toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentCanceledByProviderNotification(appointment);
        }
    }

    public void newInvoice(Invoice invoice, boolean sendEmail) {
        String title = "New invoice";
        String message = "New invoice has been issued for you";
        String url = "/invoices/" + invoice.getId();
        newNotification(title, message, url, invoice.getAppointments().get(0).getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendInvoice(invoice);
        }
    }

    @Override
    public void newExchangeRequestedNotification(Appointment oldAppointment, Appointment newAppointment, boolean sendEmail) {
        String title = "Request for exchange";
        String message = "One of the users sent you a request to exchange his appointment with your appointment";
        String url = "/appointments/" + newAppointment.getId();
        newNotification(title, message, url, newAppointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendNewExchangeRequestedNotification(oldAppointment, newAppointment);
        }
    }

    @Override
    public void newExchangeAcceptedNotification(ExchangeRequest exchangeRequest, boolean sendEmail) {
        String title = "Exchange request accepted";
        String message = "Someone accepted your appointment exchange request from " + exchangeRequest.getRequested().getStart() + " to " + exchangeRequest.getRequestor().getStart();
        String url = "/appointments/" + exchangeRequest.getRequested();
        newNotification(title, message, url, exchangeRequest.getRequested().getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendExchangeRequestAcceptedNotification(exchangeRequest);
        }
    }

    @Override
    public void newExchangeRejectedNotification(ExchangeRequest exchangeRequest, boolean sendEmail) {
        String title = "Exchange request rejected";
        String message = "Someone rejected your appointment exchange request from " + exchangeRequest.getRequestor().getStart() + " to " + exchangeRequest.getRequested().getStart();
        String url = "/appointments/" + exchangeRequest.getRequestor();
        newNotification(title, message, url, exchangeRequest.getRequestor().getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendExchangeRequestRejectedNotification(exchangeRequest);
        }
    }

    @Override
    public void newAppointmentRejectionAcceptedNotification(Appointment appointment, boolean sendEmail) {
        String title = "Rejection accepted";
        String message = "You provider accepted your rejection request";
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentRejectionAcceptedNotification(appointment);
        }
    }

    @Override
    public void newChatMessageNotification(ChatMessage chatMessage, boolean sendEmail) {
        String title = "New chat message";
        String message = "You have new chat message from " + chatMessage.getAuthor().getFirstName() + " regarding appointment scheduled at " + chatMessage.getAppointment().getStart();
        String url = "/appointments/" + chatMessage.getAppointment().getId();
        newNotification(title, message, url, chatMessage.getAuthor() == chatMessage.getAppointment().getProvider() ? chatMessage.getAppointment().getCustomer() : chatMessage.getAppointment().getProvider());
        if (sendEmail && mailingEnabled) {
            emailService.sendNewChatMessageNotification(chatMessage);
        }
    }

}

package com.tuannq.store.service.impl;

import com.tuannq.store.dao.AppointmentRepository;
import com.tuannq.store.dao.ExchangeRequestRepository;
import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.AppointmentStatus;
import com.tuannq.store.entity.ExchangeRequest;
import com.tuannq.store.entity.ExchangeStatus;
import com.tuannq.store.entity.user.customer.Customer;
import com.tuannq.store.service.ExchangeService;
import com.tuannq.store.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final AppointmentRepository appointmentRepository;
    private final NotificationService notificationService;
    private final ExchangeRequestRepository exchangeRequestRepository;

    public ExchangeServiceImpl(AppointmentRepository appointmentRepository, NotificationService notificationService, ExchangeRequestRepository exchangeRequestRepository) {
        this.appointmentRepository = appointmentRepository;
        this.notificationService = notificationService;
        this.exchangeRequestRepository = exchangeRequestRepository;
    }

    @Override
    public boolean checkIfEligibleForExchange(Long usersId, Long appointmentId) {
        Appointment appointment = appointmentRepository.getOne(appointmentId);
        return appointment.getStart().minusHours(24).isAfter(LocalDateTime.now()) && appointment.getStatus().equals(AppointmentStatus.SCHEDULED) && appointment.getCustomer().getId() == usersId;
    }

    @Override
    public List<Appointment> getEligibleAppointmentsForExchange(Long appointmentId) {
        Appointment appointmentToExchange = appointmentRepository.getOne(appointmentId);
        return appointmentRepository.getEligibleAppointmentsForExchange(LocalDateTime.now().plusHours(24), appointmentToExchange.getCustomer().getId(), appointmentToExchange.getProvider().getId(), appointmentToExchange.getWork().getId());
    }

    @Override
    public boolean checkIfExchangeIsPossible(Long oldAppointmentId, Long newAppointmentId, Long usersId) {
        Appointment oldAppointment = appointmentRepository.getOne(oldAppointmentId);
        Appointment newAppointment = appointmentRepository.getOne(newAppointmentId);
        if (oldAppointment.getCustomer().getId() == usersId) {
            return oldAppointment.getWork().getId().equals(newAppointment.getWork().getId())
                    && oldAppointment.getProvider().getId().equals(newAppointment.getProvider().getId())
                    && oldAppointment.getStart().minusHours(24).isAfter(LocalDateTime.now())
                    && newAppointment.getStart().minusHours(24).isAfter(LocalDateTime.now());
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }

    }

    @Override
    public boolean acceptExchange(Long exchangeId, Long usersId) {
        ExchangeRequest exchangeRequest = exchangeRequestRepository.getOne(exchangeId);
        Appointment requestor = exchangeRequest.getRequestor();
        Appointment requested = exchangeRequest.getRequested();
        Customer tempCustomer = requestor.getCustomer();
        requestor.setStatus(AppointmentStatus.SCHEDULED);
        exchangeRequest.setStatus(ExchangeStatus.ACCEPTED);
        requestor.setCustomer(requested.getCustomer());
        requested.setCustomer(tempCustomer);
        exchangeRequestRepository.save(exchangeRequest);
        appointmentRepository.save(requested);
        appointmentRepository.save(requestor);
        notificationService.newExchangeAcceptedNotification(exchangeRequest, true);
        return true;
    }

    @Override
    public boolean rejectExchange(Long exchangeId, Long usersId) {
        ExchangeRequest exchangeRequest = exchangeRequestRepository.getOne(exchangeId);
        Appointment requestor = exchangeRequest.getRequestor();
        exchangeRequest.setStatus(ExchangeStatus.REJECTED);
        requestor.setStatus(AppointmentStatus.SCHEDULED);
        exchangeRequestRepository.save(exchangeRequest);
        appointmentRepository.save(requestor);
        notificationService.newExchangeRejectedNotification(exchangeRequest, true);
        return true;
    }

    @Override
    public boolean requestExchange(Long oldAppointmentId, Long newAppointmentId, Long usersId) {
        if (checkIfExchangeIsPossible(oldAppointmentId, newAppointmentId, usersId)) {
            Appointment oldAppointment = appointmentRepository.getOne(oldAppointmentId);
            Appointment newAppointment = appointmentRepository.getOne(newAppointmentId);
            oldAppointment.setStatus(AppointmentStatus.EXCHANGE_REQUESTED);
            appointmentRepository.save(oldAppointment);
            ExchangeRequest exchangeRequest = new ExchangeRequest(oldAppointment, newAppointment, ExchangeStatus.PENDING);
            exchangeRequestRepository.save(exchangeRequest);
            notificationService.newExchangeRequestedNotification(oldAppointment, newAppointment, true);
            return true;
        }
        return false;
    }
}

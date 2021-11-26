package com.tuannq.store.service;

import com.tuannq.store.entity.Appointment;

import java.util.List;

public interface ExchangeService {

    boolean checkIfEligibleForExchange(Long usersId, Long appointmentId);

    List<Appointment> getEligibleAppointmentsForExchange(Long appointmentId);

    boolean checkIfExchangeIsPossible(Long oldAppointmentId, Long newAppointmentId, Long usersId);

    boolean acceptExchange(Long exchangeId, Long usersId);

    boolean rejectExchange(Long exchangeId, Long usersId);

    boolean requestExchange(Long oldAppointmentId, Long newAppointmentId, Long usersId);
}

package com.tuannq.store.service;

import com.tuannq.store.entity.Appointment;

import java.time.LocalDateTime;
import java.util.Date;

public interface JwtTokenService {
    String generateAppointmentRejectionToken(Appointment appointment);

    String generateAcceptRejectionToken(Appointment appointment);

    boolean validateToken(String token);

    Long getAppointmentIdFromToken(String token);

    Long getCustomerIdFromToken(String token);

    Long getProviderIdFromToken(String token);

    ////
    Date convertLocalDateTimeToDate(LocalDateTime localDateTime);
}

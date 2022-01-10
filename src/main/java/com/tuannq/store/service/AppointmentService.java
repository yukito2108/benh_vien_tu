package com.tuannq.store.service;

import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.ChatMessage;
import com.tuannq.store.entity.Work;
import com.tuannq.store.model.TimePeroid;
import com.tuannq.store.model.request.MedicalExaminationResultForm;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    void createNewAppointment(Long workId, Long providerId, Long customerId, LocalDateTime start);

    void updateAppointment(Appointment appointment);

    void updateUsersAppointmentsStatuses(Long usersId);

    void updateAllAppointmentsStatuses();

    void updateAppointmentsStatusesWithExpiredExchangeRequest();

    void deleteAppointmentById(Long appointmentId);

    Appointment getAppointmentByIdWithAuthorization(Long id);

    Appointment getAppointmentById(Long id);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentByCustomerId(Long customerId);

    List<Appointment> getAppointmentByProviderId(Long providerId);

    List<Appointment> getAppointmentsByProviderAtDay(Long providerId, LocalDate day);

    List<Appointment> getAppointmentsByCustomerAtDay(Long providerId, LocalDate day);

    List<Appointment> getConfirmedAppointmentsByCustomerId(Long customerId);

    List<Appointment> getCanceledAppointmentsByCustomerIdForCurrentMonth(Long usersId);

    List<TimePeroid> getAvailableHours(Long providerId, Long customerId, Long workId, LocalDate date);

    List<TimePeroid> calculateAvailableHours(List<TimePeroid> availableTimePeroids, Work work);

    List<TimePeroid> excludeAppointmentsFromTimePeroids(List<TimePeroid> peroids, List<Appointment> appointments);

    String getCancelNotAllowedReason(Long usersId, Long appointmentId);

    void cancelUsersAppointmentById(Long appointmentId, Long usersId);

    boolean isCustomerAllowedToRejectAppointment(Long customerId, Long appointmentId);

    boolean requestAppointmentRejection(Long appointmentId, Long customerId);

    boolean requestAppointmentRejection(String token);

    boolean isProviderAllowedToAcceptRejection(Long providerId, Long appointmentId);

    boolean acceptRejection(Long appointmentId, Long providerId);

    boolean acceptRejection(String token);


    void addMessageToAppointmentChat(Long appointmentId, Long authorId, ChatMessage chatMessage);

    int getNumberOfCanceledAppointmentsForUsers(Long usersId);

    int getNumberOfScheduledAppointmentsForUsers(Long usersId);

    boolean isAvailable(Long workId, Long providerId, Long customerId, LocalDateTime start);

    public void saveMedicalExaminationResults(Long appointmentId, MedicalExaminationResultForm form);

}

package com.tuannq.store.service.impl;

import com.tuannq.store.dao.AppointmentRepository;
import com.tuannq.store.dao.ChatMessageRepository;
import com.tuannq.store.entity.*;
import com.tuannq.store.entity.Users;
import com.tuannq.store.entity.user.provider.Provider;
import com.tuannq.store.exception.AppointmentNotFoundException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.DayPlan;
import com.tuannq.store.model.TimePeroid;
import com.tuannq.store.model.request.MedicalExaminationResultForm;
import com.tuannq.store.service.AppointmentService;
import com.tuannq.store.service.NotificationService;
import com.tuannq.store.service.UsersService;
import com.tuannq.store.service.WorkService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final int NUMBER_OF_ALLOWED_CANCELATIONS_PER_MONTH = 1;
    private final AppointmentRepository appointmentRepository;
    private final UsersService usersService;
    private final WorkService workService;
    private final ChatMessageRepository chatMessageRepository;
    private final NotificationService notificationService;
    private final JwtTokenServiceImpl jwtTokenService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UsersService usersService, WorkService workService, ChatMessageRepository chatMessageRepository, NotificationService notificationService, JwtTokenServiceImpl jwtTokenService) {
        this.appointmentRepository = appointmentRepository;
        this.usersService = usersService;
        this.workService = workService;
        this.chatMessageRepository = chatMessageRepository;
        this.notificationService = notificationService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    @PostAuthorize("returnObject.provider.id == principal.id or returnObject.customer.id == principal.id or hasRole('ADMIN') ")
    public Appointment getAppointmentByIdWithAuthorization(Long id) {
        return getAppointmentById(id);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("#customerId == principal.id")
    public List<Appointment> getAppointmentByCustomerId(Long customerId) {
        int x = 3;
        return appointmentRepository.findByCustomerId(customerId);
    }

    @Override
    @PreAuthorize("#providerId == principal.id")
    public List<Appointment> getAppointmentByProviderId(Long providerId) {
        return appointmentRepository.findByProviderId(providerId);
    }

    @Override
    public List<Appointment> getAppointmentsByProviderAtDay(Long providerId, LocalDate day) {
        return appointmentRepository.findByProviderIdWithStartInPeroid(providerId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerAtDay(Long providerId, LocalDate day) {
        return appointmentRepository.findByCustomerIdWithStartInPeroid(providerId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    @Override
    public List<TimePeroid> getAvailableHours(Long providerId, Long customerId, Long workId, LocalDate date) {
        Provider p = usersService.getProviderById(providerId);
        WorkingPlan workingPlan = p.getWorkingPlan();
        DayPlan selectedDay = workingPlan.getDay(date.getDayOfWeek().toString().toLowerCase());

        List<Appointment> providerAppointments = getAppointmentsByProviderAtDay(providerId, date);
        List<Appointment> customerAppointments = getAppointmentsByCustomerAtDay(customerId, date);

        List<TimePeroid> availablePeroids = selectedDay.getTimePeroidsWithBreaksExcluded();
        availablePeroids = excludeAppointmentsFromTimePeroids(availablePeroids, providerAppointments);

        availablePeroids = excludeAppointmentsFromTimePeroids(availablePeroids, customerAppointments);
        return calculateAvailableHours(availablePeroids, workService.getWorkById(workId));
    }

    @Override
    public void createNewAppointment(Long workId, Long providerId, Long customerId, LocalDateTime start) {
        if (isAvailable(workId, providerId, customerId, start)) {
            Appointment appointment = new Appointment();
            appointment.setStatus(AppointmentStatus.SCHEDULED);
            appointment.setCustomer(usersService.getCustomerById(customerId));
            appointment.setProvider(usersService.getProviderById(providerId));
            Work work = workService.getWorkById(workId);
            appointment.setWork(work);
            appointment.setStart(start);
            appointment.setEnd(start.plusMinutes(work.getDuration()));
            appointmentRepository.save(appointment);
            notificationService.newNewAppointmentScheduledNotification(appointment, true);
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public void addMessageToAppointmentChat(Long appointmentId, Long authorId, ChatMessage chatMessage) {
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);
        if (appointment.getProvider().getId() == authorId || appointment.getCustomer().getId() == authorId) {
            chatMessage.setAuthor(usersService.getUsersById(authorId));
            chatMessage.setAppointment(appointment);
            chatMessage.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            chatMessageRepository.save(chatMessage);
//            notificationService.newChatMessageNotification(chatMessage, true);
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }

    @Override
    public List<TimePeroid> calculateAvailableHours(List<TimePeroid> availableTimePeroids, Work work) {
        ArrayList<TimePeroid> availableHours = new ArrayList();
        for (TimePeroid peroid : availableTimePeroids) {
            TimePeroid workPeroid = new TimePeroid(peroid.getStart(), peroid.getStart().plusMinutes(work.getDuration()));
            while (workPeroid.getEnd().isBefore(peroid.getEnd()) || workPeroid.getEnd().equals(peroid.getEnd())) {
                availableHours.add(new TimePeroid(workPeroid.getStart(), workPeroid.getStart().plusMinutes(work.getDuration())));
                workPeroid.setStart(workPeroid.getStart().plusMinutes(work.getDuration()));
                workPeroid.setEnd(workPeroid.getEnd().plusMinutes(work.getDuration()));
            }
        }
        return availableHours;
    }

    @Override
    public List<TimePeroid> excludeAppointmentsFromTimePeroids(List<TimePeroid> peroids, List<Appointment> appointments) {

        List<TimePeroid> toAdd = new ArrayList();
        Collections.sort(appointments);
        for (Appointment appointment : appointments) {
            for (TimePeroid peroid : peroids) {
                if ((appointment.getStart().toLocalTime().isBefore(peroid.getStart()) || appointment.getStart().toLocalTime().equals(peroid.getStart())) && appointment.getEnd().toLocalTime().isAfter(peroid.getStart()) && appointment.getEnd().toLocalTime().isBefore(peroid.getEnd())) {
                    peroid.setStart(appointment.getEnd().toLocalTime());
                }
                if (appointment.getStart().toLocalTime().isAfter(peroid.getStart()) && appointment.getStart().toLocalTime().isBefore(peroid.getEnd()) && appointment.getEnd().toLocalTime().isAfter(peroid.getEnd()) || appointment.getEnd().toLocalTime().equals(peroid.getEnd())) {
                    peroid.setEnd(appointment.getStart().toLocalTime());
                }
                if (appointment.getStart().toLocalTime().isAfter(peroid.getStart()) && appointment.getEnd().toLocalTime().isBefore(peroid.getEnd())) {
                    toAdd.add(new TimePeroid(peroid.getStart(), appointment.getStart().toLocalTime()));
                    peroid.setStart(appointment.getEnd().toLocalTime());
                }
            }
        }
        peroids.addAll(toAdd);
        Collections.sort(peroids);
        return peroids;
    }

    @Override
    public List<Appointment> getCanceledAppointmentsByCustomerIdForCurrentMonth(Long customerId) {
        return appointmentRepository.findByCustomerIdCanceledAfterDate(customerId, LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay());
    }

    @Override
    public void updateUsersAppointmentsStatuses(Long usersId) {
        for (Appointment appointment : appointmentRepository.findScheduledByUsersIdWithEndBeforeDate(LocalDateTime.now(), usersId)) {
            appointment.setStatus(AppointmentStatus.FINISHED);
            updateAppointment(appointment);
        }

        for (Appointment appointment : appointmentRepository.findFinishedByUsersIdWithEndBeforeDate(LocalDateTime.now().minusDays(1), usersId)) {

            appointment.setStatus(AppointmentStatus.INVOICED);
            updateAppointment(appointment);
        }
    }

    @Override
    public void updateAllAppointmentsStatuses() {
        appointmentRepository.findScheduledWithEndBeforeDate(LocalDateTime.now())
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatus.FINISHED);
                    updateAppointment(appointment);
                    if (LocalDateTime.now().minusDays(1).isBefore(appointment.getEnd())) {
                        notificationService.newAppointmentFinishedNotification(appointment, true);
                    }
                });

        appointmentRepository.findFinishedWithEndBeforeDate(LocalDateTime.now().minusDays(1))
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatus.CONFIRMED);
                    updateAppointment(appointment);
                });
    }

    @Override
    public void updateAppointmentsStatusesWithExpiredExchangeRequest() {
        appointmentRepository.findExchangeRequestedWithStartBefore(LocalDateTime.now().plusDays(1))
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatus.SCHEDULED);
                    updateAppointment(appointment);
                });
    }

    @Override
    public void cancelUsersAppointmentById(Long appointmentId, Long usersId) {
        Appointment appointment = appointmentRepository.getOne(appointmentId);
        if (appointment.getCustomer().getId() == usersId || appointment.getProvider().getId() == usersId) {
            appointment.setStatus(AppointmentStatus.CANCELED);
            Users canceler = usersService.getUsersById(usersId);
            appointment.setCanceler(canceler);
            appointment.setCanceledAt(LocalDateTime.now());
            appointmentRepository.save(appointment);
            if (canceler.equals(appointment.getCustomer())) {
                notificationService.newAppointmentCanceledByCustomerNotification(appointment, true);
            } else if (canceler.equals(appointment.getProvider())) {
                notificationService.newAppointmentCanceledByProviderNotification(appointment, true);
            }
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }


    }


    @Override
    public boolean isCustomerAllowedToRejectAppointment(Long usersId, Long appointmentId) {
        Users users = usersService.getUsersById(usersId);
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);

        return appointment.getCustomer().equals(users) && appointment.getStatus().equals(AppointmentStatus.FINISHED) && !LocalDateTime.now().isAfter(appointment.getEnd().plusDays(1));
    }

    @Override
    public boolean requestAppointmentRejection(Long appointmentId, Long customerId) {
        if (isCustomerAllowedToRejectAppointment(customerId, appointmentId)) {
            Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);
            appointment.setStatus(AppointmentStatus.REJECTION_REQUESTED);
            notificationService.newAppointmentRejectionRequestedNotification(appointment, true);
            updateAppointment(appointment);
            return true;
        } else {
            return false;
        }

    }


    @Override
    public boolean requestAppointmentRejection(String token) {
        if (jwtTokenService.validateToken(token)) {
            Long appointmentId = jwtTokenService.getAppointmentIdFromToken(token);
            Long customerId = jwtTokenService.getCustomerIdFromToken(token);
            return requestAppointmentRejection(appointmentId, customerId);
        }
        return false;
    }


    @Override
    public boolean isProviderAllowedToAcceptRejection(Long providerId, Long appointmentId) {
        Users users = usersService.getUsersById(providerId);
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);

        return appointment.getProvider().equals(users) && appointment.getStatus().equals(AppointmentStatus.REJECTION_REQUESTED);
    }

    @Override
    public boolean acceptRejection(Long appointmentId, Long customerId) {
        if (isProviderAllowedToAcceptRejection(customerId, appointmentId)) {
            Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);
            appointment.setStatus(AppointmentStatus.REJECTED);
            updateAppointment(appointment);
            notificationService.newAppointmentRejectionAcceptedNotification(appointment, true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean acceptRejection(String token) {
        if (jwtTokenService.validateToken(token)) {
            Long appointmentId = jwtTokenService.getAppointmentIdFromToken(token);
            Long providerId = jwtTokenService.getProviderIdFromToken(token);
            return acceptRejection(appointmentId, providerId);
        }
        return false;
    }

    @Override
    public String getCancelNotAllowedReason(Long usersId, Long appointmentId) {
        Users users = usersService.getUsersById(usersId);
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);

        if (users.hasRole("ROLE_ADMIN")) {
            return "Chỉ khách hàng hoặc bác sĩ mới có thể huỷ.";
        }

        if (appointment.getProvider().equals(users)) {
            if (!appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {
                return "Chỉ có thể huỷ các cuộc hẹn có trạng thái scheduled.";
            } else {
                return null;
            }
        }

        if (appointment.getCustomer().equals(users)) {
            if (!appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {
                return "Chỉ có thể huỷ các cuộc hẹn có trạng thái scheduled.";
            } else if (LocalDateTime.now().plusDays(1).isAfter(appointment.getStart())) {
                return "Chỉ có thể huỷ cuộc hẹn trong vòng 24h sau khi cuộc hẹn bắt đầu.";
            } else if (!appointment.getWork().getEditable()) {
                return "Chỉ có thể huỷ bởi bác sĩ.";
            } else if (getCanceledAppointmentsByCustomerIdForCurrentMonth(usersId).size() >= NUMBER_OF_ALLOWED_CANCELATIONS_PER_MONTH) {
                return "Bạn đã vượt qua giới hạn huỷ hẹn trong tháng này.";
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public int getNumberOfCanceledAppointmentsForUsers(Long usersId) {
        return appointmentRepository.findCanceledByUsers(usersId).size();
    }

    @Override
    public int getNumberOfScheduledAppointmentsForUsers(Long usersId) {
        return appointmentRepository.findScheduledByUsersId(usersId).size();
    }

    @Override
    public boolean isAvailable(Long workId, Long providerId, Long customerId, LocalDateTime start) {
        if (!workService.isWorkForCustomer(workId, customerId)) {
            return false;
        }
        Work work = workService.getWorkById(workId);
        TimePeroid timePeroid = new TimePeroid(start.toLocalTime(), start.toLocalTime().plusMinutes(work.getDuration()));
        return getAvailableHours(providerId, customerId, workId, start.toLocalDate()).contains(timePeroid);
    }

    @Override
    public void saveMedicalExaminationResults(Long appointmentId, MedicalExaminationResultForm form) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isEmpty())
            throw new NotFoundException("appointment.not-found");
        Appointment appoint = appointment.get();
        appoint.setStatus(AppointmentStatus.FINISHED);
        appoint.setMedicalExaminationResults(form.getMedicalExaminationResults());
        appointmentRepository.save(appoint);
    }

    @Override
    public List<Appointment> getConfirmedAppointmentsByCustomerId(Long customerId) {
        return appointmentRepository.findConfirmedByCustomerId(customerId);
    }
}

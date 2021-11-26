package com.tuannq.store.dao;

import com.tuannq.store.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.customer.id = :customerId")
    List<Appointment> findByCustomerId(@Param("customerId") Long customerId);

    @Query("select a from Appointment a where a.provider.id = :providerId")
    List<Appointment> findByProviderId(@Param("providerId") Long providerId);

    @Query("select a from Appointment a where a.canceler.id = :usersId")
    List<Appointment> findCanceledByUsers(@Param("usersId") Long usersId);

    @Query("select a from Appointment a where  a.status='SCHEDULED' and (a.customer.id = :usersId or a.provider.id = :usersId)")
    List<Appointment> findScheduledByUsersId(@Param("usersId") Long usersId);

    @Query("select a from Appointment a where a.provider.id = :providerId and  a.start >=:dayStart and  a.start <=:dayEnd")
    List<Appointment> findByProviderIdWithStartInPeroid(@Param("providerId") Long providerId, @Param("dayStart") LocalDateTime startPeroid, @Param("dayEnd") LocalDateTime endPeroid);

    @Query("select a from Appointment a where a.customer.id = :customerId and  a.start >=:dayStart and  a.start <=:dayEnd")
    List<Appointment> findByCustomerIdWithStartInPeroid(@Param("customerId") Long customerId, @Param("dayStart") LocalDateTime startPeroid, @Param("dayEnd") LocalDateTime endPeroid);

    @Query("select a from Appointment a where a.customer.id = :customerId and a.canceler.id =:customerId and a.canceledAt >=:date")
    List<Appointment> findByCustomerIdCanceledAfterDate(@Param("customerId") Long customerId, @Param("date") LocalDateTime date);

    @Query("select a from Appointment a where a.status = 'SCHEDULED' and :now >= a.end")
    List<Appointment> findScheduledWithEndBeforeDate(@Param("now") LocalDateTime now);

    @Query("select a from Appointment a where a.status = 'SCHEDULED' and :date >= a.end and (a.customer.id = :usersId or a.provider.id = :usersId)")
    List<Appointment> findScheduledByUsersIdWithEndBeforeDate(@Param("date") LocalDateTime date, @Param("usersId") Long usersId);

    @Query("select a from Appointment a where a.status = 'FINISHED' and :date >= a.end")
    List<Appointment> findFinishedWithEndBeforeDate(@Param("date") LocalDateTime date);

    @Query("select a from Appointment a where a.status = 'FINISHED' and :date >= a.end and (a.customer.id = :usersId or a.provider.id = :usersId)")
    List<Appointment> findFinishedByUsersIdWithEndBeforeDate(@Param("date") LocalDateTime date, @Param("usersId") Long usersId);

    @Query("select a from Appointment a where a.status = 'CONFIRMED' and a.customer.id = :customerId")
    List<Appointment> findConfirmedByCustomerId(@Param("customerId") Long customerId);

    @Query("select a from Appointment a inner join a.work w where a.status = 'SCHEDULED' and a.customer.id <> :customerId and a.provider.id= :providerId and a.start >= :start and w.id = :workId")
    List<Appointment> getEligibleAppointmentsForExchange(@Param("start") LocalDateTime start, @Param("customerId") Long customerId, @Param("providerId") Long providerId, @Param("workId") Long workId);

    @Query("select a from Appointment a where a.status = 'EXCHANGE_REQUESTED' and a.start <= :start")
    List<Appointment> findExchangeRequestedWithStartBefore(@Param("start") LocalDateTime date);

}

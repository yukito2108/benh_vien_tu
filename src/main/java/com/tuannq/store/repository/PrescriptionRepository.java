package com.tuannq.store.repository;

import com.tuannq.store.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Transactional
    @Modifying
    @Query("delete from Prescription  x1 " +
            " where x1.appointment.id = :#{#appointmentId} " +
            "   and x1.product.id not in :#{#ids} ")
    void deleteByAppointment(Set<Long> ids, Long appointmentId);

}

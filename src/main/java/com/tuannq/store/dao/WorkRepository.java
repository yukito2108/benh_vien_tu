package com.tuannq.store.dao;

import com.tuannq.store.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    @Query("select w from Work w inner join w.providers p where p.id in :providerId")
    List<Work> findByProviderId(@Param("providerId") Long providerId);

    @Query("select w from Work w where w.targetCustomer = :target ")
    List<Work> findByTargetCustomer(@Param("target") String targetCustomer);

    @Query("select w from Work w inner join w.providers p where p.id in :providerId and w.targetCustomer = :target ")
    List<Work> findByTargetCustomerAndProviderId(@Param("target") String targetCustomer, @Param("providerId") Long providerId);
}

package com.tuannq.store.dao;

import com.tuannq.store.entity.WorkingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkingPlanRepository extends JpaRepository<WorkingPlan, Long> {
    @Query("select w from WorkingPlan w where w.provider.id = :providerId")
    WorkingPlan getWorkingPlanByProviderId(@Param("providerId") Long providerId);
}

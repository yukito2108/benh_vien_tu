package com.tuannq.store.service;

import com.tuannq.store.entity.WorkingPlan;
import com.tuannq.store.model.TimePeroid;

public interface WorkingPlanService {
    void updateWorkingPlan(WorkingPlan workingPlan);

    void addBreakToWorkingPlan(TimePeroid breakToAdd, Long planId, String dayOfWeek);

    void deleteBreakFromWorkingPlan(TimePeroid breakToDelete, Long planId, String dayOfWeek);

    WorkingPlan getWorkingPlanByProviderId(Long providerId);
}

package com.tuannq.store.service.impl;

import com.tuannq.store.dao.WorkingPlanRepository;
import com.tuannq.store.entity.WorkingPlan;
import com.tuannq.store.model.TimePeroid;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.WorkingPlanService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WorkingPlanServiceImpl implements WorkingPlanService {

    private final WorkingPlanRepository workingPlanRepository;

    public WorkingPlanServiceImpl(WorkingPlanRepository workingPlanRepository) {
        this.workingPlanRepository = workingPlanRepository;
    }

    @Override
    @PreAuthorize("#updateData.provider.id == principal.id")
    public void updateWorkingPlan(WorkingPlan updateData) {
        WorkingPlan workingPlan = workingPlanRepository.getOne(updateData.getId());
        workingPlan.getMonday().setWorkingHours(updateData.getMonday().getWorkingHours());
        workingPlan.getTuesday().setWorkingHours(updateData.getTuesday().getWorkingHours());
        workingPlan.getWednesday().setWorkingHours(updateData.getWednesday().getWorkingHours());
        workingPlan.getThursday().setWorkingHours(updateData.getThursday().getWorkingHours());
        workingPlan.getFriday().setWorkingHours(updateData.getFriday().getWorkingHours());
        workingPlan.getSaturday().setWorkingHours(updateData.getSaturday().getWorkingHours());
        workingPlan.getSunday().setWorkingHours(updateData.getSunday().getWorkingHours());
        workingPlanRepository.save(workingPlan);
    }

    @Override
    public void addBreakToWorkingPlan(TimePeroid breakToAdd, Long planId, String dayOfWeek) {
        CustomUsersDetails currentUsers = (CustomUsersDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WorkingPlan workingPlan = workingPlanRepository.getOne(planId);
        if (workingPlan.getProvider().getId().equals(currentUsers.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        workingPlan.getDay(dayOfWeek).getBreaks().add(breakToAdd);
        workingPlanRepository.save(workingPlan);
    }

    @Override
    public void deleteBreakFromWorkingPlan(TimePeroid breakToDelete, Long planId, String dayOfWeek) {
        CustomUsersDetails currentUsers = (CustomUsersDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WorkingPlan workingPlan = workingPlanRepository.getOne(planId);
        if (workingPlan.getProvider().getId().equals(currentUsers.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        workingPlan.getDay(dayOfWeek).getBreaks().remove(breakToDelete);
        workingPlanRepository.save(workingPlan);
    }


    @Override
    @PreAuthorize("#providerId == principal.id")
    public WorkingPlan getWorkingPlanByProviderId(Long providerId) {
        return workingPlanRepository.getWorkingPlanByProviderId(providerId);
    }


}

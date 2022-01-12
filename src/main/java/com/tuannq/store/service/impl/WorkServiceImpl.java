package com.tuannq.store.service.impl;

import com.tuannq.store.dao.WorkRepository;
import com.tuannq.store.entity.Work;
import com.tuannq.store.entity.user.customer.Customer;
import com.tuannq.store.exception.WorkNotFoundException;
import com.tuannq.store.service.UsersService;
import com.tuannq.store.service.WorkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final UsersService usersService;

    public WorkServiceImpl(WorkRepository workRepository, UsersService usersService) {
        this.workRepository = workRepository;
        this.usersService = usersService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void createNewWork(Work work) {
        workRepository.save(work);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void updateWork(Work workUpdateData) {
        Work work = getWorkById(workUpdateData.getId());
        work.setName(workUpdateData.getName());
        work.setPrice(workUpdateData.getPrice());
        work.setDuration(workUpdateData.getDuration());
        work.setDescription(workUpdateData.getDescription());
        work.setEditable(workUpdateData.getEditable());
        work.setTargetCustomer(workUpdateData.getTargetCustomer());
        work.setTemplate(workUpdateData.getTemplate());
        workRepository.save(work);
    }

    @Override
    public Work getWorkById(Long workId) {
        return workRepository.findById(workId).orElseThrow(WorkNotFoundException::new);
    }

    @Override
    public List<Work> getAllWorks() {
        return workRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteWorkById(Long workId) {
        workRepository.deleteById(workId);
    }

    @Override
    public boolean isWorkForCustomer(Long workId, Long customerId) {
        Customer customer = usersService.getCustomerById(customerId);
        Work work = getWorkById(workId);
        if (customer.hasRole("ROLE_CUSTOMER_RETAIL") && !work.getTargetCustomer().equals("retail")) {
            return false;
        } else return !customer.hasRole("ROLE_CUSTOMER_CORPORATE") || work.getTargetCustomer().equals("corporate");
    }

    @Override
    public List<Work> getWorksByProviderId(Long providerId) {
        return workRepository.findByProviderId(providerId);
    }

    @Override
    public List<Work> getRetailCustomerWorks() {
        return workRepository.findByTargetCustomer("retail");
    }

    @Override
    public List<Work> getCorporateCustomerWorks() {
        return workRepository.findByTargetCustomer("corporate");
    }

    @Override
    public List<Work> getWorksForRetailCustomerByProviderId(Long providerId) {
        return workRepository.findByTargetCustomerAndProviderId("retail", providerId);
    }

    @Override
    public List<Work> getWorksForCorporateCustomerByProviderId(Long providerId) {
        return workRepository.findByTargetCustomerAndProviderId("corporate", providerId);
    }


}

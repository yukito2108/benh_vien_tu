package com.tuannq.store.service;

import com.tuannq.store.entity.Work;

import java.util.List;

public interface WorkService {
    void createNewWork(Work work);

    Work getWorkById(Long workId);

    List<Work> getAllWorks();

    List<Work> getWorksByProviderId(Long providerId);

    List<Work> getRetailCustomerWorks();

    List<Work> getCorporateCustomerWorks();

    List<Work> getWorksForRetailCustomerByProviderId(Long providerId);

    List<Work> getWorksForCorporateCustomerByProviderId(Long providerId);

    void updateWork(Work work);

    void deleteWorkById(Long workId);

    boolean isWorkForCustomer(Long workId, Long customerId);
}

package com.tuannq.store.service;

public interface ScheduledTasksService {
    void updateAllAppointmentsStatuses();

    void issueInvoicesForCurrentMonth();
}

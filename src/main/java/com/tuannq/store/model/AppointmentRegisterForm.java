package com.tuannq.store.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AppointmentRegisterForm {

    private Long workId;
    private Long providerId;
    private Long customerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;

    public AppointmentRegisterForm() {
    }

    public AppointmentRegisterForm(Long workId, Long providerId, LocalDateTime start, LocalDateTime end) {
        this.workId = workId;
        this.providerId = providerId;
        this.start = start;
        this.end = end;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}

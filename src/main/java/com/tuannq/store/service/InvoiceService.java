package com.tuannq.store.service;

import com.tuannq.store.entity.Invoice;
import com.tuannq.store.entity.Users;
import com.tuannq.store.security.CustomUsersDetails;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    void createNewInvoice(Invoice invoice);

    Invoice getInvoiceByAppointmentId(Long appointmentId);

    Invoice getInvoiceById(Long invoiceId);

    List<Invoice> getAllInvoices();

    void changeInvoiceStatusToPaid(Long invoiceId);

    void issueInvoicesForConfirmedAppointments();

    String generateInvoiceNumber();

    File generatePdfForInvoice(Long invoiceId, Optional<Users> currentUsers);

    boolean isUsersAllowedToDownloadInvoice(CustomUsersDetails users, Invoice invoice);
}


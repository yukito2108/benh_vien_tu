package com.tuannq.store.service.impl;

import com.tuannq.store.dao.InvoiceRepository;
import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.AppointmentStatus;
import com.tuannq.store.entity.Invoice;
import com.tuannq.store.entity.Users;
import com.tuannq.store.entity.user.customer.Customer;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.AppointmentService;
import com.tuannq.store.service.InvoiceService;
import com.tuannq.store.service.NotificationService;
import com.tuannq.store.service.UsersService;
import com.tuannq.store.util.PdfGeneratorUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;
    private final UsersService usersService;
    private final AppointmentService appointmentService;
    private final NotificationService notificationService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, PdfGeneratorUtil pdfGeneratorUtil, UsersService usersService, AppointmentService appointmentService, NotificationService notificationService) {
        this.invoiceRepository = invoiceRepository;
        this.pdfGeneratorUtil = pdfGeneratorUtil;
        this.usersService = usersService;
        this.appointmentService = appointmentService;
        this.notificationService = notificationService;
    }

    @Override
    public String generateInvoiceNumber() {
        List<Invoice> invoices = invoiceRepository.findAllIssuedInCurrentMonth(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay());
        int nextInvoiceNumber = invoices.size() + 1;
        LocalDateTime today = LocalDateTime.now();
        return "FV/" + today.getYear() + "/" + today.getMonthValue() + "/" + nextInvoiceNumber;
    }

    @Override
    public void createNewInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceByAppointmentId(Long appointmentId) {
        return invoiceRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public File generatePdfForInvoice(Long invoiceId, Optional<Users>  currentUsers) {
        Invoice invoice = invoiceRepository.getOne(invoiceId);
        if (!isUsersAllowedToDownloadInvoice(CustomUsersDetails.create(currentUsers.get()), invoice)) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        return pdfGeneratorUtil.generatePdfFromInvoice(invoice);
    }

    @Override
    public boolean isUsersAllowedToDownloadInvoice(CustomUsersDetails users, Invoice invoice) {
        Long usersId = users.getId();
        if (users.hasRole("ROLE_ADMIN")) {
            return true;
        }
        for (Appointment a : invoice.getAppointments()) {
            if (a.getProvider().getId() == usersId || a.getCustomer().getId() == usersId) {
                return true;
            }
        }
        return false;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeInvoiceStatusToPaid(Long invoiceId) {
        Invoice invoice = invoiceRepository.getOne(invoiceId);
        invoice.setStatus("paid");
        invoiceRepository.save(invoice);
    }

    @Transactional
    @Override
    public void issueInvoicesForConfirmedAppointments() {
        List<Customer> customers = usersService.getAllCustomers();
        for (Customer customer : customers) {
            List<Appointment> appointmentsToIssueInvoice = appointmentService.getConfirmedAppointmentsByCustomerId(customer.getId());
            if (!appointmentsToIssueInvoice.isEmpty()) {
                for (Appointment a : appointmentsToIssueInvoice) {
                    a.setStatus(AppointmentStatus.INVOICED);
                    appointmentService.updateAppointment(a);
                }
                Invoice invoice = new Invoice(generateInvoiceNumber(), "issued", LocalDateTime.now(), appointmentsToIssueInvoice);
                invoiceRepository.save(invoice);
                notificationService.newInvoice(invoice, true);
            }

        }
    }
}

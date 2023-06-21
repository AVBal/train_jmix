package com.company.train.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Table(name = "TRAIN_STAGE", indexes = {
        @Index(name = "IDX_TRAIN_STAGE_CONTRACT", columnList = "CONTRACT_ID"),
        @Index(name = "IDX_TRAIN_STAGE_INVOICE", columnList = "INVOICE_ID"),
        @Index(name = "IDX_TRAIN_STAGE_SERVICE_COMPLETION_CERTIFICATE", columnList = "SERVICE_COMPLETION_CERTIFICATE_ID")
})
@Entity(name = "train_Stage")
public class Stage {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "DATE_FROM", nullable = false)
    @NotNull
    private LocalDate dateFrom;

    @Column(name = "DATE_TO", nullable = false)
    @NotNull
    private LocalDate dateTo;

    @NotNull
    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "VAT")
    private Double vat;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "DESCRIPTION")
    private String description;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "CONTRACT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Contract contract;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @Composition
    @JoinColumn(name = "INVOICE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "SERVICE_COMPLETION_CERTIFICATE_ID")
    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    private ServiceCompletionCertificate serviceCompletionCertificate;

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getVat() {
        return vat;
    }

    public ServiceCompletionCertificate getServiceCompletionCertificate() {
        return serviceCompletionCertificate;
    }

    public void setServiceCompletionCertificate(ServiceCompletionCertificate serviceCompletionCertificate) {
        this.serviceCompletionCertificate = serviceCompletionCertificate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
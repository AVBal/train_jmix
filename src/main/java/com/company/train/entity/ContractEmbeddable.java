package com.company.train.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JmixEntity(name = "train_ContractEmbeddable")
@Embeddable
public class ContractEmbeddable {
    @Column(name = "NUMBER_", nullable = false)
    @NotNull
    @InstanceName
    private String number;

    @Column(name = "DATE_")
    private LocalDate date;

    @Column(name = "AMOUNT", nullable = false)
    @NotNull
    private Double amount;

    @Column(name = "VAT")
    private Double vat;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "STATUS")
    private Integer status = Status.NEW.getId();

    @Column(name = "DESCRIPTION")
    private String description;

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getVat() {
        return vat;
    }

    public void setStatus(Status status) {
        this.status = status == null ? null : status.getId();
    }

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
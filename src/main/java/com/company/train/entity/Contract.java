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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "TRAIN_CONTRACT", indexes = {
        @Index(name = "IDX_TRAIN_CONTRACT_CUSTOMER", columnList = "CUSTOMER_ID"),
        @Index(name = "IDX_TRAIN_CONTRACT_PERFORMER", columnList = "PERFORMER_ID")
})
@Entity(name = "train_Contract")
public class Contract {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Organization customer;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PERFORMER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Organization performer;

    @Column(name = "NUMBER_", nullable = false)
    @NotNull
    @InstanceName
    private String number;

    @Column(name = "SIGNED_DATE", nullable = false)
    @NotNull
    private LocalDate signedDate;

    @Column(name = "TYPE_")
    private String type;

    @Column(name = "DATE_FROM", nullable = false)
    @NotNull
    private LocalDate dateFrom;

    @Column(name = "DATE_TO", nullable = false)
    @NotNull
    private LocalDate dateTo;

    @Column(name = "AMOUNT", nullable = false)
    @NotNull
    private Double amount;

    @Column(name = "VAT")
    private Double vat;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "CUSTOMER_SIGNER")
    private String customerSigner;

    @Column(name = "PERFORMER_SIGNER")
    private String performerSigner;

    @Column(name = "STATUS")
    private Integer status = Status.NEW.getId();

    @Composition
    @OneToMany(mappedBy = "contract")
    private List<Stage> stages;

    @Composition
    @OneToMany(mappedBy = "contract")
    private List<ContractFile> files = new ArrayList<>();

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getVat() {
        return vat;
    }

    public List<ContractFile> getFiles() {
        return files;
    }

    public void setFiles(List<ContractFile> files) {
        this.files = files;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public void setStatus(Status status) {
        this.status = status == null ? null : status.getId();
    }

    public String getPerformerSigner() {
        return performerSigner;
    }

    public void setPerformerSigner(String performerSigner) {
        this.performerSigner = performerSigner;
    }

    public String getCustomerSigner() {
        return customerSigner;
    }

    public void setCustomerSigner(String customerSigner) {
        this.customerSigner = customerSigner;
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

    public ContractType getType() {
        return type == null ? null : ContractType.fromId(type);
    }

    public void setType(ContractType type) {
        this.type = type == null ? null : type.getId();
    }

    public LocalDate getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(LocalDate signedDate) {
        this.signedDate = signedDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Organization getPerformer() {
        return performer;
    }

    public void setPerformer(Organization performer) {
        this.performer = performer;
    }

    public Organization getCustomer() {
        return customer;
    }

    public void setCustomer(Organization customer) {
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
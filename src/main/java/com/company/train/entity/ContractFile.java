package com.company.train.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "TRAIN_CONTRACT_FILE", indexes = {
        @Index(name = "IDX_TRAIN_CONTRACT_FILE_CONTRACT", columnList = "CONTRACT_ID"),
        @Index(name = "IDX_TRAIN_CONTRACT_FILE_INVOICE", columnList = "INVOICE_ID"),
        @Index(name = "IDX_TRAIN_CONTRACT_FILE_SERVICE_COMPLETION_CERTIFICATE", columnList = "SERVICE_COMPLETION_CERTIFICATE_ID")
})
@Entity(name = "train_ContractFile")
public class ContractFile {
    @InstanceName
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "FILE_", nullable = false, length = 1024)
    @NotNull
    private FileRef file;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Contract contract;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "INVOICE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "SERVICE_COMPLETION_CERTIFICATE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ServiceCompletionCertificate serviceCompletionCertificate;

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

    public FileRef getFile() {
        return file;
    }

    public void setFile(FileRef file) {
        this.file = file;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
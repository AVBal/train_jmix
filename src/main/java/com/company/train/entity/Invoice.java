package com.company.train.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "TRAIN_INVOICE")
@Entity(name = "train_Invoice")
public class Invoice {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "FIELDS_NUMBER_", nullable = false)),
            @AttributeOverride(name = "date", column = @Column(name = "FIELDS_DATE_")),
            @AttributeOverride(name = "amount", column = @Column(name = "FIELDS_AMOUNT", nullable = false)),
            @AttributeOverride(name = "vat", column = @Column(name = "FIELDS_VAT")),
            @AttributeOverride(name = "totalAmount", column = @Column(name = "FIELDS_TOTAL_AMOUNT")),
            @AttributeOverride(name = "description", column = @Column(name = "FIELDS_DESCRIPTION")),
            @AttributeOverride(name = "status", column = @Column(name = "FIELDS_STATUS"))
    })
    private ContractEmbeddable fields;

    @Composition
    @OneToMany(mappedBy = "invoice")
    private List<ContractFile> files;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "STAGE_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<ContractFile> getFiles() {
        return files;
    }

    public void setFiles(List<ContractFile> files) {
        this.files = files;
    }

    public ContractEmbeddable getFields() {
        return fields;
    }

    public void setFields(ContractEmbeddable fields) {
        this.fields = fields;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
package com.company.train.app.index;

import com.company.train.entity.Contract;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = Contract.class)
public interface ContractIndexDefinition {
    @AutoMappedField(includeProperties =
            {"number", "type", "status", "customerSigner",
                    "performerSigner", "customer.name",
                    "performer.name", "files.file"})
    void orderMapping();
}

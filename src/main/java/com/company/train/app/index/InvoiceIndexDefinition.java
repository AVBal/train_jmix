package com.company.train.app.index;

import com.company.train.entity.Invoice;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = Invoice.class)
public interface InvoiceIndexDefinition {
    @AutoMappedField(includeProperties =
            {"fields.number", "fields.description",
                    "fields.status", "files.file"})
    void orderMapping();
}

package com.company.train.app.index;

import com.company.train.entity.ServiceCompletionCertificate;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = ServiceCompletionCertificate.class)
public interface ServiceCompletionCertificateIndexDefinition {
    @AutoMappedField(includeProperties =
            {"fields.number", "fields.description",
                    "fields.status", "files"})
    void orderMapping();
}

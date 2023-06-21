package com.company.train.app.index;

import com.company.train.entity.Organization;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = Organization.class)
public interface OrganizationIndexDefinition {
    @AutoMappedField(includeProperties = {"name", "taxNumber", "registrationNumber"})
    void orderMapping();
}

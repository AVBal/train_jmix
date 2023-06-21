package com.company.train.app.index;

import com.company.train.entity.Stage;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = Stage.class)
public interface StageIndexDefinition {
    @AutoMappedField(includeProperties = {"name", "description"})
    void orderMapping();
}

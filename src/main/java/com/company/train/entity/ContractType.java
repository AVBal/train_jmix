package com.company.train.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum ContractType implements EnumClass<String> {

    FIX_PRICE("fixPrice"),
    TIME_AND_MATERIAL("timeAndMaterial"),
    OUT_STAFF("outStaff");

    private String id;

    ContractType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ContractType fromId(String id) {
        for (ContractType at : ContractType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
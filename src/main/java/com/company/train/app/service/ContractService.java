package com.company.train.app.service;

import com.company.train.entity.Contract;
import com.company.train.entity.Organization;
import com.company.train.entity.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service("train_ContractService")
public class ContractService {
    @Value("${vat}")
    public float vat;

    public void checkVatContract(Contract contract) {
        Double amount = contract.getAmount();
        Boolean escapeVat = escapeVat(contract);
        if (escapeVat != null && amount != null) {
            contract.setVat(escapeVat ? 0d : amount * vat / 100);
            contract.setTotalAmount(amount + contract.getVat());
        }
    }

    public void checkVatStage(Stage stage) {
        Double amount = stage.getAmount();
        Boolean escapeVat = escapeVat(stage.getContract());
        if (escapeVat != null && amount != null) {
            stage.setVat(escapeVat ? 0d : amount * vat / 100);
            stage.setTotalAmount(amount + stage.getVat());
        }
    }

    @Nullable
    private Boolean escapeVat(Contract contract) {
        Organization customer = contract.getCustomer();
        Organization performer = contract.getPerformer();
        if (customer != null && performer != null) {
            Boolean customerEscapeVat = customer.getEscapeVat();
            Boolean performerEscapeVat = performer.getEscapeVat();
            return (customerEscapeVat != null && customerEscapeVat) ||
                    (performerEscapeVat != null && performerEscapeVat);
        }
        return null;
    }

}
package com.company.train.app.bpm;

import com.company.train.entity.Contract;
import com.company.train.entity.Invoice;
import com.company.train.entity.ServiceCompletionCertificate;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("train_BpmContractSwitchStatusEventListener")
public class BpmContractSwitchStatusEventListener {
    @Autowired
    private Emailer emailer;
    @Autowired
    private Messages messages;
    @Autowired
    private DataManager dataManager;

    public void notify(Contract contract) {
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(getEmails())
                .setSubject("Смена статуса контракта")
                .setBody("Статус контракта № " + contract.getNumber() +
                        " изменился на \"" + messages.getMessage(contract.getStatus()) + "\"")
                .build();
        emailer.sendEmailAsync(emailInfo);
    }

    public void notify(Invoice invoice) {
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(getEmails())
                .setSubject("Смена статуса счета")
                .setBody("Статус счета № " + invoice.getFields().getNumber() +
                        " изменился на \"" + messages.getMessage(invoice.getFields().getStatus()) + "\"")
                .build();
        emailer.sendEmailAsync(emailInfo);
    }

    public void notify(ServiceCompletionCertificate serviceCompletionCertificate) {
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(getEmails())
                .setSubject("Смена статуса акта")
                .setBody("Статус акта № " + serviceCompletionCertificate.getFields().getNumber() +
                        " изменился на \"" + messages.getMessage(serviceCompletionCertificate.getFields().getStatus()) + "\"")
                .build();
        emailer.sendEmailAsync(emailInfo);
    }

    private String getEmails() {
        List<KeyValueEntity> kvEntities = dataManager.loadValues("select u.email from train_User u " +
                        "where u.username in (select r.username from sec_RoleAssignmentEntity r " +
                        "where r.roleCode = :roleCode1 or r.roleCode = :roleCode2)")
                .properties("email")
                .parameter("roleCode1", "bpm_initiator")
                .parameter("roleCode2", "manager")
                .list();
        StringBuilder stringBuilder = new StringBuilder();
        for (KeyValueEntity kv : kvEntities) {
            if (kv.getValue("email") != null) {
                stringBuilder.append(",").append((String) kv.getValue("email"));
            }
        }
        return stringBuilder.substring(1);
    }
}
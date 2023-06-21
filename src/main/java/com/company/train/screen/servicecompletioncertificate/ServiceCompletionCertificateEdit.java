package com.company.train.screen.servicecompletioncertificate;

import com.company.train.entity.*;
import com.company.train.screen.contractfilefragment.ContractFileFragment;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.Outcome;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UiController("train_ServiceCompletionCertificate.edit")
@UiDescriptor("service-completion-certificate-edit.xml")
@EditedEntityContainer("serviceCompletionCertificateDc")
@ProcessForm(
        outcomes = {
                @Outcome(id = "approve"),
                @Outcome(id = "reject")
        }
)
public class ServiceCompletionCertificateEdit extends StandardEditor<ServiceCompletionCertificate> {
    @ProcessVariable
    protected ServiceCompletionCertificate serviceCompletionCertificate;
    @Autowired
    private ContractFileFragment contractFilesFragment;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private ProcessFormContext processFormContext;
    @Autowired
    private Button rejectButton;
    @Autowired
    private Button completeTaskButton;

    @Subscribe
    public void onInit(InitEvent event) {
        if (serviceCompletionCertificate != null) {  // bpm buttons
            setEntityToEdit(serviceCompletionCertificate);
            completeTaskButton.setVisible(true);
            rejectButton.setVisible(true);
            switch (getEditedEntity().getFields().getStatus()) {
                case ON_HARMONIZATION -> completeTaskButton.setCaption("Сделать активным");
                case ACTIVE -> completeTaskButton.setCaption("Завершить");
            }
        }
    }

    @Subscribe("contractLinkButton")
    public void onContractLinkButtonClick(Button.ClickEvent event) {
        screenBuilders.editor(Contract.class, this)
                .editEntity(getEditedEntity().getStage().getContract())
                .build()
                .show()
                .addAfterCloseListener(e -> getScreenData().loadAll());
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        ServiceCompletionCertificate editedEntity = getEditedEntity();
        contractFilesFragment.setServiceCompletionCertificate(editedEntity);
        DataContext parentDataContext = dataContext.getParent();
        Stage stage = editedEntity.getStage();
        if (parentDataContext != null) {
            ContractEmbeddable fields = parentDataContext.create(ContractEmbeddable.class);
            fields.setStatus(Status.NEW);
            fields.setDate(LocalDate.now());
            fields.setAmount(stage.getAmount());
            fields.setVat(stage.getVat());
            fields.setTotalAmount(stage.getTotalAmount());
            editedEntity.setFields(fields);
            stage.setServiceCompletionCertificate(editedEntity);
        }
    }

    @Subscribe("completeTaskButton")
    public void onCompleteTaskButtonClick(Button.ClickEvent event) {
        switch (getEditedEntity().getFields().getStatus()) {
            case NEW -> getEditedEntity().getFields().setStatus(Status.ON_HARMONIZATION);
            case ON_HARMONIZATION -> getEditedEntity().getFields().setStatus(Status.ACTIVE);
            case ACTIVE -> getEditedEntity().getFields().setStatus(Status.COMPLETE);
        }
        commitChanges()
                .then(() -> {
                    processFormContext.taskCompletion()
                            .withOutcome("approve")
                            .complete();
                    closeWithDefaultAction();
                });
    }

    @Subscribe("rejectButton")
    public void onRejectButtonClick(Button.ClickEvent event) {
        getEditedEntity().getFields().setStatus(Status.CANCEL);
        commitChanges()
                .then(() -> {
                    processFormContext.taskCompletion()
                            .withOutcome("reject")
                            .complete();
                    closeWithDefaultAction();
                });
    }

}
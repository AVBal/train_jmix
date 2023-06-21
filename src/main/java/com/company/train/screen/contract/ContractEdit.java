package com.company.train.screen.contract;

import com.company.train.app.service.ContractService;
import com.company.train.entity.*;
import com.company.train.screen.contractfilefragment.ContractFileFragment;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.Outcome;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UiController("train_Contract.edit")
@UiDescriptor("contract-edit.xml")
@EditedEntityContainer("contractDc")
@ProcessForm(
        outcomes = {
                @Outcome(id = "approve"),
                @Outcome(id = "reject")
        }
)
public class ContractEdit extends StandardEditor<Contract> {
    @ProcessVariable
    protected Contract contract;
    @Autowired
    private ProcessFormContext processFormContext;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CurrencyField<Double> amountField;
    @Autowired
    private EntityPicker<Organization> customerField;
    @Autowired
    private EntityPicker<Organization> performerField;
    @Autowired
    private Table<Stage> stageTable;
    @Autowired
    private CollectionPropertyContainer<Stage> stageDc;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private ContractFileFragment contractFilesFragment;
    @Autowired
    private Button completeTaskButton;
    @Autowired
    private Button rejectButton;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private DateField<LocalDate> dateFromField;
    @Autowired
    private DateField<LocalDate> dateToField;

    @Subscribe
    public void onInit(InitEvent event) {
        dateFromField.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                dateToField.setRangeStart(e.getValue().plusDays(1));
            }
        });
        dateToField.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                dateFromField.setRangeEnd(e.getValue().minusDays(1));
            }
        });
        if (contract != null) {  // bpm buttons
            setEntityToEdit(contract);
            completeTaskButton.setVisible(true);
            rejectButton.setVisible(true);
            switch (getEditedEntity().getStatus()) {
                case ON_HARMONIZATION -> completeTaskButton.setCaption("Сделать активным");
                case ACTIVE -> completeTaskButton.setCaption("Завершить контракт");
            }
        }
        customerField.addValueChangeListener(e -> contractService.checkVatContract(getEditedEntity()));
        performerField.addValueChangeListener(e -> contractService.checkVatContract(getEditedEntity()));
        amountField.addValueChangeListener(e -> contractService.checkVatContract(getEditedEntity()));
        stageDc.addItemPropertyChangeListener(stageItemPropertyChangeEvent -> updateContractSumsAndDates());
        stageDc.addCollectionChangeListener(collectionChangeEvent -> updateContractSumsAndDates());
    }

    private void enableContractSumsAndDatesFields(boolean enable) {
        dateFromField.setEditable(enable);
        dateToField.setEditable(enable);
        amountField.setEditable(enable);
    }

    private void updateContractSumsAndDates() {
        boolean isEmptyStagesDc = stageDc.getItems().isEmpty();
        enableContractSumsAndDatesFields(isEmptyStagesDc);
        if (!stageDc.getItems().isEmpty()) {
            getEditedEntity().setAmount(stageDc.getItems().stream()  // contract sum is the sum of all stages
                    .mapToDouble(Stage::getAmount)
                    .sum());
            stageDc.getItems().stream()  // min contract date by min date of stages
                    .map(Stage::getDateFrom)
                    .min(LocalDate::compareTo)
                    .ifPresent(e -> getEditedEntity().setDateFrom(e));
            stageDc.getItems().stream()  // max contract date by max date of stages
                    .map(Stage::getDateTo)
                    .max(LocalDate::compareTo)
                    .ifPresent(e -> getEditedEntity().setDateTo(e));
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        contractFilesFragment.setContract(getEditedEntity());
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        enableContractSumsAndDatesFields(stageDc.getItems().isEmpty());
    }

    @Subscribe("createButton")
    public void onCreateButtonClick(Button.ClickEvent event) {
        Stage newStage = dataContext.create(Stage.class);
        screenBuilders.editor(stageTable)
                .newEntity(newStage)
                .withInitializer(stage -> {
                    Contract editedEntity = getEditedEntity();
                    stage.setContract(editedEntity);
                })
                .build()
                .show();
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        // create stage
        if (stageDc.getItems().size() == 0) {
            Stage stage = dataContext.create(Stage.class);
            Contract editedEntity = getEditedEntity();
            stage.setContract(editedEntity);
            stage.setDateFrom(editedEntity.getDateFrom());
            stage.setDateTo(editedEntity.getDateTo());
            stage.setAmount(editedEntity.getAmount());
            stage.setVat(editedEntity.getVat());
            stage.setTotalAmount(editedEntity.getTotalAmount());
            stageDc.getMutableItems().add(stage);
        }
    }

    @Subscribe
    public void onValidation(ValidationEvent event) {
        if (getEditedEntity().getCustomer().getId().equals(getEditedEntity().getPerformer().getId())) {
            event.addErrors(ValidationErrors.of("Организация не может быть заказчиком и исполнителем одновременно"));
        }
    }

    @Install(to = "stageTable.invoice", subject = "columnGenerator")
    private Component stageTableInvoiceColumnGenerator(Stage stage) {
        Button button = uiComponents.create(Button.class);
        button.setCaption((stage.getInvoice() == null) ? "Сформировать счет" : "Показать счет");
        button.addClickListener(e -> {
            if (stage.getInvoice() == null) {
                screenBuilders.editor(Invoice.class, this)
                        .newEntity()
                        .withParentDataContext(dataContext)
                        .withInitializer(invoice -> invoice.setStage(stage))
                        .build()
                        .show();
            } else {
                screenBuilders.editor(Invoice.class, this)
                        .editEntity(stage.getInvoice())
                        .build()
                        .show();
            }
        });
        return button;
    }

    @Install(to = "stageTable.serviceCompletionCertificate", subject = "columnGenerator")
    private Component stageTableServiceCompletionCertificateColumnGenerator(Stage stage) {
        Button button = uiComponents.create(Button.class);
        button.setCaption(stage.getServiceCompletionCertificate() == null ? "Сформировать акт" : "Показать акт");
        button.addClickListener(e -> {
            if (stage.getServiceCompletionCertificate() == null) {
                screenBuilders.editor(ServiceCompletionCertificate.class, this)
                        .newEntity()
                        .withParentDataContext(dataContext)
                        .withInitializer(serviceCompletionCertificate -> serviceCompletionCertificate.setStage(stage))
                        .build()
                        .show();
            } else {
                screenBuilders.editor(ServiceCompletionCertificate.class, this)
                        .editEntity(stage.getServiceCompletionCertificate())
                        .build()
                        .show();
            }
        });
        return button;
    }

    @Subscribe("completeTaskButton")
    public void onCompleteTaskButtonClick(Button.ClickEvent event) {
        switch (getEditedEntity().getStatus()) {
            case NEW -> getEditedEntity().setStatus(Status.ON_HARMONIZATION);
            case ON_HARMONIZATION -> getEditedEntity().setStatus(Status.ACTIVE);
            case ACTIVE -> getEditedEntity().setStatus(Status.COMPLETE);
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
        getEditedEntity().setStatus(Status.CANCEL);
        commitChanges()
                .then(() -> {
                    processFormContext.taskCompletion()
                            .withOutcome("reject")
                            .complete();
                    closeWithDefaultAction();
                });
    }

}
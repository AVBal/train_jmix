package com.company.train.screen.stage;

import com.company.train.app.service.ContractService;
import io.jmix.ui.component.CurrencyField;
import io.jmix.ui.component.DateField;
import io.jmix.ui.screen.*;
import com.company.train.entity.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UiController("train_Stage.edit")
@UiDescriptor("stage-edit.xml")
@EditedEntityContainer("stageDc")
public class StageEdit extends StandardEditor<Stage> {
    @Autowired
    private CurrencyField<Double> amountField;
    @Autowired
    private ContractService contractService;
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
        amountField.addValueChangeListener(e -> contractService.checkVatStage(getEditedEntity()));
    }

}
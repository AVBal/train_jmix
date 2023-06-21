package com.company.train.screen.contract;

import io.jmix.ui.screen.*;
import com.company.train.entity.Contract;

@UiController("train_Contract.browse")
@UiDescriptor("contract-browse.xml")
@LookupComponent("contractsTable")
public class ContractBrowse extends StandardLookup<Contract> {
}
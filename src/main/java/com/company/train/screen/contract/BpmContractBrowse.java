package com.company.train.screen.contract;

import io.jmix.ui.screen.*;
import com.company.train.entity.Contract;

@UiController("train_BpmContract.browse")
@UiDescriptor("bpm-contract-browse.xml")
@LookupComponent("contractsTable")
public class BpmContractBrowse extends StandardLookup<Contract> {
}
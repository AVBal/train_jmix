package com.company.train.screen.invoice;

import io.jmix.ui.screen.*;
import com.company.train.entity.Invoice;

@UiController("train_BpmInvoice.browse")
@UiDescriptor("bpm-invoice-browse.xml")
@LookupComponent("invoicesTable")
public class BpmInvoiceBrowse extends StandardLookup<Invoice> {
}
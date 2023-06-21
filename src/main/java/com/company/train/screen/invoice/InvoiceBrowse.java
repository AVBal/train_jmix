package com.company.train.screen.invoice;

import io.jmix.ui.screen.*;
import com.company.train.entity.Invoice;

@UiController("train_Invoice.browse")
@UiDescriptor("invoice-browse.xml")
@LookupComponent("invoicesTable")
public class InvoiceBrowse extends StandardLookup<Invoice> {
}
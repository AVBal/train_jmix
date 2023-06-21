package com.company.train.screen.servicecompletioncertificate;

import io.jmix.ui.screen.*;
import com.company.train.entity.ServiceCompletionCertificate;

@UiController("train_BpmServiceCompletionCertificate.browse")
@UiDescriptor("bpm-service-completion-certificate-browse.xml")
@LookupComponent("serviceCompletionCertificatesTable")
public class BpmServiceCompletionCertificateBrowse extends StandardLookup<ServiceCompletionCertificate> {
}
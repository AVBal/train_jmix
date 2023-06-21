package com.company.train.screen.servicecompletioncertificate;

import io.jmix.ui.screen.*;
import com.company.train.entity.ServiceCompletionCertificate;

@UiController("train_ServiceCompletionCertificate.browse")
@UiDescriptor("service-completion-certificate-browse.xml")
@LookupComponent("serviceCompletionCertificatesTable")
public class ServiceCompletionCertificateBrowse extends StandardLookup<ServiceCompletionCertificate> {
}
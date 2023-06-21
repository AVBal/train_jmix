package com.company.train.screen.contractfilefragment;

import com.company.train.entity.Contract;
import com.company.train.entity.ContractFile;
import com.company.train.entity.Invoice;
import com.company.train.entity.ServiceCompletionCertificate;
import io.jmix.core.FileRef;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.FileMultiUploadField;
import io.jmix.ui.component.Table;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UiController("train_ContractFileFragment")
@UiDescriptor("contract-file-fragment.xml")
public class ContractFileFragment extends ScreenFragment {
    private Contract contract;
    private Invoice invoice;
    private ServiceCompletionCertificate serviceCompletionCertificate;
    @Autowired
    private FileMultiUploadField fileMultiUpload;
    @Autowired
    private TemporaryStorage temporaryStorage;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private CollectionContainer<ContractFile> filesDc;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader;

    @Subscribe
    public void onInit(InitEvent event) {
        fileMultiUpload.addQueueUploadCompleteListener(queueUploadCompleteEvent -> {
            for (Map.Entry<UUID, String> entry : fileMultiUpload.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, fileName);
                ContractFile contractFile = dataContext.create(ContractFile.class);
                if (contract != null) {
                    contractFile.setContract(contract);
                } else if (invoice != null) {
                    contractFile.setInvoice(invoice);
                } else if (serviceCompletionCertificate != null) {
                    contractFile.setServiceCompletionCertificate(serviceCompletionCertificate);
                }
                contractFile.setFile(fileRef);
                filesDc.getMutableItems().add(contractFile);
            }
            notifications.create()
                    .withCaption("Загружено файлов: " + fileMultiUpload.getUploadsMap().size())
                    .show();
            fileMultiUpload.clearUploads();
        });
        fileMultiUpload.addFileUploadErrorListener(queueFileUploadErrorEvent ->
                notifications.create()
                        .withCaption("Ошибка загрузки файлов")
                        .show());
    }

    @Install(to = "filesTable.download", subject = "columnGenerator")
    private Component filesTableDownloadColumnGenerator(ContractFile contractFile) {
        Button button = uiComponents.create(Button.class);
        button.setIcon("font-icon:DOWNLOAD");
        button.addClickListener(e -> {
            downloader.download(contractFile.getFile());
        });
        return button;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setServiceCompletionCertificate(ServiceCompletionCertificate serviceCompletionCertificate) {
        this.serviceCompletionCertificate = serviceCompletionCertificate;
    }
}
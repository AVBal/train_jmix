package com.company.train.screen.organization;

import io.jmix.ui.screen.*;
import com.company.train.entity.Organization;

@UiController("train_Organization.edit")
@UiDescriptor("organization-edit.xml")
@EditedEntityContainer("organizationDc")
public class OrganizationEdit extends StandardEditor<Organization> {
}
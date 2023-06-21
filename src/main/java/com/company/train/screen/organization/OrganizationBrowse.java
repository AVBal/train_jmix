package com.company.train.screen.organization;

import io.jmix.ui.screen.*;
import com.company.train.entity.Organization;

@UiController("train_Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
public class OrganizationBrowse extends StandardLookup<Organization> {
}
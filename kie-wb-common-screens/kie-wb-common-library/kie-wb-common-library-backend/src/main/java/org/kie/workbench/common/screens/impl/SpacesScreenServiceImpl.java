/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.screens.impl;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.guvnor.structure.organizationalunit.OrganizationalUnit;
import org.guvnor.structure.organizationalunit.OrganizationalUnitService;
import org.kie.workbench.common.screens.library.api.SpacesScreenService;
import org.kie.workbench.common.screens.library.api.preferences.LibraryInternalPreferencesPortableGeneratedImpl;
import org.uberfire.preferences.shared.bean.PreferenceBeanServerStore;

@ApplicationScoped
public class SpacesScreenServiceImpl implements SpacesScreenService {

    private OrganizationalUnitService organizationalUnitService;

    private PreferenceBeanServerStore preferenceBeanServerStore;

    public SpacesScreenServiceImpl() {
    }

    @Inject
    public SpacesScreenServiceImpl(final OrganizationalUnitService organizationalUnitService,
                                   final PreferenceBeanServerStore preferenceBeanServerStore) {
        this.organizationalUnitService = organizationalUnitService;
        this.preferenceBeanServerStore = preferenceBeanServerStore;
    }

    @Override
    public Collection<OrganizationalUnit> getSpaces() {
        return organizationalUnitService.getOrganizationalUnits();
    }

    @Override
    public Response savePreference(final LibraryInternalPreferencesPortableGeneratedImpl preference) {
        preferenceBeanServerStore.save(preference);
        return Response.ok().build();
    }

    @Override
    public OrganizationalUnit getSpace(final String name) {
        return organizationalUnitService.getOrganizationalUnit(name);
    }

    @Override
    public boolean isValidGroupId(final String groupId) {
        return organizationalUnitService.isValidGroupId(groupId);
    }

    @Override
    public Response postSpace(final NewSpace newSpace) {
        organizationalUnitService.createOrganizationalUnit(newSpace.name, newSpace.groupId);
        return Response.status(201).build();
    }
}

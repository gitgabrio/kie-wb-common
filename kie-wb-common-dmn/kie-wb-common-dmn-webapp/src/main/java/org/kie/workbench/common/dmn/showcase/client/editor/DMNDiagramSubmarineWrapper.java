/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.dmn.showcase.client.editor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.kie.workbench.common.dmn.showcase.client.services.SubmarineClientDiagramServiceImpl;
import org.kie.workbench.common.stunner.core.client.annotation.DiagramEditor;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.client.service.ServiceCallback;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.promise.Promises;

@ApplicationScoped
public class DMNDiagramSubmarineWrapper {

    @Inject
    @DiagramEditor
    private DMNDiagramEditor dmnDiagramEditor;

    private PlaceManager placeManager;
    private SubmarineClientDiagramServiceImpl clientDiagramService;
    private Promises promises;

    public DMNDiagramSubmarineWrapper() {
        //CDI proxy
    }

    @Inject
    public DMNDiagramSubmarineWrapper(final PlaceManager placeManager,
                                      final SubmarineClientDiagramServiceImpl clientDiagramService,
                                      final Promises promises) {
        this.placeManager = placeManager;
        this.clientDiagramService = clientDiagramService;
        this.promises = promises;
    }

    public void newFile() {
        placeManager.registerOnOpenCallback(DMNDiagramsNavigatorScreen.DIAGRAM_EDITOR,
                                            () -> {
                                                dmnDiagramEditor.setContent("");
                                                placeManager.unregisterOnOpenCallbacks(DMNDiagramsNavigatorScreen.DIAGRAM_EDITOR);
                                            });

        placeManager.goTo(DMNDiagramsNavigatorScreen.DIAGRAM_EDITOR);
    }

    public void openFile(final Path path) {
        placeManager.registerOnOpenCallback(DMNDiagramsNavigatorScreen.DIAGRAM_EDITOR,
                                            () -> {
                                                clientDiagramService.loadAsXml(path,
                                                                               new ServiceCallback<String>() {
                                                                                   @Override
                                                                                   public void onSuccess(final String xml) {
                                                                                       dmnDiagramEditor.setContent(xml);
                                                                                       placeManager.unregisterOnOpenCallbacks(DMNDiagramsNavigatorScreen.DIAGRAM_EDITOR);
                                                                                   }

                                                                                   @Override
                                                                                   public void onError(final ClientRuntimeError error) {
                                                                                       placeManager.unregisterOnOpenCallbacks(DMNDiagramsNavigatorScreen.DIAGRAM_EDITOR);
                                                                                   }
                                                                               });
                                            });

        placeManager.goTo(DMNDiagramsNavigatorScreen.DIAGRAM_EDITOR);
    }

    @SuppressWarnings("unchecked")
    public void saveFile(final ServiceCallback<String> callback) {
        final Path path = dmnDiagramEditor.getCanvasHandler().getDiagram().getMetadata().getPath();
        dmnDiagramEditor.getContent().then(xml -> {
            clientDiagramService.saveAsXml(path,
                                           (String) xml,
                                           callback);
            return promises.resolve();
        });
    }
}

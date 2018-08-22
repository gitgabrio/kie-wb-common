/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

/**
 * If running via command line, the properties are already set in the pom.xml file.
 * If running in an IDE, set these properties in your debug configuration VM arguments.
 * On Eclipse, Run / Debug Configurations... / Arguments / VM arguments:
 * -Dsun.net.spi.nameservice.provider.1=dns,OpenShiftNameService
 * -Dsun.net.spi.nameservice.provider.2=default
 * -Dorg.guvnor.ala.openshift.access.OpenShiftClientListener.postCreate=org.guvnor.ala.openshift.dns.OpenShiftNameServiceClientListener
 * -Dorg.guvnor.ala.openshift.dns.OpenShiftNameServiceClientListener.routerHost=10.34.75.115
 */
package org.guvnor.ala.openshift.dns;
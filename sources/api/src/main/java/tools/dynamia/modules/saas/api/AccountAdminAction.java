/*
 * Copyright (C) 2023 Dynamia Soluciones IT S.A.S - NIT 900302344-1
 * Colombia / South America
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

package tools.dynamia.modules.saas.api;

import tools.dynamia.actions.AbstractAction;
import tools.dynamia.actions.ActionEvent;
import tools.dynamia.actions.ActionSelfFilter;
import tools.dynamia.integration.Containers;

public abstract class AccountAdminAction extends AbstractAction implements ActionSelfFilter {

    private boolean authorizationRequired;


    @Override
    public void beforeActionPerformed(ActionEvent evt) {
        if (isAuthorizationRequired()) {
            var provider = Containers.get().findObject(AccountAdminActionAuthorizationProvider.class);

            if (provider != null) {
                evt.stopPropagation();
                provider.authorize(this, evt, () -> actionPerformed(evt));
            }
        }
    }

    @Override
    public void afterActionPerformed(ActionEvent evt) {
        //do nothing
    }

    public boolean isAuthorizationRequired() {
        return authorizationRequired;
    }

    public void setAuthorizationRequired(boolean authorizationRequired) {
        this.authorizationRequired = authorizationRequired;
    }
}

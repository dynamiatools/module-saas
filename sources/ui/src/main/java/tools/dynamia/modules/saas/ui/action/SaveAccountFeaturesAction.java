package tools.dynamia.modules.saas.ui.action;

/*-
 * #%L
 * DynamiaModules - SaaS UI
 * %%
 * Copyright (C) 2016 - 2019 Dynamia Soluciones IT SAS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import tools.dynamia.actions.InstallAction;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.domain.AbstractEntity;
import tools.dynamia.domain.query.ListDataSet;
import tools.dynamia.modules.saas.domain.AccountFeature;
import tools.dynamia.ui.UIMessages;

@InstallAction
public class SaveAccountFeaturesAction extends AbstractCrudAction {
    public SaveAccountFeaturesAction() {
        setName("Save");
        setApplicableClass(AccountFeature.class);
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        ListDataSet<AccountFeature> list = (ListDataSet<AccountFeature>) evt.getController().getQueryResult();
        crudService().executeWithinTransaction(() -> {
            list.getData().forEach(AbstractEntity::save);
        });
        UIMessages.showMessage("OK");
    }
}

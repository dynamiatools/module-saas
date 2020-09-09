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

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Vlayout;

import tools.dynamia.actions.*;
import tools.dynamia.commons.ApplicableClass;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.crud.CrudState;
import tools.dynamia.modules.saas.api.AccountAdminAction;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.zk.actions.ButtonActionRenderer;
import tools.dynamia.zk.util.ZKUtil;

import java.util.Comparator;

@InstallAction
public class ShowAccountAdminActions extends AbstractCrudAction {

    public ShowAccountAdminActions() {
        setName("Admin");
        setImage("settings");
    }

    @Override
    public CrudState[] getApplicableStates() {
        return CrudState.get(CrudState.READ);
    }

    @Override
    public ApplicableClass[] getApplicableClasses() {
        return ApplicableClass.get(Account.class);
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {
            AccountDTO info = account.toDTO();
            ActionEventBuilder evtBuilder = (source, params) -> new ActionEvent(info, this);

            ActionLoader<AccountAdminAction> loader = new ActionLoader<>(AccountAdminAction.class);
            loader.setIgnoreRestrictions(true);
            Vlayout layout = new Vlayout();
            layout.setHflex("1");
            ButtonActionRenderer defaultRenderer = new ButtonActionRenderer();

            var actions = loader.load();
            actions.sort(Comparator.comparing(Action::getName));
            actions.forEach(a -> {
                ActionRenderer renderer = a.getRenderer() == null ? defaultRenderer : a.getRenderer();
                Object component = renderer.render(a, evtBuilder);
                if (component instanceof Button) {
                    ((Button) component).setZclass("btn btn-primary btn-block");
                }
                layout.appendChild((Component) component);
            });

            ZKUtil.showDialog("Actions for " + info.getName(), layout, "600px", "500px");

        } else {
            UIMessages.showMessage("Select account", MessageType.WARNING);
        }

    }

}

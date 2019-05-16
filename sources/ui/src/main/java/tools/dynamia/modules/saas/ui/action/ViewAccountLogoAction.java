
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

import org.zkoss.zul.Div;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.ApplicableClass;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.crud.CrudState;
import tools.dynamia.modules.entityfile.ui.components.EntityFileImage;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.zk.util.ZKUtil;

/**
 *
 * @author Mario Serrano Leones
 */
@InstallAction
public class ViewAccountLogoAction extends AbstractCrudAction {

    public ViewAccountLogoAction() {
        setName("View Logo");
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {
            showLogo(account);
        } else {
            UIMessages.showMessage("Seleccione cuenta para ver logo", MessageType.WARNING);
        }
    }

    @Override
    public ApplicableClass[] getApplicableClasses() {
        return ApplicableClass.get(Account.class);
    }

    @Override
    public CrudState[] getApplicableStates() {
        return CrudState.get(CrudState.READ);
    }

    private void showLogo(Account account) {
        if (account.getLogo() == null) {
            UIMessages.showMessage("La cuenta seleccionada no tiene logo", MessageType.WARNING);
        } else {
            System.out.println(account.getLogo().getStoredEntityFile().getThumbnailUrl());
            EntityFileImage image = new EntityFileImage();
            image.setValue(account.getLogo());

            Div div = new Div();
            div.setHflex("1");
            div.setVflex("1");
            div.setStyle("overflow:auto;background:silver");

            div.appendChild(image);

            ZKUtil.showDialog("Logo " + account.getName(), div, "600px", "500px");

        }
    }

}

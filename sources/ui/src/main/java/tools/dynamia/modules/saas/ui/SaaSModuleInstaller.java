package tools.dynamia.modules.saas.ui;

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

import org.springframework.stereotype.Component;
import tools.dynamia.crud.CrudPage;
import tools.dynamia.modules.saas.domain.*;
import tools.dynamia.navigation.Module;
import tools.dynamia.navigation.ModuleProvider;

/**
 * @author Mario Serrano Leones
 */
@Component
public class SaaSModuleInstaller implements ModuleProvider {

    @Override
    public Module getModule() {
        Module module = new Module("saas", "SaaS");
        module.setIcon("globe");
        module.setPosition(-1);
        module.addPage(new CrudPage("accounts", "Accounts", Account.class));
        module.addPage(new CrudPage("accountsType", "Accounts Type", AccountType.class));
        module.addPage(new CrudPage("accountProfile", "Profiles", AccountProfile.class));
        module.addPage(new CrudPage("accountPayments", "Payments", AccountPayment.class));
        module.addPage(new CrudPage("accountPaymentsMethods", "Payments Methods", AccountPaymentMethod.class));

        return module;
    }

}

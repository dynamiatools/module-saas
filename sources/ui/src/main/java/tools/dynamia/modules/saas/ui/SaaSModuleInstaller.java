/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.ui;

import org.springframework.stereotype.Component;

import tools.dynamia.crud.CrudPage;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountPayment;
import tools.dynamia.modules.saas.domain.AccountProfile;
import tools.dynamia.modules.saas.domain.AccountType;
import tools.dynamia.navigation.Module;
import tools.dynamia.navigation.ModuleProvider;

/**
 *
 * @author mario_2
 */
@Component
public class SaaSModuleInstaller implements ModuleProvider {

    @Override
    public Module getModule() {
        Module module = new Module("saas", "SaaS");
        module.setIcon("globe");
        module.addPage(new CrudPage("accounts", "Accounts", Account.class));
        module.addPage(new CrudPage("accountsType", "Accounts Type", AccountType.class));
        module.addPage(new CrudPage("accountProfile", "Profiles", AccountProfile.class));
        module.addPage(new CrudPage("accountPayments", "Payments", AccountPayment.class));

        return module;
    }

}

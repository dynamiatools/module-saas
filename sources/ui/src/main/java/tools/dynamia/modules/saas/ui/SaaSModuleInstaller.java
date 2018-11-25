
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
 * @author Mario Serrano Leones
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dynamia.modules.saas.zk;

import com.dynamia.modules.saas.domain.Account;
import com.dynamia.modules.saas.domain.AccountType;
import com.dynamia.tools.web.crud.CrudPage;
import com.dynamia.tools.web.navigation.Module;
import com.dynamia.tools.web.navigation.ModuleProvider;
import org.springframework.stereotype.Component;

/**
 *
 * @author mario_2
 */
@Component
public class SaaSModuleInstaller implements ModuleProvider{

    @Override
    public Module getModule() {
        Module module = new Module("saas", "SAAS");
        module.addPage(new CrudPage("accounts","Accounts",Account.class));
        module.addPage(new CrudPage("accountsType","Accounts Type",AccountType.class));
        
        return module;
    }
    
}

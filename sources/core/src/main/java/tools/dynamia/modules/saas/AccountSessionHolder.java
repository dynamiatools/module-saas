/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import tools.dynamia.integration.Containers;
import tools.dynamia.integration.sterotypes.Component;
import tools.dynamia.modules.saas.domain.Account;

/**
 *
 * @author mario
 */
@Component("accountSessionHolder")
@Scope("session")
public class AccountSessionHolder {

    private Account current;

    public static AccountSessionHolder get() {
        return Containers.get().findObject(AccountSessionHolder.class);
    }

    public Account getCurrent() {
        return current;
    }

    public void setCurrent(Account current) {
        this.current = current;
    }

}

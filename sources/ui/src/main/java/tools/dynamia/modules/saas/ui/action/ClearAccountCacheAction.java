package tools.dynamia.modules.saas.ui.action;

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.modules.saas.domain.Account;

@InstallAction
public class ClearAccountCacheAction extends AbstractCrudAction {

    @Autowired
    private AccountServiceAPI accountServiceAPI;

    public ClearAccountCacheAction() {
        setName("Clear Cache");
        setImage("refresh");
        setApplicableClass(Account.class);
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        accountServiceAPI.clearCache();
    }
}

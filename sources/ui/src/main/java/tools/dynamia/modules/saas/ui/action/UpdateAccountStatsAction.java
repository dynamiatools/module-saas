package tools.dynamia.modules.saas.ui.action;

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;

@InstallAction
public class UpdateAccountStatsAction extends AbstractCrudAction {

    @Autowired
    private AccountService service;

    public UpdateAccountStatsAction() {
        setName("Update Stats");
        setImage("chart");
        setApplicableClass(Account.class);
        setMenuSupported(true);
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {
            service.updateStats(account);
            UIMessages.showMessage("Account stats updated succesfully");
        } else {
            UIMessages.showMessage("Select account", MessageType.WARNING);
        }
    }
}

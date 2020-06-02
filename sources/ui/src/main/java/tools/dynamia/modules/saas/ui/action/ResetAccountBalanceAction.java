package tools.dynamia.modules.saas.ui.action;

import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.ClassMessages;
import tools.dynamia.commons.Messages;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;

import java.math.BigDecimal;

@InstallAction
public class ResetAccountBalanceAction extends AbstractCrudAction {


    public ResetAccountBalanceAction() {
        setName(msg("resetBalance"));
        setApplicableClass(Account.class);
        setMenuSupported(true);
        setImage("fa-balance-scale");
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {
            UIMessages.showQuestion(msg("confirmResetBalance"), () -> {
                crudService().executeWithinTransaction(() -> {
                    account.setBalance(BigDecimal.ZERO);
                    crudService().save(account);
                });
                evt.getController().doQuery();
                UIMessages.showMessage(msg("balanceReseted"));
            });
        } else {
            UIMessages.showMessage(msg("selectAccount"), MessageType.WARNING);
        }
    }
}

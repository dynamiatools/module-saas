package tools.dynamia.modules.saas.ui.action;

import tools.dynamia.actions.ActionGroup;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountPayment;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.zk.util.ZKUtil;
import tools.dynamia.zk.viewers.ui.Viewer;

import java.util.List;

@InstallAction
public class ViewAccountPayments extends AbstractCrudAction {

    public ViewAccountPayments() {
        setName("View Payments");
        setApplicableClass(Account.class);
        setImage("table");
        setColor("white");
        setBackground("#00b19d");
        setMenuSupported(true);
        setGroup(ActionGroup.get("PAY"));
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {
            List<AccountPayment> payments = crudService().find(AccountPayment.class, "account", account);

            Viewer viewer = new Viewer("table", AccountPayment.class, payments);
            viewer.setHeight("700px");
            viewer.setWidth("800px");
            ZKUtil.showDialog(account.toString(), viewer);
        } else {
            UIMessages.showMessage("Selecct account", MessageType.WARNING);
        }
    }
}

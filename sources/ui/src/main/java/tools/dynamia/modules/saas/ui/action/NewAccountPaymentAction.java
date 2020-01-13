package tools.dynamia.modules.saas.ui.action;

import tools.dynamia.actions.ActionGroup;
import tools.dynamia.actions.FastAction;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountPayment;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.zk.util.ZKUtil;
import tools.dynamia.zk.viewers.ui.Viewer;

@InstallAction
public class NewAccountPaymentAction extends AbstractCrudAction {

    public NewAccountPaymentAction() {
        setName("New Payment");
        setApplicableClass(Account.class);
        setImage("fa-dollar");
        setColor("white");
        setBackground("#00b19d");
        setMenuSupported(true);
        setAttribute("showLabel", true);
        setGroup(ActionGroup.get("PAY"));
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {

            AccountPayment payment = new AccountPayment();
            payment.setAccount(account);
            Viewer viewer = new Viewer("form", AccountPayment.class, payment);
            viewer.setVflex("1");
            viewer.setContentVflex("0");
            viewer.addAction(new FastAction("Create Payment", e -> {
                crudService().save(payment);
                UIMessages.showMessage("Payment created successfully");
                viewer.getParent().detach();
                evt.getController().doQuery();
            }));
            ZKUtil.showDialog(account.toString(), viewer);
        } else {
            UIMessages.showMessage("Selecct account", MessageType.WARNING);
        }
    }
}

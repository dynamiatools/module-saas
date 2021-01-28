package tools.dynamia.modules.saas.ui.action;

import tools.dynamia.actions.ActionGroup;
import tools.dynamia.actions.FastAction;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.actions.PrimaryAction;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountPayment;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.web.util.HttpUtils;
import tools.dynamia.zk.util.ZKUtil;
import tools.dynamia.zk.viewers.ui.Viewer;

@InstallAction
@PrimaryAction
public class NewAccountPaymentAction extends AbstractCrudAction {


    public NewAccountPaymentAction() {
        setName(msg("newPayment"));
        setApplicableClass(Account.class);
        setImage("payment");
        setColor("white");
        setBackground(".green");
        setMenuSupported(true);
        setAttribute("showLabel", true);
        setGroup(ActionGroup.get("CRUD"));
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {

            AccountPayment payment = new AccountPayment();
            payment.setAccount(account);
            Viewer viewer = new Viewer("form", AccountPayment.class, payment);
            if (HttpUtils.isSmartphone()) {
                viewer.setVflex("1");
                viewer.setContentVflex("0");
            }
            viewer.addAction(new FastAction(msg("createPayment"), e -> {
                UIMessages.showQuestion(msg("confirmNewPayment"), () -> {
                    payment.computeComission();
                    crudService().save(payment);
                    UIMessages.showMessage(msg("paymentCreated"));
                    viewer.getParent().detach();
                    evt.getController().doQuery();
                });
            }));
            ZKUtil.showDialog(account.toString(), viewer);
        } else {
            UIMessages.showMessage(msg("selectAccount"), MessageType.WARNING);
        }
    }
}

package tools.dynamia.modules.saas.ui.action;

import org.springframework.beans.factory.annotation.Autowired;

import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.ApplicableClass;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.crud.CrudState;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.ui.UIMessages;

@InstallAction
public class ReinitAccountAction extends AbstractCrudAction {

	@Autowired
	private AccountService service;

	public ReinitAccountAction() {
		setName("Reinit");
	}

	@Override
	public ApplicableClass[] getApplicableClasses() {
		return ApplicableClass.get(Account.class);
	}

	@Override
	public CrudState[] getApplicableStates() {
		return CrudState.get(CrudState.READ);
	}

	@Override
	public void actionPerformed(CrudActionEvent evt) {
		Account account = (Account) evt.getData();
		if (account != null) {
			UIMessages.showQuestion("Are you sure to re init account info?", () -> {
				service.initAccount(account);
				UIMessages.showMessage("Account reinit successfully");
			});
		}

	}

}

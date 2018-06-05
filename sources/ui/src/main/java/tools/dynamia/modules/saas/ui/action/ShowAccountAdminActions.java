package tools.dynamia.modules.saas.ui.action;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Vlayout;

import tools.dynamia.actions.ActionEvent;
import tools.dynamia.actions.ActionEventBuilder;
import tools.dynamia.actions.ActionLoader;
import tools.dynamia.actions.ActionRenderer;
import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.ApplicableClass;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.crud.CrudState;
import tools.dynamia.modules.saas.api.AccountAdminAction;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.zk.actions.ButtonActionRenderer;
import tools.dynamia.zk.util.ZKUtil;

@InstallAction
public class ShowAccountAdminActions extends AbstractCrudAction {

	public ShowAccountAdminActions() {
		setName("Admin");
		setImage("settings");
	}

	@Override
	public CrudState[] getApplicableStates() {
		return CrudState.get(CrudState.READ);
	}

	@Override
	public ApplicableClass[] getApplicableClasses() {
		return ApplicableClass.get(Account.class);
	}

	@Override
	public void actionPerformed(CrudActionEvent evt) {
		Account account = (Account) evt.getData();
		if (account != null) {
			AccountDTO info = account.toDTO();
			ActionEventBuilder evtBuilder = (source, params) -> new ActionEvent(info, this);

			ActionLoader<AccountAdminAction> loader = new ActionLoader<>(AccountAdminAction.class);
			loader.setIgnoreRestrictions(true);
			Vlayout layout = new Vlayout();
			layout.setHflex("1");
			ButtonActionRenderer defaultRenderer = new ButtonActionRenderer();
			
			loader.load().forEach(a -> {
				ActionRenderer renderer = a.getRenderer() == null ? defaultRenderer : a.getRenderer();
				Object component = renderer.render(a, evtBuilder);
				if(component instanceof Button){
					((Button) component).setZclass("btn btn-primary btn-block");
				}
				layout.appendChild((Component) component);
			});

			ZKUtil.showDialog("Actions for " + info.getName(), layout, "600px", "500px");

		} else {
			UIMessages.showMessage("Select account", MessageType.WARNING);
		}

	}

}

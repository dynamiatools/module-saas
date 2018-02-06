/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.ui.action;

import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.ApplicableClass;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.crud.CrudState;
import tools.dynamia.crud.actions.NewAction;
import tools.dynamia.modules.saas.domain.AccountProfile;
import tools.dynamia.modules.saas.ui.controllers.AccountProfileController;

/**
 *
 * @author mario
 */
@InstallAction
public class NewProfileAction extends NewAction {

	@Override
	public void actionPerformed(CrudActionEvent evt) {

		AccountProfileController controller = (AccountProfileController) evt.getController();
		controller.nuevo();

	}

	@Override
	public CrudState[] getApplicableStates() {
		return CrudState.get(CrudState.READ);
	}

	@Override
	public ApplicableClass[] getApplicableClasses() {
		return ApplicableClass.get(AccountProfile.class);
	}
}

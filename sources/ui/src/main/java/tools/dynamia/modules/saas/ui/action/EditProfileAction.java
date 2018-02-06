/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.ui.action;

import tools.dynamia.actions.InstallAction;
import tools.dynamia.commons.ApplicableClass;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.crud.CrudState;
import tools.dynamia.crud.actions.EditAction;
import tools.dynamia.modules.saas.domain.AccountProfile;
import tools.dynamia.modules.saas.ui.controllers.AccountProfileController;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;

/**
 *
 * @author mario
 */
@InstallAction
public class EditProfileAction extends EditAction {

	@Override
	public void actionPerformed(CrudActionEvent evt) {
		if (evt.getData() != null) {
			AccountProfile perfil = (AccountProfile) evt.getData();
			AccountProfileController controller = (AccountProfileController) evt.getController();
			controller.edit(perfil);
		} else {
			UIMessages.showMessage("Seleccione perfil a editar", MessageType.ERROR);
		}

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

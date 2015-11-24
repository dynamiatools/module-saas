/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.ui.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import tools.dynamia.domain.ValidationError;
import tools.dynamia.modules.saas.domain.AccountProfile;
import tools.dynamia.modules.saas.domain.AccountProfileRestriction;
import tools.dynamia.navigation.Module;
import tools.dynamia.navigation.ModuleContainer;
import tools.dynamia.ui.MessageType;
import tools.dynamia.ui.UIMessages;
import tools.dynamia.ui.icons.IconSize;
import tools.dynamia.zk.crud.CrudController;
import tools.dynamia.zk.util.ZKUtil;

/**
 *
 * @author mario
 */
@Component("accountProfile")
@Scope("prototype")
public class AccountProfileController extends CrudController<AccountProfile> {

	@Autowired
	private ModuleContainer moduleContainer;
	private Listbox modules;
	private Listbox permisos;
	private List<AccountProfileRestriction> toDelete = new ArrayList<>();

	// autowired by zk composer
	private org.zkoss.zk.ui.Component asignar;
	private org.zkoss.zk.ui.Component remover;
	private org.zkoss.zk.ui.Component save;

	@Override
	protected void afterPageLoaded() {
		setupActions();
		buildModel();
	}

	private void setupActions() {
		IconSize iconSize = IconSize.SMALL;
		if (asignar != null) {
			ZKUtil.configureComponentIcon("add", asignar, iconSize);
		}

		if (remover != null) {
			ZKUtil.configureComponentIcon("delete", remover, iconSize);
		}

		if (save != null) {
			ZKUtil.configureComponentIcon("save", save, iconSize);
		}

	}

	@Override
	protected void afterEdit() {
		Window window = ZKUtil.showDialog(getPagePath("new"), "Edit Profile", getSelected(), "90%", "90%");
		window.addEventListener(Events.ON_CLOSE, evt -> doQuery());

	}

	@Override
	protected void afterDelete() {
		query();
	}

	@Override
	protected void beforeSave() {

	}

	public void nuevo() {
		ZKUtil.showDialog(getPagePath("new"), "New Profile", null, "90%", "90%");
		query();
	}

	public void addAccessRestriction() {
		Module value = getSelectedModule();
		if (value == null) {
			UIMessages.showMessage("Select a module");
		} else {
			addPageRestriction(value);
		}

		updateListboxRestrictions();
	}

	private void addPageRestriction(Module mod) {
		try {

			if (mod.isVisible() && mod.isEnable()) {
				for (AccountProfileRestriction restriction : getEntity().getRestrictions()) {
					if (restriction.getValue().equals(mod.getId())) {
						UIMessages.showMessage("Restriccion existente", MessageType.WARNING);
						return;
					}
				}

				AccountProfileRestriction restriction = new AccountProfileRestriction(mod.getName(), "ACCESS", mod.getId());
				getEntity().getRestrictions().add(restriction);
				restriction.setProfile(getEntity());
			}

		} catch (ValidationError e) {
			ZKUtil.showMessage(e.getMessage(), MessageType.ERROR);
		}
	}

	public void removeAccessRestriction() {
		final AccountProfileRestriction p = getSelectedPermiso();
		if (p != null) {
			UIMessages.showQuestion("Esta seguro que desea eliminar el acceso seleccionado?", () -> {
				getEntity().getRestrictions().remove(p);
				p.setProfile(null);
				toDelete.add(p);
				updateListboxRestrictions();
			});

		} else {
			ZKUtil.showMessage("Seleccione el permiso que desea borrar", MessageType.ERROR);
		}
	}

	private void updateListboxRestrictions() {
		ZKUtil.fillListbox(permisos, getEntity().getRestrictions(), true);
	}

	private Module getSelectedModule() {
		try {
			return modules.getSelectedItem().getValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private AccountProfileRestriction getSelectedPermiso() {
		if (permisos != null) {
			return (AccountProfileRestriction) permisos.getSelectedItem().getValue();
		} else {
			return null;
		}
	}

	private void buildModel() {
		if (modules != null) {
			List<Module> model = new ArrayList<>(moduleContainer.getModules());
			Collections.sort(model);
			ZKUtil.fillListbox(modules, model, true);
		}
	}

	private String getPagePath(String name) {
		return "classpath:/zk/saas/profiles/" + name + ".zul";
	}
}

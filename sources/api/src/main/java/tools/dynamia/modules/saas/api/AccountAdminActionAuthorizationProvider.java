package tools.dynamia.modules.saas.api;

import tools.dynamia.actions.Action;
import tools.dynamia.actions.ActionEvent;
import tools.dynamia.commons.Callback;

public interface AccountAdminActionAuthorizationProvider {

    void authorize(Action action, ActionEvent evt, Callback onAuthorization);
}

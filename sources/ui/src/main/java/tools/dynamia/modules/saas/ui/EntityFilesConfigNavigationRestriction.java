package tools.dynamia.modules.saas.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.navigation.NavigationElement;
import tools.dynamia.navigation.NavigationRestriction;

@Component
public class EntityFilesConfigNavigationRestriction implements NavigationRestriction {

    @Autowired
    private AccountServiceAPI accountServiceAPI;

    @Override
    public Boolean allowAccess(NavigationElement element) {
        if (element.getVirtualPath().equals("system/config/entityFile")) {
            Long accountId = accountServiceAPI.getCurrentAccountId();
            if (accountId != null) {
                AccountDTO accountDTO = accountServiceAPI.getAccount(accountId);
                if (accountDTO != null && !accountDTO.getType().equals("admin")) {
                    return false;
                }
            }

        }
        return null;
    }

    @Override
    public int getOrder() {

        return 0;
    }

}

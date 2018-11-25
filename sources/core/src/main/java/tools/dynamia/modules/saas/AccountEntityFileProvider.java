
package tools.dynamia.modules.saas;

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.integration.sterotypes.Provider;
import tools.dynamia.modules.entityfile.EntityFileAccountProvider;
import tools.dynamia.modules.saas.api.AccountServiceAPI;

/**
 *
 * @author Mario Serrano Leones
 */
@Provider
public class AccountEntityFileProvider implements EntityFileAccountProvider {

    @Autowired
    private AccountServiceAPI accountServiceAPI;

    @Override
    public Long getAccountId() {
        return accountServiceAPI.getCurrentAccountId();
    }

}

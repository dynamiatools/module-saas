
package tools.dynamia.modules.saas.api;

import tools.dynamia.modules.saas.api.dto.AccountDTO;

/**
 * @author Mario Serrano Leones
 */
public interface AccountInitializer {

    void init(AccountDTO accountDTO);

    /**
     * Define account initializer priority or order. Lower value is high priority
     * @return
     */
    default int getPriority() {
        return 0;
    }

}

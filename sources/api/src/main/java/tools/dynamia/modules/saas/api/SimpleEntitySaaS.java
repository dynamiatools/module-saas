
package tools.dynamia.modules.saas.api;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import tools.dynamia.domain.SimpleEntity;

/**
 *
 * @author Mario Serrano Leones
 */
@MappedSuperclass
public abstract class SimpleEntitySaaS extends SimpleEntity implements AccountAware {

    @NotNull
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}

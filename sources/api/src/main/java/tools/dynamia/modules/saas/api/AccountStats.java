package tools.dynamia.modules.saas.api;

import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.io.Serializable;

public class AccountStats implements Serializable {

    private Long accountId;
    private String name;
    private String value;
    private String description;
    private long quantity;
    private String module;

    public AccountStats() {

    }

    public AccountStats(Long accountId, String name, String value) {
        this.accountId = accountId;
        this.name = name;
        this.value = value;
    }

    public AccountStats(Long accountId, String name, long quantity) {
        this.accountId = accountId;
        this.name = name;
        this.quantity = quantity;
        this.value = String.valueOf(quantity);
    }

    public AccountStats(Long accountId, String name, String value, String description, long quantity) {
        this.accountId = accountId;
        this.name = name;
        this.value = value;
        this.description = description;
        this.quantity = quantity;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public AccountStats module(String module) {
        this.module = module;
        return this;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "AccountStats{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", module='" + module + '\'' +
                '}';
    }
}

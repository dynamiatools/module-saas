package tools.dynamia.modules.saas.api.dto;

import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountStatusDTO implements Serializable {


    private Long id;
    private String name;
    private AccountStatus status;
    private Date statusDate;
    private String statusDescription;
    private String globalMessage;
    private boolean showGlobalMessage;
    private String globalMessageType;
    private final BigDecimal balance;


    public AccountStatusDTO(Long id, String name, AccountStatus status, Date statusDate, String statusDescription,
                            String globalMessage, boolean showGlobalMessage, String globalMessageType, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.statusDate = statusDate;
        this.statusDescription = statusDescription;
        this.globalMessage = globalMessage;
        this.showGlobalMessage = showGlobalMessage;
        this.globalMessageType = globalMessageType;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public String getStatusDescription() {
        if (statusDescription == null) {
            statusDescription = "";
        }
        return statusDescription;
    }

    public String getGlobalMessage() {
        return globalMessage;
    }

    public boolean isShowGlobalMessage() {
        return showGlobalMessage;
    }

    public String getGlobalMessageType() {
        return globalMessageType;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

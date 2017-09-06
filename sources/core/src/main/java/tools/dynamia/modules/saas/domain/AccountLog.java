package tools.dynamia.modules.saas.domain;

import tools.dynamia.domain.SimpleEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "saas_logs")
public class AccountLog extends SimpleEntity {


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logDate")
    private Date date = new Date();

    @OneToOne
    @NotNull
    private Account account;
    private String ip;
    private String pathInfo;
    private String message;
    @Column(length = 2000)
    private String clientInfo;

    public AccountLog() {
    }

    public AccountLog(Account account, String ip, String message) {
        this.account = account;
        this.ip = ip;
        this.message = message;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}

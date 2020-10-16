package tools.dynamia.modules.saas.domain;

/*-
 * #%L
 * DynamiaModules - SaaS Core
 * %%
 * Copyright (C) 2016 - 2019 Dynamia Soluciones IT SAS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import tools.dynamia.domain.jpa.BaseEntity;
import tools.dynamia.domain.Transferable;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "saas_logs")
public class AccountLog extends BaseEntity implements Transferable<AccountLogDTO> {


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

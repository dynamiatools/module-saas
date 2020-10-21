
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

import org.hibernate.annotations.BatchSize;
import tools.dynamia.domain.jpa.SimpleEntity;
import tools.dynamia.domain.Transferable;
import tools.dynamia.domain.contraints.NotEmpty;
import tools.dynamia.domain.util.DomainUtils;
import tools.dynamia.modules.saas.api.dto.AccountTypeDTO;
import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mario Serrano Leones
 */
@Entity
@Table(name = "saas_account_types")
@BatchSize(size = 10)
public class AccountType extends SimpleEntity implements Transferable<AccountTypeDTO> {

    @NotNull
    @NotEmpty(message = "ingrese nombre del tipo de cuenta")
    private String name;
    private String description;
    private String internalDescription;
    private boolean active;
    private boolean publicType;
    private String publicName;
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTypeRestriction> restrictions = new ArrayList<>();
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private AccountPeriodicity periodicity = AccountPeriodicity.MONTHLY;
    private BigDecimal price;
    @Min(value = 1, message = "Enter valid max users")
    private int maxUsers = 1;
    private boolean allowAdditionalUsers;
    private BigDecimal additionalUserPrice;
    private boolean printingSupport;
    private int allowedOverdueDays = 5;
    private String reference;
    private boolean paymentRequired;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public boolean isAllowAdditionalUsers() {
        return allowAdditionalUsers;
    }

    public void setAllowAdditionalUsers(boolean allowAdditionalUsers) {
        this.allowAdditionalUsers = allowAdditionalUsers;
    }

    public BigDecimal getAdditionalUserPrice() {
        return additionalUserPrice;
    }

    public void setAdditionalUserPrice(BigDecimal additionalUserPrice) {
        this.additionalUserPrice = additionalUserPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInternalDescription() {
        return internalDescription;
    }

    public void setInternalDescription(String internalDescription) {
        this.internalDescription = internalDescription;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPublicType() {
        return publicType;
    }

    public void setPublicType(boolean publicType) {
        this.publicType = publicType;
    }

    public List<AccountTypeRestriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<AccountTypeRestriction> restrictions) {
        this.restrictions = restrictions;
    }

    public AccountPeriodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(AccountPeriodicity periodicity) {
        this.periodicity = periodicity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isPrintingSupport() {
        return printingSupport;
    }

    public void setPrintingSupport(boolean printingSupport) {
        this.printingSupport = printingSupport;
    }

    @Override
    public AccountTypeDTO toDTO() {
        AccountTypeDTO dto = DomainUtils.autoDataTransferObject(this, AccountTypeDTO.class);
        try {
            getRestrictions().forEach(r -> dto.getRestrictions().add(r.toDTO()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    public int getAllowedOverdueDays() {
        return allowedOverdueDays;
    }

    public void setAllowedOverdueDays(int allowedOverdueDays) {
        this.allowedOverdueDays = allowedOverdueDays;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPublicName() {
        if (publicName == null) {
            return getName();
        }
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public boolean isPaymentRequired() {
        return paymentRequired;
    }

    public void setPaymentRequired(boolean paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public boolean isAdmin() {
        return "admin".equals(name);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tools.dynamia.domain.SimpleEntity;

/**
 *
 * @author mario
 */
@Entity
@Table(name = "saas_restrictions")
public class AccountTypeRestriction extends SimpleEntity {

    @ManyToOne
    private AccountType type;
    @Column(name = "rest_name")
    private String name;
    @Column(name = "rest_key")
    private String key;
    @Column(name = "rest_values", length = 5000)
    private String values;
    private boolean active;
    private String description;

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

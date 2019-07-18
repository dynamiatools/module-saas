package tools.dynamia.modules.saas.domain;

import tools.dynamia.domain.Descriptor;
import tools.dynamia.domain.OrderBy;
import tools.dynamia.domain.SimpleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "saas_payments_methods")
@OrderBy("name")
@Descriptor(fields = {"name", "active"})
public class AccountPaymentMethod extends SimpleEntity {

    @NotNull
    private String name;
    private boolean active = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }
}

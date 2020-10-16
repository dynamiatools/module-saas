package tools.dynamia.modules.saas.domain;

import tools.dynamia.domain.Descriptor;
import tools.dynamia.domain.jpa.SimpleEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "saas_categories")
@Descriptor(fields = "name")
public class AccountCategory extends SimpleEntity {

    @NotNull
    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

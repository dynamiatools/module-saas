package tools.dynamia.modules.saas.domain;

import org.hibernate.annotations.BatchSize;
import tools.dynamia.commons.StringUtils;
import tools.dynamia.domain.Descriptor;
import tools.dynamia.domain.jpa.SimpleEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "saas_sale_channels")
@Descriptor(fields = {"name"})
@BatchSize(size = 10)
public class AccountChannelSale extends SimpleEntity {

    @NotNull
    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.toUpperCase(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

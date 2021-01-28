package tools.dynamia.modules.saas.domain;

import tools.dynamia.domain.contraints.NotEmpty;
import tools.dynamia.domain.jpa.BaseEntity;
import tools.dynamia.domain.jpa.ContactInfo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "saas_resellers")
public class AccountReseller extends BaseEntity {

    @NotEmpty
    private String name;
    private String identification;
    private String idType;
    private ContactInfo contactInfo = new ContactInfo();
    private boolean enabled;
    private Long externalId;
    @OneToOne
    private Account mainAccount;
    private double comissionRate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Account getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(Account mainAccount) {
        this.mainAccount = mainAccount;
    }

    public double getComissionRate() {
        return comissionRate;
    }

    public void setComissionRate(double comissionRate) {
        this.comissionRate = comissionRate;
    }

    @Override
    public String toString() {
        return name;
    }

}

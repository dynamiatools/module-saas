package tools.dynamia.modules.saas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.dynamia.domain.DefaultEntityReferenceRepository;
import tools.dynamia.domain.EntityReferenceRepository;
import tools.dynamia.modules.saas.domain.Account;

@Configuration
public class AccountConfig {

    @Bean
    public EntityReferenceRepository<Long> accountReferenceRepository() {
        DefaultEntityReferenceRepository<Long> repo = new DefaultEntityReferenceRepository<>(Account.class, "name");
        repo.setCacheable(true);
        return repo;
    }
}

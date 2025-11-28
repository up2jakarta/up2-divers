package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.cl.SystemEntity;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@TestConfiguration
@EnableAutoConfiguration
@SpringBootConfiguration
@EntityScan(basePackageClasses = SystemEntity.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class TUConfiguration {

    @Bean
    public EntityResolver entityResolver(EntityManager entityManager) {
        return new EntityResolver(entityManager);
    }

    @Bean
    public TableResolver tableResolver(DataSource dataSource) {
        return new TableResolver(dataSource);
    }

}

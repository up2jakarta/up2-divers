package io.github.up2jakarta.test;

import io.github.up2jakarta.lov.EntityResolver;
import io.github.up2jakarta.lov.TableResolver;
import io.github.up2jakarta.test.lov.SystemEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@TestPropertySource(properties = "spring.main.banner-mode=off")
public class TUConfiguration {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("/data.sql")
                .generateUniqueName(true)
                .build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        final org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(SystemEntity.class)
                .setProperty(Environment.DIALECT, H2Dialect.class)
                .setProperty(Environment.HBM2DDL_AUTO, "none")
                .setProperty(Environment.SHOW_SQL, false);
        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .applySetting(Environment.JAKARTA_JTA_DATASOURCE, dataSource);
        return configuration.buildSessionFactory(builder.build());
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public EntityManager entityManager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean
    public EntityResolver entityResolver(EntityManager entityManager) {
        return new EntityResolver(entityManager);
    }

    @Bean
    public TableResolver tableResolver(DataSource dataSource) {
        return new TableResolver(dataSource);
    }

}

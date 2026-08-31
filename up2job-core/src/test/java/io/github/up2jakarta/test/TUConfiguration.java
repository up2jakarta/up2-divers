package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.prc.TokenProcessor;
import io.github.up2jakarta.csv.slv.DecimalResolver;
import io.github.up2jakarta.test.impl.TermType;
import jakarta.validation.Validator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.StepRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = {TokenProcessor.class, DecimalResolver.class, TermType.class})
@EnableTransactionManagement
@EnableAutoConfiguration
public class TUConfiguration {

    static final Logger LOG = LoggerFactory.getLogger(TUConfiguration.class);

    static {
        Locale.setDefault(Locale.US);
    }

    @Bean
    protected PlatformTransactionManager transactionManager(DataSource dataSource) {
        final JdbcTransactionManager manager = new JdbcTransactionManager(dataSource);
        manager.setLazyInit(false);
        manager.setNestedTransactionAllowed(true);
        return manager;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("/org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Bean
    public Validator validator() {
        return Up2Factory.validator(
                new ParameterMessageInterpolator(
                        Set.of(Locale.ENGLISH),
                        Locale.ENGLISH,
                        context -> Locale.ENGLISH,
                        false
                )
        );
    }

    @Bean
    public Container container(final ApplicationContext context) {
        return context::getBean;
    }

    @Bean
    public CSVFormat format() {
        return CSVFormat.RFC4180.builder()
                .setQuoteMode(QuoteMode.MINIMAL)
                .setQuote('"')
                .setDelimiter(';')
                .setNullString("")
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .setIgnoreSurroundingSpaces(true)
                .setCommentMarker('-')
                .get();
    }

    @Bean
    protected AsyncTaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor("ps-");
    }

    @Bean
    protected StepRunner stepRunner(JobLauncher launcher, JobRepository repository) {
        return new StepRunner(launcher, repository);
    }

}

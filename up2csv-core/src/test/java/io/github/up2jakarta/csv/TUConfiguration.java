package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.misc.CompositeKeyCreator;
import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.processor.TokenProcessor;
import io.github.up2jakarta.csv.resolver.DecimalResolver;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.extension.DummyConverter;
import io.github.up2jakarta.csv.test.persistence.InputErrorEntity;
import io.github.up2jakarta.csv.test.persistence.InputRowEntity;
import io.github.up2jakarta.csv.test.persistence.SimpleErrorEntity;
import jakarta.validation.Validator;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static io.github.up2jakarta.csv.test.Tests.messageInterpolator;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@ComponentScan(basePackageClasses = {
        MapperFactory.class, TokenProcessor.class, DecimalResolver.class,
        DummyConverter.class, CurrencyConverter.class // Test
})
public class TUConfiguration {

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public CollapsedStringAdapter tokenAdapter() {
        return new CollapsedStringAdapter(); // for @Up2Token
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public Validator validator() {
        return CSV.validator(messageInterpolator());
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public BeanContext beanContext(final ApplicationContext context) {
        return context::getBean;
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InputRepository<InputRowEntity> inputRepository() {
        return r -> 0;
    }

    /**
     * Choose one {@link io.github.up2jakarta.csv.core.EventCreator} depends on your implementation:
     *
     * @see #compositeKeyCreator() for composite key implementation
     */
    @Bean
    @Scope(value = SCOPE_SINGLETON)
    @Deprecated(forRemoval = true)
    public SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> simpleKeyCreator() {
        return new SimpleKeyCreator<>(SimpleErrorEntity::new);
    }

    /**
     * Choose one {@link io.github.up2jakarta.csv.core.EventCreator} depends on your implementation:
     *
     * @see #simpleKeyCreator() for simple key implementation
     */
    @Bean
    @Scope(value = SCOPE_SINGLETON)
    @Deprecated(forRemoval = true)
    public CompositeKeyCreator<InputRowEntity, InputErrorEntity.PKey, InputErrorEntity> compositeKeyCreator() {
        return new CompositeKeyCreator<>(InputErrorEntity::new, InputErrorEntity.PKey::new);
    }

}

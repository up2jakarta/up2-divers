package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.processor.DefaultValueTransformer;
import io.github.up2jakarta.csv.processor.TokenTransformer;
import io.github.up2jakarta.csv.processor.TrimTransformer;
import io.github.up2jakarta.csv.test.DummyTransformer;
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
@ComponentScan(basePackageClasses = {DummyTransformer.class})
public class TUConfiguration {

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public CollapsedStringAdapter tokenAdapter() {
        return new CollapsedStringAdapter();
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public TokenTransformer tokenTransformer(CollapsedStringAdapter tokenAdapter) {
        return new TokenTransformer(tokenAdapter);
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public TrimTransformer trimTransformer() {
        return new TrimTransformer();
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public DefaultValueTransformer defaultValueTransformer() {
        return new DefaultValueTransformer();
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
    public MapperFactory mapperFactory(final BeanContext context, Validator validator) {
        return new MapperFactory(context, validator);
    }

}

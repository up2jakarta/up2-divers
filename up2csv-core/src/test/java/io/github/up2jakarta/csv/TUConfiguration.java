package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.processor.TokenProcessor;
import io.github.up2jakarta.csv.resolver.DecimalResolver;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.ext.DummyConverter;
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
        DummyConverter.class, CurrencyConverter.class
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
        return MapperFactory.validator(messageInterpolator());
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public BeanContext beanContext(final ApplicationContext context) {
        return context::getBean;
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    DataIdResolver resolver() {
        return DataIdResolver.INSTANCE;
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InvoiceAggregator invoiceAggregator(MapperFactory<DataId> factory, ErrorCreator creator) throws BeanException {
        return new InvoiceAggregator(factory, creator);
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InvoiceSeparator invoiceSeparator(MapperFactory<DataId> factory) throws BeanException {
        return new InvoiceSeparator(factory);
    }

    /**
     * Choose one {@link io.github.up2jakarta.csv.core.EventCreator} depends on your implementation:
     *
     * @see #compositeKeyCreator() for composite key implementation
     */
    @Bean
    @Scope(value = SCOPE_SINGLETON)
    @Deprecated(forRemoval = true)
    public SimpleCreator simpleKeyCreator() {
        return new SimpleCreator();
    }

    /**
     * Choose one {@link io.github.up2jakarta.csv.core.EventCreator} depends on your implementation:
     *
     * @see #simpleKeyCreator() for simple key implementation
     */
    @Bean
    @Scope(value = SCOPE_SINGLETON)
    @Deprecated(forRemoval = true)
    public ErrorCreator compositeKeyCreator() {
        return new ErrorCreator();
    }

}

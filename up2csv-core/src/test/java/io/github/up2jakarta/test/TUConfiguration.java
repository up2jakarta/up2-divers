package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.SimpleTermResolver;
import io.github.up2jakarta.csv.data.TermResolver;
import io.github.up2jakarta.csv.prc.TokenProcessor;
import io.github.up2jakarta.csv.slv.DecimalResolver;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.core.misc.cvr.NumberConverter;
import io.github.up2jakarta.test.core.misc.ext.DummyConverter;
import io.github.up2jakarta.test.core.misc.lov.CurrencyConverter;
import io.github.up2jakarta.test.fmt.sln.Tree90Linker;
import io.github.up2jakarta.test.impl.*;
import io.github.up2jakarta.test.impl.sln.InvoiceItemLinker;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Locale;
import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = {
        Up2Factory.class, TokenProcessor.class, DecimalResolver.class,
        DummyConverter.class, CurrencyConverter.class, NumberConverter.class,
        InvoiceItemLinker.class, Tree90Linker.class
})
public class TUConfiguration {

    @Bean
    public Validator validator() {
        return Up2Factory.validator(
                new ParameterMessageInterpolator(
                        Set.of(Locale.ENGLISH, Locale.FRENCH),
                        Locale.ENGLISH,
                        context -> Locale.ENGLISH,
                        false
                )
        );
    }

    @Bean
    public Container container(final ApplicationContext context) {
        return new Container() {
            @Override
            public <B> B getBean(Class<B> type) {
                return context.getBean(type);
            }

            @Override
            public <B> B getBean(Class<B> type, String name) {
                return context.getBean(name, type);
            }
        };
    }

    @Bean
    TermResolver<TermType> resolver() {
        return new SimpleTermResolver<>(TermType.class, BusinessType.class, BusinessType::value);
    }

    @Bean
    @Lazy
    public MyFullAggregator invoiceFullAggregator(Up2Factory<TermType> factory) throws BeanException {
        return new MyFullAggregator(factory);
    }

    @Bean
    @Lazy
    public MyFastAggregator invoiceFastAggregator(Up2Factory<TermType> factory) throws BeanException {
        return new MyFastAggregator(factory);
    }

    @Bean
    @Lazy
    public MyUnitAggregator invoiceUnitAggregator(Up2Factory<TermType> factory) throws BeanException {
        return new MyUnitAggregator(factory);
    }

}

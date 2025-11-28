package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyConverter;
import io.github.up2jakarta.csv.data.DataResolver;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.prc.TokenProcessor;
import io.github.up2jakarta.csv.slv.DecimalResolver;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = {
        Up2Factory.class, TokenProcessor.class, DecimalResolver.class,
        DummyConverter.class, CurrencyConverter.class
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
    public BeanContext beanContext(final ApplicationContext context) {
        return new BeanContext() {
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
    DataResolver<GroupType> resolver() {
        return new InputResolver();
    }

    @Bean
    public MyFullAggregator invoiceFullAggregator(Up2Factory<GroupType> factory) throws BeanException {
        return new MyFullAggregator(factory);
    }

    @Bean
    public MyFastAggregator invoiceFastAggregator(Up2Factory<GroupType> factory) throws BeanException {
        return new MyFastAggregator(factory);
    }

    @Bean
    public MyUnitAggregator invoiceUnitAggregator(Up2Factory<GroupType> factory) throws BeanException {
        return new MyUnitAggregator(factory);
    }

}

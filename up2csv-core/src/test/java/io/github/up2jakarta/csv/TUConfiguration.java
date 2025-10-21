package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyConverter;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.MyFastAggregator;
import io.github.up2jakarta.csv.impl.MyFullAggregator;
import io.github.up2jakarta.csv.impl.MyUnitAggregator;
import io.github.up2jakarta.csv.prc.TokenProcessor;
import io.github.up2jakarta.csv.slv.DecimalResolver;
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
        return context::getBean;
    }

    @Bean
    DataTypeResolver<GroupType> resolver() {
        return DataTypeResolver.empty(GroupType.class);
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

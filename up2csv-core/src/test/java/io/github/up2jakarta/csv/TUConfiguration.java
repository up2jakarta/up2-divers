package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.hdl.IErrorCreator;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyConverter;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InvoiceAggregator;
import io.github.up2jakarta.csv.impl.SimpleCreator;
import io.github.up2jakarta.csv.prc.TokenProcessor;
import io.github.up2jakarta.csv.slv.DecimalResolver;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Locale;
import java.util.Set;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@ComponentScan(basePackageClasses = {
        MapperFactory.class, TokenProcessor.class, DecimalResolver.class,
        DummyConverter.class, CurrencyConverter.class
})
public class TUConfiguration {

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public Validator validator() {
        return MapperFactory.validator(
                new ParameterMessageInterpolator(
                        Set.of(Locale.ENGLISH, Locale.FRENCH),
                        Locale.ENGLISH,
                        context -> Locale.ENGLISH,
                        false
                )
        );
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public BeanContext beanContext(final ApplicationContext context) {
        return context::getBean;
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    DataTypeResolver<GroupType> resolver() {
        return DataTypeResolver.empty(GroupType.class);
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InvoiceAggregator invoiceAggregator(MapperFactory<GroupType> factory, SimpleCreator creator) throws BeanException {
        return new InvoiceAggregator(factory, creator);
    }

    /**
     * Choose one {@link IErrorCreator} depends on your implementation:
     */
    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public SimpleCreator simpleCreator() {
        return new SimpleCreator();
    }

}

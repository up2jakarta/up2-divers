package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.api.TestUtil;
import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.format.standard.CrossIndustryInvoiceType;
import io.github.up2jakarta.cii.ppf.adapters.ProfileAdapter;
import io.github.up2jakarta.xml.adapters.LocalDateAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@ComponentScan(basePackageClasses = {ProfileAdapter.class, DocumentCodeType.class, LocalDateAdapter.class})
@ComponentScan(basePackageClasses = {TestUtil.class})
public class TUConfiguration {

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public SchemaValidator schemaValidator() {
        return new SchemaValidator();
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InvoiceValidator<CrossIndustryInvoiceType> invoiceValidator(final XmlAdapter<?, ?>[] adapters) {
        return new InvoiceValidator<>(CrossIndustryInvoiceType.class, adapters);
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InvoiceReader<CrossIndustryInvoiceType> invoiceReader(final XmlAdapter<?, ?>[] adapters) {
        return new InvoiceReader<>(CrossIndustryInvoiceType.class, adapters);
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InvoiceWriter<CrossIndustryInvoiceType> invoiceWriter(final XmlAdapter<?, ?>[] adapters) {
        return new InvoiceWriter<>(CrossIndustryInvoiceType.class, adapters);
    }

}

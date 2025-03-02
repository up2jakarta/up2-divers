package io.github.up2jakarta.cii.format.minified;

import io.github.up2jakarta.cii.InvoiceReader;
import io.github.up2jakarta.cii.InvoiceValidator;
import io.github.up2jakarta.cii.InvoiceWriter;
import io.github.up2jakarta.cii.api.TestUtil;
import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.ppf.adapters.ProfileAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@ComponentScan(basePackageClasses = {ProfileAdapter.class, DocumentCodeType.class, TestUtil.class})
class MiniConfiguration {

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

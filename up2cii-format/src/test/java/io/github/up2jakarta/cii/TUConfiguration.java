package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.api.TestUtil;
import io.github.up2jakarta.cii.core.ErrorEnhancer;
import io.github.up2jakarta.cii.core.LocalDateAdapter;
import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.format.standard.CrossIndustryInvoiceType;
import io.github.up2jakarta.cii.ppf.adapters.ProfileAdapter;
import io.github.up2jakarta.xml.SchemaValidator;
import io.github.up2jakarta.xml.api.XConfigurationException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static java.nio.file.Files.createDirectory;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@ComponentScan(basePackageClasses = {ProfileAdapter.class, DocumentCodeType.class, LocalDateAdapter.class})
@ComponentScan(basePackageClasses = {TestUtil.class})
public class TUConfiguration {

    static {
        // Setting Local
        Locale.setDefault(Locale.US);
        // Hacking System error
        System.setErr(System.out);
    }

    public static File xmlOutput() {
        try {
            final File out = new File(loadResource(".").getParentFile(), "workspace");
            if (!out.exists()) {
                createDirectory(out.toPath());
            }
            return out;
        } catch (IOException e) {
            throw new XConfigurationException("Cannot create XML output directory", e);
        }
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public SchemaValidator schemaValidator() {
        return new SchemaValidator(CII.getSchema(), ErrorEnhancer::enhance);
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

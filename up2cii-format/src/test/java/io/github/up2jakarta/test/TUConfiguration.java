package io.github.up2jakarta.test;

import io.github.up2jakarta.cii.CII;
import io.github.up2jakarta.cii.InvoiceReader;
import io.github.up2jakarta.cii.InvoiceValidator;
import io.github.up2jakarta.cii.InvoiceWriter;
import io.github.up2jakarta.cii.core.ErrorEnhancer;
import io.github.up2jakarta.cii.core.LocalDateAdapter;
import io.github.up2jakarta.cii.edi.adapters.AllowanceChargeIdentificationCodeAdapter;
import io.github.up2jakarta.cii.edi.adapters.AllowanceChargeReasonCodeAdapter;
import io.github.up2jakarta.cii.edi.adapters.DocumentCodeAdapter;
import io.github.up2jakarta.cii.format.standard.CrossIndustryInvoiceType;
import io.github.up2jakarta.cii.ppf.adapters.ProfileAdapter;
import io.github.up2jakarta.cii.ppf.adapters.SpecialServiceDescriptionCodeAdapter;
import io.github.up2jakarta.test.api.TestUtil;
import io.github.up2jakarta.xml.SchemaValidator;
import io.github.up2jakarta.xml.api.XConfigurationException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

import static java.nio.file.Files.createDirectory;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@ComponentScan(basePackageClasses = {ProfileAdapter.class, DocumentCodeAdapter.class, LocalDateAdapter.class})
@ComponentScan(basePackageClasses = {TestUtil.class})
public class TUConfiguration {

    static {
        // Setting Local
        Locale.setDefault(Locale.ENGLISH);
        // Hacking System error
        System.setErr(System.out);
    }

    public static File xmlOutput() {
        try {
            final File out = Path.of(".", "target", "workspace").toFile();
            if (!out.exists()) {
                createDirectory(out.toPath());
            }
            return out;
        } catch (IOException e) {
            throw new XConfigurationException("Cannot create XML output directory", e);
        }
    }

    @Bean
    public AllowanceChargeIdentificationCodeAdapter allowanceChargeIdentificationCodeAdapter() {
        return AllowanceChargeIdentificationCodeAdapter.ECE_5189;
    }

    @Bean
    public AllowanceChargeReasonCodeAdapter allowanceChargeReasonCodeAdapter() {
        return AllowanceChargeReasonCodeAdapter.ECE_4465;
    }

    @Bean
    public SpecialServiceDescriptionCodeAdapter specialServiceDescriptionCodeAdapter() {
        return SpecialServiceDescriptionCodeAdapter.ECE_7161;
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

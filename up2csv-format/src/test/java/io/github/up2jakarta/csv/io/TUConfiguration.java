package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataResolver;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.prc.TokenProcessor;
import io.github.up2jakarta.csv.slv.DecimalResolver;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Up2Factory.class, TokenProcessor.class, DecimalResolver.class, GroupType.class})
public class TUConfiguration {

    @Bean
    public CSVFormat format() {
        return CSVFormat.RFC4180.builder()
                .setQuoteMode(QuoteMode.MINIMAL)
                .setQuote('"')
                .setDelimiter(';')
                .setNullString("")
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .setIgnoreSurroundingSpaces(true)
                .setCommentMarker('-')
                .get();
    }

    @Bean
    public BeanContext beanContext(final ApplicationContext context) {
        return context::getBean;
    }

    @Bean
    DataResolver<GroupType> resolver() {
        return DataResolver.empty();
    }

}

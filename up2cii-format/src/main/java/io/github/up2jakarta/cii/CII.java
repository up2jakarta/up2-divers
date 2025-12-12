package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.core.PrefixMapper;
import io.github.up2jakarta.xml.XContext;
import io.github.up2jakarta.xml.api.XConfigurationException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Cross Industry Invoice UtilityClass.
 */
public final class CII {

    // Simple Formatters
    public static final DateTimeFormatter FORMATTER_YEAR = DateTimeFormatter.ofPattern("yyyy");
    public static final DateTimeFormatter FORMATTER_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter FORMATTER_LOCAL_TIME = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter FORMATTER_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    // CII URI(s) for CII-D16B
    public static final String XML_SCHEMA_RSM_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100";
    public static final String XML_SCHEMA_UDT_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100";
    public static final String XML_SCHEMA_QDT_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:QualifiedDataType:100";
    public static final String XML_SCHEMA_RAM_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100";
    // CII XML Configuration
    public static final QName CII_QNAME = new QName(XML_SCHEMA_RSM_NAMESPACE_URL, "CrossIndustryInvoice");
    // Offset configuration
    private static final String OFFSET_PATTERN = "+HHMM";
    private static final String DEFAULT_OFFSET = "+0000";
    //  Complex Formatters
    public static final DateTimeFormatter FORMATTER_OFFSET_DATE_TIME = new DateTimeFormatterBuilder()
            .append(FORMATTER_LOCAL_DATE_TIME)
            .appendOffset(OFFSET_PATTERN, DEFAULT_OFFSET)
            .toFormatter();
    public static final DateTimeFormatter FORMATTER_OFFSET_TIME = new DateTimeFormatterBuilder()
            .append(FORMATTER_LOCAL_TIME)
            .appendOffset(OFFSET_PATTERN, DEFAULT_OFFSET)
            .toFormatter();
    public static final DateTimeFormatter FORMATTER_OFFSET_DATE = new DateTimeFormatterBuilder()
            .append(FORMATTER_LOCAL_DATE)
            .appendOffset(OFFSET_PATTERN, DEFAULT_OFFSET)
            .toFormatter();
    // CII SINGLETONS
    private static final Schema CII_SCHEMA;
    private static final SchemaFactory CII_FACTORY;
    private static final DocumentBuilderFactory CII_BUILDER;

    static {
        final URL xsd = getResource("CII_D16B/uncefact/data/standard/CrossIndustryInvoice_100pD16B.xsd");
        CII_FACTORY = XContext.newSchemaFactory();
        CII_SCHEMA = XContext.newSchema(CII_FACTORY, xsd);
        CII_BUILDER = XContext.newDocumentFactory(getSchema(), false, true);
    }

    private CII() {
    }

    public static URL getResource(String path) {
        return Thread.currentThread().getContextClassLoader().getResource(path);
    }

    public static SchemaFactory getFactory() {
        return CII_FACTORY;
    }

    public static Schema getSchema() {
        return CII_SCHEMA;
    }

    public static DocumentBuilderFactory getBuilder() {
        return CII_BUILDER;
    }

    public static void config(Marshaller marshaller) {
        try {
            marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", PrefixMapper.getInstance());
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new XConfigurationException("Cannot customize XML marshaller", e);
        }
    }

}

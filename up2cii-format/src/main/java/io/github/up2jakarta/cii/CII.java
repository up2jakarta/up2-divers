package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.core.PrefixMapper;
import io.github.up2jakarta.xml.XContext;
import io.github.up2jakarta.xml.api.XConfigurationException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
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
    public static final CollapsedStringAdapter TOKEN_ADAPTER = new CollapsedStringAdapter();
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
    private static volatile Schema CII_SCHEMA = null;
    private static volatile SchemaFactory CII_FACTORY = null;
    private static volatile DocumentBuilderFactory CII_BUILDER = null;

    private CII() {
    }

    public static ClassLoader getLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static SchemaFactory getFactory() {
        if (CII_FACTORY == null) {
            synchronized (CII.class) {
                if (CII_FACTORY == null) {
                    CII_FACTORY = XContext.newSchemaFactory();
                }
            }
        }
        return CII_FACTORY;
    }

    public static Schema getSchema() {
        if (CII_SCHEMA == null) {
            synchronized (CII.class) {
                if (CII_SCHEMA == null) {
                    final String path = "CII_D16B/uncefact/data/standard/CrossIndustryInvoice_100pD16B.xsd";
                    CII_SCHEMA = XContext.newSchema(getFactory(), getLoader().getResource(path));
                }
            }
        }
        return CII_SCHEMA;
    }

    public static DocumentBuilderFactory getBuilder() {
        if (CII_BUILDER == null) {
            synchronized (CII.class) {
                if (CII_BUILDER == null) {
                    CII_BUILDER = XContext.newDocumentFactory(getSchema(), false, true);
                }
            }
        }
        return CII_BUILDER;
    }

    public static void config(Marshaller marshaller) {
        try {
            marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", PrefixMapper.getInstance());
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (PropertyException e) {
            throw new XConfigurationException("Cannot customize XML marshaller", e);
        }
    }

}

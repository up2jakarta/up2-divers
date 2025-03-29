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
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Cross Industry Invoice UtilityClass.
 */
public abstract class CII {

    // CII SINGLETONS
    private static volatile Schema CII_SCHEMA = null;
    private static volatile DocumentBuilderFactory CII_BUILDER = null;

    // Offset configuration
    private static final String OFFSET_PATTERN = "+HHMM";
    private static final String DEFAULT_OFFSET = "+0000";

    // Simple Formatters
    public static final DateTimeFormatter FORMATTER_YEAR = DateTimeFormatter.ofPattern("yyyy");
    public static final DateTimeFormatter FORMATTER_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter FORMATTER_LOCAL_TIME = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter FORMATTER_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

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

    // CII URI(s) for CII-D16B
    public static final String XML_SCHEMA_RSM_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100";
    public static final String XML_SCHEMA_UDT_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100";
    public static final String XML_SCHEMA_QDT_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:QualifiedDataType:100";
    public static final String XML_SCHEMA_RAM_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100";

    // CII XML Configuration
    public static final QName CII_QNAME = new QName(XML_SCHEMA_RSM_NAMESPACE_URL, "CrossIndustryInvoice");
    public static final CollapsedStringAdapter TOKEN_ADAPTER = new CollapsedStringAdapter();

    private CII() {
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static String getPath() {
        return "CII_D16B";
    }

    public static Schema getSchema() {
        if (CII_SCHEMA == null) {
            synchronized (CII.class) {
                if (CII_SCHEMA == null) {
                    final String path = getPath() + "/uncefact/data/standard/CrossIndustryInvoice_100pD16B.xsd";
                    final URL XSD_URL = getClassLoader().getResource(path);
                    CII_SCHEMA = XContext.getSchema(XSD_URL);
                }
            }
        }
        return CII_SCHEMA;
    }

    public static DocumentBuilderFactory getBuilder() {
        if (CII_BUILDER == null) {
            synchronized (CII.class) {
                if (CII_BUILDER == null) {
                    CII_BUILDER = XContext.getDocumentBuilderFactory(getSchema(), false);
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

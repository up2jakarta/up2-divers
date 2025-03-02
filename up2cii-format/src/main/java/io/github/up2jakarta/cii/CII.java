package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.xml.InvoicePrefixMapper;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

import javax.xml.namespace.QName;
import java.net.URL;

/**
 * CII Utility.
 */
public interface CII {

    NamespacePrefixMapper NS_PREFIX_MAPPER = new InvoicePrefixMapper();
    CollapsedStringAdapter TOKEN_ADAPTER = new CollapsedStringAdapter();
    ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    // XML Schemas path
    String XSD_ROOT = "CII_D16B";
    String XSD_PATH = XSD_ROOT + "/uncefact/data/standard/CrossIndustryInvoice_100pD16B.xsd";
    URL XSD_URL = CLASS_LOADER.getResource(CII.XSD_PATH);

    // URI(s) for CII-D16B
    String XML_SCHEMA_RSM_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100";
    String XML_SCHEMA_UDT_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100";
    String XML_SCHEMA_QDT_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:QualifiedDataType:100";
    String XML_SCHEMA_RAM_NAMESPACE_URL = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100";

    // CII XML Configuration
    String CII_TAG_NAME = "CrossIndustryInvoice";
    String CII_ALLOWED_PROTOCOL = "file,nested";
    QName CII_QNAME = new QName(XML_SCHEMA_RSM_NAMESPACE_URL, CII_TAG_NAME);

    /**
     * Generate and return an enum constant from the given {@code id}.
     *
     * @param id the given id
     * @return Java valid constant name
     */
    static String codeConstant(String id) {
        id = id.toUpperCase();
        if (Character.isDigit(id.charAt(0))) {
            id = "V_" + id;
        }
        return id.replace("-", "_");
    }

}

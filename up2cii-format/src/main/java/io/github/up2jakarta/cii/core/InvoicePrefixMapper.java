package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.CII;
import io.github.up2jakarta.xml.api.XPrefixMapper;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

import javax.xml.XMLConstants;
import java.util.HashMap;
import java.util.Map;

public class InvoicePrefixMapper extends NamespacePrefixMapper implements XPrefixMapper {

    private final static String PROPERTY_NAME = "org.glassfish.jaxb.namespacePrefixMapper";

    private final Map<String, String> namespaceMap = new HashMap<>();

    /**
     * Create mappings.
     */
    public InvoicePrefixMapper() {
        namespaceMap.put(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "xsi");
        namespaceMap.put(XMLConstants.W3C_XML_SCHEMA_NS_URI, "xs");
        namespaceMap.put(CII.XML_SCHEMA_RSM_NAMESPACE_URL, "rsm");
        namespaceMap.put(CII.XML_SCHEMA_UDT_NAMESPACE_URL, "udt");
        namespaceMap.put(CII.XML_SCHEMA_QDT_NAMESPACE_URL, "qdt");
        namespaceMap.put(CII.XML_SCHEMA_RAM_NAMESPACE_URL, "ram");
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return namespaceMap.getOrDefault(namespaceUri, suggestion);
    }


    @Override
    public String getProperty() {
        return PROPERTY_NAME;
    }
}

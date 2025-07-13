package io.github.up2jakarta.cii.core;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

import java.util.HashMap;
import java.util.Map;

import static io.github.up2jakarta.cii.CII.*;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class PrefixMapper extends NamespacePrefixMapper {

    private static final PrefixMapper INSTANCE = new PrefixMapper();
    private final Map<String, String> namespaceMap = new HashMap<>();

    /**
     * Create mappings.
     */
    private PrefixMapper() {
        namespaceMap.put(W3C_XML_SCHEMA_INSTANCE_NS_URI, "xsi");
        namespaceMap.put(W3C_XML_SCHEMA_NS_URI, "xs");
        namespaceMap.put(XML_SCHEMA_RSM_NAMESPACE_URL, "rsm");
        namespaceMap.put(XML_SCHEMA_UDT_NAMESPACE_URL, "udt");
        namespaceMap.put(XML_SCHEMA_QDT_NAMESPACE_URL, "qdt");
        namespaceMap.put(XML_SCHEMA_RAM_NAMESPACE_URL, "ram");
    }

    public static NamespacePrefixMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return namespaceMap.getOrDefault(namespaceUri, suggestion);
    }

}

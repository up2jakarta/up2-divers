package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.lov.core.WKCache;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.xml.XMLConstants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "token", propOrder = {"value"}, namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
public final class TokenType implements ChargeReasonCodeType<TokenType> {

    private static final WKCache<String, TokenType> CACHE = new WKCache<>();

    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    private final String value;

    private TokenType(String value) {
        this.value = value;
    }

    public static TokenType from(String value) {
        return CACHE.get(value, TokenType::new);
    }

    @Override
    public String getCode() {
        return value;
    }

    @Override
    public String getName() {
        return value;
    }

}

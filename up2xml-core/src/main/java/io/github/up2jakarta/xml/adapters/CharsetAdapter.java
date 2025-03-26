package io.github.up2jakarta.xml.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.nio.charset.Charset;

/**
 * {@link XmlAdapter} mapping {@link Charset} to xsd:token.
 *
 * @see Charset
 */
public class CharsetAdapter extends XmlAdapter<String, Charset> {

    @Override
    public Charset unmarshal(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Charset.forName(value);
    }

    @Override
    public String marshal(Charset value) {
        if (value == null) {
            return null;
        }
        return value.name();
    }

}

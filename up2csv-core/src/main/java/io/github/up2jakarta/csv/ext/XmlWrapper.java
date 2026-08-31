package io.github.up2jakarta.csv.ext;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.SafeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Simple {@link TypeAdapter} that wraps XML {@link XmlAdapter}.
 *
 * @param <T> the property type
 */
public final class XmlWrapper<T> extends SafeAdapter<T> {

    private final XmlAdapter<String, T> delegate;

    public XmlWrapper(Class<T> type, SeverityType level, String code, XmlAdapter<String, T> delegate) {
        super(type, level, code);
        this.delegate = delegate;
    }

    @Override
    protected T doParse(String value) throws Exception {
        return delegate.unmarshal(value);
    }

    @Override
    protected String doFormat(T value) throws Exception {
        return delegate.marshal(value);
    }

}

package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.PropertyException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;

import static io.github.up2jakarta.csv.misc.Beans.getBean;
import static io.github.up2jakarta.csv.misc.Beans.getTypeArguments;

/**
 * {@link XmlType} extension that supports {@link XmlJavaTypeAdapter}.
 *
 * @see XmlAdapter
 */
@Named
@Singleton
public final class XmlAdapterExtension extends ConversionExtension<XmlType, XmlJavaTypeAdapter> {

    private final BeanContext context;

    @Inject
    XmlAdapterExtension(BeanContext context) {
        super(XmlType.class);
        this.context = context;
    }

    private static XmlJavaTypeAdapter getAdapter(Field field, Class<?> type) {
        final XmlJavaTypeAdapter xml = field.getAnnotation(XmlJavaTypeAdapter.class);
        if (xml == null) {
            return type.getAnnotation(XmlJavaTypeAdapter.class);
        }
        return xml;
    }

    @Override
    public Optional<XmlJavaTypeAdapter> get(Class<? extends Segment> segmentType, Field property, Class<?> type, Field... path) throws BeanException {
        final XmlJavaTypeAdapter xml = getAdapter(property, type);
        if (xml != null) {
            if (!segmentType.isAnnotationPresent(XmlType.class)) {
                throw new BeanException(segmentType, "must be annotated with @XmlType");
            }
            //noinspection unchecked
            final Class<? extends XmlAdapter<String, ?>> adapterType = (Class<? extends XmlAdapter<String, ?>>) xml.value();
            final Type[] arguments = getTypeArguments(adapterType, XmlAdapter.class);
            if (!String.class.equals(arguments[0]) || !type.equals(arguments[1])) {
                final String typeName = type.getSimpleName();
                throw new BeanException(property, "adapter must extends XmlAdapter<String, " + typeName + ">");
            }
            return Optional.of(xml);
        }
        return Optional.empty();
    }

    @Override
    public Conversion<?> resolve(Field property, Class<?> type, XmlJavaTypeAdapter config) throws BeanException {
        //noinspection unchecked
        final Class<? extends XmlAdapter<String, Object>> adapterType = (Class<? extends XmlAdapter<String, Object>>) config.value();
        final Optional<Error> error = ConversionResolver.getError(property);
        final XmlAdapter<String, Object> adapter = getBean(context, adapterType);
        final PropertyFormatter<Object> f = v -> {
            try {
                return adapter.marshal(v);
            } catch (Exception ex) {
                final SeverityType severityType = error.map(Error::severity).orElse(SeverityType.ERROR);
                final String code = error.map(Error::value).orElse(Errors.ERROR_XML_ENUM);
                throw PropertyException.of(severityType, code, ex);
            }
        };
        return new Conversion<>(adapter::unmarshal, f, error);
    }

}

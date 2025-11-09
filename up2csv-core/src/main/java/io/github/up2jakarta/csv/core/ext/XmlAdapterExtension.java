package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.ConversionExtension;
import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyFormatter;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_XML_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.getTypeArguments;

/**
 * {@link XmlType} extension that supports {@link XmlJavaTypeAdapter}.
 *
 * @see XmlAdapter
 */
@Named
@Singleton
public final class XmlAdapterExtension extends ConversionExtension<XmlType, XmlJavaTypeAdapter> {

    @Inject
    XmlAdapterExtension() {
        super(XmlType.class);
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
                final String cn = type.getSimpleName();
                throw new BeanException(property, "@XmlJavaTypeAdapter[value] should extends XmlAdapter<String, " + cn + ">");
            }
            return Optional.of(xml);
        }
        return Optional.empty();
    }

    @Override
    public <V> Conversion<V> resolve(Field property, Class<V> type, XmlJavaTypeAdapter config) throws BeanException {
        //noinspection unchecked
        final Class<XmlAdapter<String, V>> adapterType = (Class<XmlAdapter<String, V>>) config.value();
        final Optional<Error> error = ConversionResolver.getError(property, type);
        final XmlAdapter<String, V> adapter = this.getBean(adapterType);
        final PropertyFormatter<V> f = v -> {
            try {
                return adapter.marshal(v);
            } catch (Exception ex) {
                final SeverityType severityType = error.map(Error::severity).orElse(SeverityType.ERROR);
                final String code = error.map(Error::value).orElse(ERROR_XML_ENUM);
                throw PropertyException.of(severityType, code, ex);
            }
        };
        return new Conversion<>(adapter::unmarshal, f, error);
    }

}

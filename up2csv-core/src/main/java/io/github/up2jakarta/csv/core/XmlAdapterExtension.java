package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionExtension;
import io.github.up2jakarta.csv.extension.Segment;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.Beans.getBean;
import static io.github.up2jakarta.csv.core.Beans.getTypeArguments;

/**
 * {@link XmlType} extension that supports {@link XmlJavaTypeAdapter}.
 *
 * @see XmlAdapter
 */
@Named
@Singleton
@SuppressWarnings("unchecked")
public final class XmlAdapterExtension extends ConversionExtension<XmlType, XmlJavaTypeAdapter> {

    private final BeanContext context;

    @Inject
    XmlAdapterExtension(BeanContext context) {
        super(XmlType.class);
        this.context = context;
    }

    private static XmlJavaTypeAdapter getAdapter(Field field) {
        final XmlJavaTypeAdapter xml = field.getAnnotation(XmlJavaTypeAdapter.class);
        if (xml == null) {
            return field.getType().getAnnotation(XmlJavaTypeAdapter.class);
        }
        return xml;
    }

    @Override
    public Optional<XmlJavaTypeAdapter> get(Class<? extends Segment> segmentType, Field property) throws BeanException {
        final Class<?> fieldType = property.getType();
        final XmlJavaTypeAdapter xml = getAdapter(property);
        if (xml != null) {
            if (segmentType.getAnnotation(XmlType.class) == null) {
                throw new BeanException(segmentType, "must be annotated with @XmlType");
            }
            final Class<? extends XmlAdapter<String, ?>> adapterType = (Class<? extends XmlAdapter<String, ?>>) xml.value();
            final Type[] arguments = getTypeArguments(adapterType, XmlAdapter.class);
            if (!String.class.equals(arguments[0]) || !fieldType.equals(arguments[1])) {
                final String typeName = fieldType.getSimpleName();
                throw new BeanException(property, "adapter must extends XmlAdapter<String, " + typeName + ">");
            }
            return Optional.of(xml);
        }
        return Optional.empty();
    }

    @Override
    public Conversion<?> resolve(Field property, XmlJavaTypeAdapter config) throws BeanException {
        final Class<? extends XmlAdapter<String, ?>> adapterType = (Class<? extends XmlAdapter<String, ?>>) config.value();
        final Optional<Error> error = CodeListResolver.getError(property);
        final XmlAdapter<String, ?> adapter = getBean(context, adapterType);
        return Conversion.of(adapter::unmarshal, error.orElse(null));
    }

}

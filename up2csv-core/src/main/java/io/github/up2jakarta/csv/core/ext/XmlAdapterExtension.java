package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_JPA_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * {@link XmlType} extension that supports {@link XmlJavaTypeAdapter}.
 *
 * @see XmlAdapter
 */
@Named
@Singleton
@SuppressWarnings("unchecked")
public final class XmlAdapterExtension extends TypeExtension<XmlType, XmlJavaTypeAdapter> {

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
    public Optional<XmlJavaTypeAdapter> get(Class<? extends Segment> st, Field p, Class<?> type, Field... ps) throws BeanException {
        final XmlJavaTypeAdapter xml = getAdapter(p, type);
        if (xml != null) {
            if (!st.isAnnotationPresent(XmlType.class)) {
                throw new BeanException(st, "must be annotated with @XmlType");
            }
            final Class<? extends XmlAdapter<String, ?>> adapterType = (Class<? extends XmlAdapter<String, ?>>) xml.value();
            final Type[] arguments = getTypeArguments(adapterType, XmlAdapter.class);
            if (!String.class.equals(arguments[0]) || !type.equals(arguments[1])) {
                final CharSequence cn = getTypeName(type);
                throw new BeanException(p, "@XmlJavaTypeAdapter[value] should extends XmlAdapter<String, " + cn + ">");
            }
            return Optional.of(xml);
        }
        return Optional.empty();
    }

    @Override
    public <V> TypeAdapter<V> resolve(Field property, Class<V> type, XmlJavaTypeAdapter config) throws BeanException {
        final Class<XmlAdapter<String, V>> adapterType = (Class<XmlAdapter<String, V>>) config.value();
        final XmlAdapter<String, V> adapter = this.getBean(adapterType, "");
        if (adapter instanceof TypeAdapter<?> pa) {
            return (TypeAdapter<V>) pa;
        }
        final Optional<Error> error = error(property, type);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_JPA_ENUM);
        return new XmlWrapper<>(type, level, code, adapter);
    }

}

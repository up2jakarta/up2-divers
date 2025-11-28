package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.BeanContext;
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
public final class XmlAdapterExtension implements TypeExtension<Object, XmlJavaTypeAdapter> {

    private final BeanContext context;

    @Inject
    XmlAdapterExtension(BeanContext context) {
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
    public Optional<XmlJavaTypeAdapter> resolve(Class<? extends Segment> st, Field p, Class<?> pt, Field... ps) throws BeanException {
        final XmlJavaTypeAdapter xml = getAdapter(p, pt);
        if (xml != null) {
            final Type[] types = getTypeArguments(xml.value(), XmlAdapter.class);
            if (!String.class.equals(types[0]) || !(types[1] instanceof Class<?> c) || !c.isAssignableFrom(pt)) {
                final CharSequence cn = getTypeName(pt);
                throw new BeanException(p, "@XmlJavaTypeAdapter[value] should extends XmlAdapter<String, " + cn + ">");
            }
            return Optional.of(xml);
        }
        return Optional.empty();
    }

    @Override
    public TypeAdapter<?> resolve(Field pf, Class<Object> pt, XmlJavaTypeAdapter pc) throws BeanException {
        final XmlAdapter<String, Object> adapter = getBean(context, pc.value(), "");
        if (adapter instanceof TypeAdapter<?> pa) {
            return pa;
        }
        final Optional<Error> error = error(pf, pt);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_JPA_ENUM);
        return new XmlWrapper<>(pt, level, code, adapter);
    }

}

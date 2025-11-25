package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.core.BeanAccess.WO;
import static io.github.up2jakarta.csv.core.ext.Beans.isInnerType;
import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isStatic;

/**
 * Internal technical checker.
 */
final class BeanChecker implements TypeListener, TypeContext {

    static final BeanChecker INSTANCE = new BeanChecker();

    private BeanChecker() {
    }

    private static void check(Field field) throws BeanException {
        if (isStatic(field.getModifiers())) {
            throw new BeanException(field, "must not be static");
        }
    }

    private static void check(BeanAccess mode, Class<? extends Segment> type) throws BeanException {
        if (mode == WO && type.isLocalClass()) {
            throw new BeanException(type, "local class is not allowed");
        }
        if (type.isInterface()) {
            throw new BeanException(type, "interface is not allowed");
        }
        if (isAbstract(type.getModifiers())) {
            throw new BeanException(type, "abstract class is not allowed");
        }
    }

    @Override
    public TypeContext beforeSegment(BeanAccess mode, Class<? extends Segment> type) throws BeanException {
        check(mode, type);
        if (mode == WO && isInnerType(type)) {
            throw new BeanException(type, "inner class is not allowed");
        }
        return this;
    }

    @Override
    public void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
        check(property);
    }

    @Override
    public void beforeFragmentProperty(BeanAccess mode, Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
        check(mode, fragmentType);
        check(fragment);
    }

}

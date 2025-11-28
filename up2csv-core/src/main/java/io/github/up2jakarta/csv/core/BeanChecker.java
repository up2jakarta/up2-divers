package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.core.ext.Beans.isInnerType;
import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isStatic;

/**
 * Internal technical checker.
 */
final class BeanChecker implements TypeListener, TypeContext {

    private static final BeanChecker RA = new BeanChecker(Mode.RO);
    private static final BeanChecker WA = new BeanChecker(Mode.WO);

    private final Mode mode;

    private BeanChecker(Mode mode) {
        this.mode = mode;
    }

    static BeanChecker of(Mode mode) {
        if (mode == Mode.RO) {
            return RA;
        }
        return WA;
    }

    private static void check(Field field) throws BeanException {
        if (isStatic(field.getModifiers())) {
            throw new BeanException(field, "must not be static");
        }
    }

    private static void check(Mode mode, Class<? extends Segment> type) throws BeanException {
        if (mode == Mode.WO && type.isLocalClass()) {
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
    public TypeContext beforeSegment(Class<? extends Segment> type) throws BeanException {
        check(mode, type);
        if (mode == Mode.WO && isInnerType(type)) {
            throw new BeanException(type, "inner class is not allowed");
        }
        return this;
    }

    @Override
    public void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
        check(property);
    }

    @Override
    public void beforeFragmentProperty(Field fragment, Class<? extends Segment> type, int offset) throws BeanException {
        check(mode, type);
        check(fragment);
    }

}

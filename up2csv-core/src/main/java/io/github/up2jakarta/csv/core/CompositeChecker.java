package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.ext.CheckerContext;
import io.github.up2jakarta.csv.api.ext.SegmentListener;
import io.github.up2jakarta.csv.cfg.Checker;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.core.BeanSupport.getAnnotationsByType;
import static io.github.up2jakarta.csv.core.Beans.getBean;

final class CompositeChecker implements CheckerContext {

    private final SegmentListener[] listeners;
    private final CheckerContext[] contexts;

    private CompositeChecker(SegmentListener[] listeners, CheckerContext[] contexts) {
        this.listeners = listeners;
        this.contexts = contexts;
    }

    static CompositeChecker of(Class<? extends Segment> type, BeanContext context) throws BeanException {
        if (type.getTypeParameters().length != 0) {
            throw new BeanException(type, "generic class is not allowed");
        }
        final Checker[] checkers = getAnnotationsByType(Checker.class, type).toArray(Checker[]::new);
        final List<SegmentListener> result = new LinkedList<>();
        result.add(TechnicalChecker.INSTANCE);
        for (final Checker checker : checkers) {
            final SegmentListener bean = getBean(context, checker.value());
            if (bean.isActivated(type)) {
                result.add(bean);
            }
        }
        final SegmentListener[] listeners = result.toArray(SegmentListener[]::new);
        final CheckerContext[] contexts = new CheckerContext[listeners.length];
        for (var i = 0; i < listeners.length; i++) {
            contexts[i] = listeners[i].beforeSegment(type);
        }
        return new CompositeChecker(listeners, contexts);
    }

    void afterSegment() throws BeanException {
        for (var i = 0; i < this.listeners.length; i++) {
            this.listeners[i].afterSegment(contexts[i]);
        }
    }


    @Override
    public void beforeSuperSegment(Class<? extends Segment> superType) throws BeanException {
        for (final CheckerContext context : contexts) {
            context.beforeSuperSegment(superType);
        }
    }

    @Override
    public void afterSuperSegment(Class<? extends Segment> superType) throws BeanException {
        for (final CheckerContext context : contexts) {
            context.afterSuperSegment(superType);
        }
    }

    @Override
    public void beforePositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
        for (final CheckerContext context : contexts) {
            context.beforePositionProperty(property, propertyType, offset);
        }
    }

    @Override
    public void afterPositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
        for (final CheckerContext context : contexts) {
            context.afterPositionProperty(property, propertyType, offset);
        }
    }

    @Override
    public void beforeFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
        for (final CheckerContext context : contexts) {
            context.beforeFragmentProperty(fragment, fragmentType);
        }
    }

    @Override
    public void afterFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
        for (final CheckerContext context : contexts) {
            context.afterFragmentProperty(fragment, fragmentType);
        }
    }

    @Override
    public void unknownProperty(Field property, Class<?> propertyType) throws BeanException {
        for (final CheckerContext context : contexts) {
            context.unknownProperty(property, propertyType);
        }
    }
}

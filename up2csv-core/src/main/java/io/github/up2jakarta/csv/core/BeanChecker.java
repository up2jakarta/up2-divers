package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.BSOperator.Node;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.BSBuilder.name;
import static io.github.up2jakarta.csv.core.BeanAccessor.getInstance;
import static io.github.up2jakarta.csv.ext.Beans.isInnerType;
import static io.github.up2jakarta.csv.prc.DefaultProcessor.undefined;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static io.github.up2jakarta.lov.core.Defaults.creator;
import static io.github.up2jakarta.lov.core.Defaults.prototype;
import static java.lang.String.join;
import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.function.Predicate.not;

/**
 * Internal technical checker.
 */
final class BeanChecker implements TypeListener, TypeContext {

    private static final BeanChecker RO = new BeanChecker(Mode.RO);
    private static final BeanChecker WO = new BeanChecker(Mode.WO);

    private final Mode mode;

    private BeanChecker(Mode mode) {
        this.mode = mode;
    }

    static BeanChecker of(Mode mode) {
        if (mode == Mode.RO) {
            return RO;
        }
        return WO;
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

    static void check(Class<? extends Segment> type) throws BeanException {
        if (type.getTypeParameters().length != 0) {
            throw new BeanException(type, "generic class is not allowed");
        }
    }

    static void check(Field field, Position csv) throws BeanException {
        if (csv.value() < 0) {
            throw new BeanException(field, "@Position[value] must be positive");
        }
        if (csv.required() && !undefined(csv)) {
            throw new BeanException(field, "@Position[defaultValue] should be empty because required");
        }
        if (field.isAnnotationPresent(Valid.class)) {
            throw new BeanException(field, "must not be annotated with @Valid");
        }
        if (field.getAnnotationsByType(ValidOverride.class).length != 0) {
            throw new BeanException(field, "must not be annotated with @ValidOverride");
        }
        if (field.getAnnotationsByType(PositionOverride.class).length != 0) {
            throw new BeanException(field, "must not be annotated with @PositionOverride");
        }
        if (field.getAnnotationsByType(FragmentOverride.class).length != 0) {
            throw new BeanException(field, "must not be annotated with @FragmentOverride");
        }
    }

    static void check(Field field, Class<?> type, Fragment csv) throws BeanException {
        if (csv.value() < 0) {
            throw new BeanException(field, "@Fragment[value] must be positive");
        }
        if (!Segment.class.isAssignableFrom(type)) {
            throw new BeanException(field, "type must implements Segment");
        }
    }

    static void check(Node<?, ?, ?> pn, Member source, ReferenceId pa) throws BeanException {
        if (pn == null) {
            throw new BeanException(source, "should not be annotated with " + name(pa) + " because not found");
        }
        if (!pn.identifiable) {
            throw new BeanException(source, "should not be annotated with " + name(pa) + " because @BusinessId is undefined");
        }
    }

    static <L extends ILinker<Segment, ?>> L check(L ln, Class<? extends Segment> pc, Member fp, String cv) throws BeanException {
        final Constructor<? extends Segment> c = creator(pc);
        final Object[] p = prototype(c);
        final Segment s = getInstance().toCreator(c).newInstance(p);
        try {
            ln.from(s);
        } catch (NullPointerException npe) {
            throw new BeanException(fp, "must be initialized with " + cv);
        }
        return ln;
    }

    static <A extends Annotation> void check(PPosition<?, ?> p, Class<A> a) throws BeanException {
        if (p.defaultValue != null) {
            throw new BeanException(p.getSource(), "@Position[defaultValue] must be empty because @" + getTypeName(a));
        }
        if (p.getType() == String.class) {
            if (p.isAnnotationAbsent(NotEmpty.class, NotBlank.class, NotNull.class)) {
                throw new BeanException(p.getSource(), "should be annotated with @NotBlank because @" + getTypeName(a));
            }
        } else if (p.isAnnotationAbsent(NotNull.class)) {
            throw new BeanException(p.getSource(), "must be annotated with @NotNull because @" + getTypeName(a));
        }
    }

    static void check(List<Bean<?, ?, ?>> stack, Bean<?, ?, ?> node) throws BeanException {
        final Class<?> t = node.type;
        if (isInnerType(node.type)) {
            final Class<?> et = node.type.getEnclosingClass();
            final Optional<Bean<?, ?, ?>> parent = stack.stream().filter(n -> n.type.equals(et)).findAny();
            if (parent.isEmpty()) {
                var cn = stack.stream().filter(not(Bean.BC.class::isInstance)).map(n -> getTypeName(n.type)).toList();
                throw new BeanException(t, "inner class is not allowed outside enclosing segments: " + join(", ", cn));
            }
            if (parent.get() instanceof Bean.BC<?, ?> n) {
                throw new BeanException(t, "inner class is not allowed inside enclosing segment: " + getTypeName(n.type));
            }
        }
        stack.addLast(node);
        for (final BSProperty<?, ?> p : node.properties) {
            if (p instanceof PFragment<?, ?> fp) {
                check(stack, (Bean<?, ?, ?>) fp.node);
            }
        }
    }

    static void check(IMode mode, BusinessExporter.Format<?, ?> computer) throws BeanException {
        final boolean readable = mode != ModeType.NEAT;
        if (readable && !computer.identifiable) {
            throw new BeanException(computer.node.type, "must have one property annotated with @BusinessId");
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

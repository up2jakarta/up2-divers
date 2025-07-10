package io.github.up2jakarta.job.ctx;

import org.slf4j.Logger;
import org.springframework.batch.item.ExecutionContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

public class ContextProvider<P extends Enum<P> & ContextParameter<P>, A extends Annotation> {

    protected static final String PREFIX = "flow";
    protected static final char SEPARATOR = '.';

    private final Class<P> parameterType;
    private final Class<A> annotationType;
    private final Function<A, P> mapper;
    private final P inputName;
    private final P stepOrder;
    private final P flowOrder;

    public ContextProvider(Class<P> pType, Class<A> aType, Function<A, P> mapper, P iName, P sOrder, P fOrder) {
        if (!aType.isAnnotationPresent(ContextProxy.class)) {
            throw new IllegalArgumentException(aType.getName() + " must be annotated by @ContextProxy");
        }
        requireNonNull(iName, "inputName");
        requireNonNull(iName, "stepOrder");
        requireNonNull(iName, "flowOrder");
        this.mapper = mapper;
        this.inputName = iName;
        this.flowOrder = fOrder;
        this.stepOrder = sOrder;
        this.parameterType = pType;
        this.annotationType = aType;
    }

    public static boolean isParameter(String key) {
        return key.startsWith(PREFIX + SEPARATOR);
    }

    public static String stepKey(ContextParameter<?> p) {
        return PREFIX + SEPARATOR + p.getCode();
    }

    static void debug(Logger logger, String source, String reference, String key, Object value, boolean output) {
        if (value != null && isParameter(key)) {
            final String direction = output ? "=>" : "<=";
            final String type = value.getClass().getSimpleName();
            logger.debug("{}[{}] : {} {} {}[{}]", source, reference, key, direction, type, value);
        }
    }

    static String flowKey(ContextParameter<?> p, int order) {
        return PREFIX + SEPARATOR + order + SEPARATOR + p.getCode();
    }

    static boolean canRemove(ContextParameter<?> p, int order) {
        return p.getToStepId() < order;
    }

    static boolean canLog(ContextParameter<?> p, int order) {
        return p.getFromStepId() == order && p.getToStepId() != order;
    }

    static boolean isInputFor(ContextParameter<?> p, int stepId) {
        return (p.getFromStepId() < stepId && stepId < p.getToStepId()) || (p.getFromStepId() == stepId && !p.isOutput());
    }

    static boolean isOutputFor(ContextParameter<?> p, int stepId) {
        return p.getFromStepId() == stepId && p.isOutput() && p.isSave();
    }

    static void syncParameter(Logger logger, ContextAware source, ExecutionContext target, String key, Object value) {
        final Object existing = target.get(key);
        if (existing == null) {
            target.put(key, value);
        } else if (!existing.equals(value)) {
            logger.warn("{} : parameter #[{}] value miss-match", source, key);
        }
    }

    List<P> getInputs(int stepId) {
        return stream(this.getParameterType().getEnumConstants()).filter(p -> isInputFor(p, stepId)).toList();
    }

    List<P> getOutputs(int stepId) {
        return stream(this.getParameterType().getEnumConstants()).filter(p -> isOutputFor(p, stepId)).toList();
    }

    void copyInputs(Logger log, ContextAware src, ExecutionContext ctx, int stepId, Map<String, Object> data) {
        for (final ContextParameter<?> fp : this.getInputs(stepId)) {
            final String key = stepKey(fp);
            final Object value = data.getOrDefault(key, ctx.get(key));
            if (value == null) {
                throw new RuntimeException(src + " missed input parameter : " + key);
            }
            syncParameter(log, src, ctx, key, value);
        }
    }

    void checkOutputs(Logger logger, ExecutionContext source, ContextAware context, int stepId) {
        for (final ContextParameter<?> fp : this.getOutputs(stepId)) {
            final String key = stepKey(fp);
            final Object value = source.get(key);
            if (value == null) {
                logger.error("{} missed output parameter #[{}]", context, key);
                throw new RuntimeException(context + " missed output parameter : " + key);
            }
        }
    }

    Class<P> getParameterType() {
        return parameterType;
    }

    P getInputParameter() {
        return inputName;
    }

    P getStepOrderParameter() {
        return stepOrder;
    }

    P getFlowOrderParameter() {
        return flowOrder;
    }

    boolean isAnnotationPresent(AnnotatedElement element) {
        return element.isAnnotationPresent(annotationType);
    }

    P getParameter(AnnotatedElement element) {
        final A annotation = element.getAnnotation(annotationType);
        return mapper.apply(annotation);
    }

}

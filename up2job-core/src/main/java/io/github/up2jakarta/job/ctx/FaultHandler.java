package io.github.up2jakarta.job.ctx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.atomic.AtomicLong;

abstract class FaultHandler<T extends SupportAware> extends FlowHandler<T>
        implements InvocationHandler, FaultAware {

    protected final T context;
    protected final ContextProvider<?, ?> provider;
    private final AtomicLong counter = new AtomicLong(0);

    protected FaultHandler(T context, ContextProvider<?, ?> provider) {
        this.provider = provider;
        this.context = context;
    }

    protected abstract void setValue(ContextParameter<?> parameter, Object value);

    protected abstract Object getValue(ContextParameter<?> parameter, Class<?> type, boolean notNull);

    protected abstract Class<? extends ContextAware> getContract();

    @Override
    public final T getContext() {
        return context;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        final Class<?> mc = method.getDeclaringClass();
        if (mc == FaultAware.class) {
            return method.invoke(this, arguments);
        }
        final Parameter[] parameters = method.getParameters();
        if (mc == ContextAware.class || mc == getContract() || mc == Object.class) {
            return method.invoke(context, arguments);
        }
        // GETTER
        if (provider.isAnnotationPresent(method)) {
            final ContextParameter<?> parameter = provider.getParameter(method);
            return getValue(parameter, method.getReturnType(), method.isAnnotationPresent(CMLinked.class));
        }
        // SETTER
        for (var i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            if (provider.isAnnotationPresent(parameter)) {
                setValue(provider.getParameter(parameter), arguments[i]);
            }
        }
        return null;
    }

    @Override
    public final void addFailures(long count) {
        counter.addAndGet(count);
    }

    @Override
    public final long getFailures() {
        return counter.get();
    }

}

package io.github.up2jakarta.lov.core;

/**
 * Interface marker for managed beans with dependencies.
 */
public abstract class BeanAware {

    private BeanContext context;

    final void setContext(BeanContext context) {
        this.context = context;
    }

    protected final <T> T getBean(Class<?> type, String name) throws BeanException {
        return Beans.getBean(context, type, name);
    }

}

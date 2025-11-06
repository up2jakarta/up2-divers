package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.core.BeanException;

/**
 * Interface marker for beans with dependencies.
 */
public abstract class BeanAware {

    private BeanContext context;

    void setContext(BeanContext context) {
        this.context = context;
    }

    protected <T> T getBean(Class<?> beanType) throws BeanException {
        return Beans.getBean(context, beanType);
    }

}

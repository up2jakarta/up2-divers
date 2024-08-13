package io.github.up2jakarta.csv.extension;

/**
 * Contract interface for JavaBean container, useful for contained application that use JSR-365 (CDI) or whatever.
 *
 * @see ConfigurableTransformer
 */
@FunctionalInterface
public interface BeanContext {

    /**
     * Return the bean instance that uniquely matches the given bean type.
     *
     * @param beanType the bean type
     * @param <B>      the bean type
     * @return the bean instance
     */
    <B> B getBean(Class<B> beanType);

}

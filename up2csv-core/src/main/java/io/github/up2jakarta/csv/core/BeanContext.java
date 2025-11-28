package io.github.up2jakarta.csv.core;

/**
 * Contract interface for IoC container, useful for contained application that use JSR-365 (CDI) or whatever.
 */
@FunctionalInterface
public interface BeanContext {

    /**
     * Returns the bean instance that matches the specified bean class.
     *
     * @param type the bean type
     * @param <B>  the bean class
     * @return the bean instance
     */
    <B> B getBean(Class<B> type);

    /**
     * Returns the bean instance that matches the specified bean class and the qualified name.
     *
     * @param type the bean type
     * @param <B>  the bean class
     * @return the bean instance
     */
    default <B> B getBean(Class<B> type, String name) {
        return getBean(type);
    }

}

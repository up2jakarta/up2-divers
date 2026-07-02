package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.lov.core.BeanException;

/**
 * Contract interface for IoC container, useful for contained application that use JSR-365 (CDI) or whatever.
 */
@FunctionalInterface
public interface Container {

    /**
     * System property name that tell the engine to use trusted mode to access to private properties.
     * <p>
     * By default, the trusted mode is disabled and the <code>package</code> that contains segment classes
     * must be open to <code>up2jakarta.csv.core</code> module by one of the following options:
     * <ul>
     *     <li>JVM arguments: <code>--add-opens xx.yyy/xx.yyy.csv.core.segments=up2jakarta.csv.core</code></li>
     *     <li>module-info.java: <code>opens xx.yyy.csv.core.segments to up2jakarta.csv.core;</code></li>
     * </ul>
     *
     * <p>
     * <b>To enable the trusted mode, the JVM should be started with the following extra arguments:</b>
     * <ul>
     *     <li><code>-Dup2jakarta.csv.core.trusted.mode=true</code></li>
     *     <li><code>--add-opens java.base/java.lang.invoke=up2jakarta.csv.core</code></li>
     * </ul>
     */
    String TRUSTED_MODE = "up2jakarta.csv.core.trusted.mode";

    /**
     * Returns the bean instance that matches the specified bean class and the qualified name if not empty.
     *
     * @param ctn  the beans container
     * @param type the bean type
     * @param <B>  the bean class
     * @return the bean instance
     */
    @SuppressWarnings("unchecked")
    static <B> B from(Container ctn, Class<?> type, String name) throws BeanException {
        try {
            if (name.isEmpty()) {
                return (B) ctn.getBean(type);
            }
            return (B) ctn.getBean(type, name);
        } catch (Exception e) {
            throw new BeanException(type, "qualified bean must be found");
        }
    }

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
        throw new UnsupportedOperationException();
    }

}

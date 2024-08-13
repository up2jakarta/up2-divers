package io.github.up2jakarta.csv.exception;

/**
 * {@link RuntimeException} for {@link io.github.up2jakarta.csv.annotation.Processor}.
 */
public class ProcessorException extends RuntimeException {

    private static final String FORMAT = "%s[%s] processing error: %s";

    private final Class<?> beanType;
    private final String property;

    public ProcessorException(final Class<?> beanType, final String property, final Throwable cause) {
        super(cause);
        this.beanType = beanType;
        this.property = property;
    }

    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public ProcessorException(final Class<?> beanType, final String property, final String message) {
        super(message);
        this.beanType = beanType;
        this.property = property;
    }

    /**
     * @return the bean property name
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return the bean class
     */
    public final Class<?> getBeanType() {
        return beanType;
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, beanType.getSimpleName(), property, getMessage());
    }

}

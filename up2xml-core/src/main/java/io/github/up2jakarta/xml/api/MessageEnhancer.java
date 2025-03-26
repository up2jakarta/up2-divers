package io.github.up2jakarta.xml.api;

@FunctionalInterface
public interface MessageEnhancer {

    static <E extends Throwable> E getCause(Throwable ex, Class<E> type) {
        if (ex == null) {
            return null;
        }
        if (type.isInstance(ex)) {
            //noinspection unchecked
            return (E) ex;
        }
        return getCause(ex.getCause(), type);
    }

    String enhance(Throwable cause, String msg);

}

package io.github.up2jakarta.job.core;

@SuppressWarnings("unused")
public abstract class BusinessException extends RuntimeException {

    protected <T> BusinessException(BusinessError<?> error, Throwable cause) {
        super(error.toString(), cause);
    }

    protected BusinessException(BusinessError<?> error) {
        super(error.toString());
    }

    public static Throwable getCause(Throwable ex) {
        if (ex instanceof BusinessException) {
            return ex.getCause();
        }
        return ex;
    }

    public static BusinessError<?> getError(Throwable ex, BusinessError<?> defaultError) {
        if (ex instanceof BusinessException fte) {
            return fte.getError();
        }
        return defaultError;
    }

    public abstract BusinessError<?> getError();

}

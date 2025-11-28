package io.github.up2jakarta.lov;

/**
 * Contract interface for {@link IError} with localized message.
 * <p>
 * Note that the implementation maye not extends {@link Exception} to avoid performance issues
 * related to the computing {@link Exception#getStackTrace()}.
 */
public interface IException extends IError, IMessage {

    String FORMAT = "#[%s] %s";

    /**
     * @return the error cause.
     */
    Throwable getCause();

}

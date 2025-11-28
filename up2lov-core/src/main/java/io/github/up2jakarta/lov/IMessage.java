package io.github.up2jakarta.lov;

/**
 * Contract interface for format an exception, useful for readable errors.
 */
public interface IMessage {

    /**
     * @return the localized message.
     * @see Exception#getLocalizedMessage()
     */
    String getLocalizedMessage();

}

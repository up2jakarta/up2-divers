package io.github.up2jakarta.lov;

/**
 * Contract interface for format an exception, useful for readable errors.
 */
public interface MessageFormatter {

    /**
     * @return the formatted message.
     */
    String getFormattedMessage();

}

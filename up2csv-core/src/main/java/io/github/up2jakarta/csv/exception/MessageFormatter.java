package io.github.up2jakarta.csv.exception;

/**
 * Contract interface for format an exception, useful to store errors.
 */
public interface MessageFormatter {

    /**
     * @return the formatted message.
     */
    String getFormattedMessage();

}

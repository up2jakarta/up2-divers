package io.github.up2jakarta.xml.api;

/**
 * Encapsulate XML configuration error.
 */
public class XConfigurationException extends RuntimeException {

    public XConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

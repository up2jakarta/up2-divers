package io.github.up2jakarta.xml.api;

/**
 * Encapsulate XML validation error.
 */
public class XValidationException extends RuntimeException {

    public XValidationException(Throwable cause) {
        super(cause);
    }

    public XValidationException(IValidationError error) {
        super("[" + error.getLineNumber() + "] " + error.getMessage(), error.getLinkedException());
    }

    public int size() {
        return 0;
    }

}

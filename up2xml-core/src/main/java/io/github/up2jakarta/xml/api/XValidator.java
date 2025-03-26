package io.github.up2jakarta.xml.api;

import org.w3c.dom.Document;

import java.util.List;

/**
 * Common validator for XML invoices.
 *
 * @param <X> the XML Type
 */
public interface XValidator<X> extends IValidator<IValidationError> {

    /**
     * Validate the given {@param invoice}.
     *
     * @param invoice the input to be validated.
     * @return the list of errors.
     */
    List<IValidationError> validate(X invoice);

    /**
     * Validate the given {@param xmlDocument}.
     *
     * @param xmlDocument the input to be validated: Must be an XML document and must not be null.
     * @return the list of errors.
     */
    List<IValidationError> validate(Document xmlDocument);

}

package io.github.up2jakarta.cii.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Generic validator for XML files.
 *
 * @param <E> the type of error
 */
public interface IValidator<E> {

    /**
     * Validate the given XML file.
     *
     * @param xmlFile the input to be validated: Must be an XML document and must not be null.
     * @return the list of errors.
     * @throws IOException if the underlying {@link org.xml.sax.XMLReader} throws an {@link IOException}.
     */
    List<E> validate(File xmlFile) throws IOException;

}

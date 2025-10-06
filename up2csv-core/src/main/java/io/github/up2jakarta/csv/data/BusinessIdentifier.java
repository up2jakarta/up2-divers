package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.core.BeanException;

@FunctionalInterface
public interface BusinessIdentifier<S extends Segment> {

    /**
     * Gets and returns the unique business identifier.
     *
     * @param bean the segment bean
     * @return the business identifier
     * @throws BeanException if the business property is not accessible for read
     * @see BusinessId
     * @see ParentId
     */
    Object get(S bean) throws BeanException;

}

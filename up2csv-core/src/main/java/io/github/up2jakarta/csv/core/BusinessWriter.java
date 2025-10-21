package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.Referencable;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Base business writer for multi-segments format, that's able to write business-objects to output stream.
 *
 * @param <T> the business object type
 */
public abstract class BusinessWriter<T extends Referencable> implements Closeable, Flushable {

    /**
     * Segregates the given business-object to many records and writes them.
     *
     * @param bean the business object to write in multi-segments format
     * @throws IOException   for some reason cannot be opened for writing.
     * @throws BeanException for any problem when getting fields from business-object
     */
    public abstract void write(T bean) throws IOException, BeanException;

    /**
     * Writes the given record in the stream.
     *
     * @param record the record data
     * @throws IOException for some reason cannot be opened for writing.
     */
    protected abstract void write(String[] record) throws IOException;

}

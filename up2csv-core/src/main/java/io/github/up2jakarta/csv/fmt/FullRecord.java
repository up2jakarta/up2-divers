package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.data.Header;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.core.Identifiable;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FULL}.
 *
 * @param <T> the input segment type
 * @see FullError
 */
public class FullRecord<T extends CodeList<T>> extends FastRecord<T> implements IFullRecord<T>, Identifiable<String> {

    @Header(code = "RID", name = "Record")
    protected final String key;

    public FullRecord(String key, T type, String pivot, String[] data) {
        super(type, pivot, data);
        this.key = key;
    }

    @Override
    public final String getKey() {
        return key;
    }

    @Override
    public String getReference() {
        return key;
    }
}

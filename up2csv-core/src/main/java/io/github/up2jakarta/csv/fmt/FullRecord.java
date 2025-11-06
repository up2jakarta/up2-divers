package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.csv.data.Identifiable;

import static io.github.up2jakarta.xml.adapters.KeyCoder.token;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FULL}.
 *
 * @param <T> the segment type
 * @see FullError
 */
public class FullRecord<T extends IType<?, T>> extends FastRecord<T> implements IFullRecord<T>, Identifiable<String> {

    @Definition(code = "RID", value = "Record")
    protected final String key;

    public FullRecord(String key, T type, String pivot, String[] data) {
        super(type, pivot, data);
        this.key = token(key);
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

package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.core.Identifiable;

import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FULL}.
 *
 * @param <T> the input segment type
 * @param <P> the input pivot type
 * @see FullError
 */
public class FullRecord<T extends CodeList<T>, P extends Comparable<P>> extends FastRecord<T, P> implements IFullRecord<T, P>, Identifiable<String> {

    @Definition(code = "RID", value = "Record")
    protected final String key;

    public FullRecord(String key, T type, P pivot, String[] data) {
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

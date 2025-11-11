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
 * @param <I> the input type definition
 * @param <P> the pivot type
 * @see FullError
 */
public class FullRecord<I extends IType<?, I>, P extends Comparable<P>> extends FastRecord<I, P> implements IFullRecord<I, P>, Identifiable<String> {

    @Definition(code = "RID", value = "Record")
    protected final String key;

    public FullRecord(String key, I type, P pivot, String[] data) {
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

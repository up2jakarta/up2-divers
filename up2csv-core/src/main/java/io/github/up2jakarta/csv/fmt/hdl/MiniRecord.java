package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;

import java.util.Arrays;

import static io.github.up2jakarta.xml.adapters.KeyCoder.token;

/**
 * Simple implementation of record for {@link io.github.up2jakarta.csv.core.ModeType#FAST}.
 *
 * @param <T> the segment type
 */
public class MiniRecord<T extends IType<?, T>> implements IRecord<T> {

    private final T type;
    private final String[] data;
    private final String businessKey;

    public MiniRecord(T type, String businessKey, String... data) {
        this.businessKey = token(businessKey);
        this.type = type;
        this.data = data;
    }

    @Override
    public String getBusinessReference() {
        return businessKey;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public String[] getColumns() {
        return data;
    }

    @Override
    public final String toString() {
        return "#" + businessKey + "[" + type.getCode() + "] -> " + Arrays.toString(data);
    }

}

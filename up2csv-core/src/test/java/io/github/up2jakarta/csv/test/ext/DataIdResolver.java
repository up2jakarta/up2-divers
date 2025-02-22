package io.github.up2jakarta.csv.test.ext;

import io.github.up2jakarta.csv.extension.DataTypeResolver;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.input.DataId;

import java.lang.reflect.Field;
import java.util.Optional;

public class DataIdResolver extends DataTypeResolver<DataId> {

    public static final DataIdResolver INSTANCE = new DataIdResolver();

    private DataIdResolver() {
        super(DataId.class);
    }

    @Override
    public Optional<DataId> get(Class<? extends Segment> type, Field field, Field... path) {
        return Optional.of(DataId.NONE);
    }

}

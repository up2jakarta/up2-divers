package io.github.up2jakarta.csv.test.ext;

import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.impl.DataId;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Stack;

public class DataIdResolver extends DataTypeResolver<DataId> {

    public static final DataIdResolver INSTANCE = new DataIdResolver();

    private DataIdResolver() {
        super(DataId.class);
    }

    @Override
    public Optional<DataId> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) throws BeanException {
        return Optional.of(DataId.NONE);
    }

}

package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeException;

final class PropertyModeType extends EventModeType<TypeException> {

    static final PropertyModeType INSTANCE = new PropertyModeType();

    private PropertyModeType() {
        super(TypeException.class);
    }

    @Override
    public TypeException of(SeverityType level, String code, String message) {
        return new TypeException(level, code, message);
    }

    @Override
    public TypeException of(SeverityType level, String code, Throwable cause) {
        return TypeException.of(level, code, cause);
    }

    @Override
    public TypeException of(SeverityType level, String code, String message, Throwable cause) {
        return new TypeException(level, code, message, cause);
    }
}

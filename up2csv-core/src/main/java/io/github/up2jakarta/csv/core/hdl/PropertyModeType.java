package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.lov.PropertyException;
import io.github.up2jakarta.lov.SeverityType;

final class PropertyModeType extends EventModeType<PropertyException> {

    static final PropertyModeType INSTANCE = new PropertyModeType();

    private PropertyModeType() {
        super(PropertyException.class);
    }

    @Override
    public PropertyException of(SeverityType level, String code, String message) {
        return new PropertyException(level, code, message);
    }

    @Override
    public PropertyException of(SeverityType level, String code, Throwable cause) {
        return PropertyException.of(level, code, cause);
    }

    @Override
    public PropertyException of(SeverityType level, String code, String message, Throwable cause) {
        return new PropertyException(level, code, message, cause);
    }
}

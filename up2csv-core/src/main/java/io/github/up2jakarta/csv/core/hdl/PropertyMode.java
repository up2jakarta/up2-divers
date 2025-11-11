package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

final class PropertyMode extends EventType<PropertyException> {
    static final PropertyMode INSTANCE = new PropertyMode();

    private PropertyMode() {
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
}

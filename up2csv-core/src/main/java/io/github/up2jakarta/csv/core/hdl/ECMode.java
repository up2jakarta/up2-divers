package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

final class ECMode extends EventType<PropertyException> {
    static final ECMode INSTANCE = new ECMode();

    private ECMode() {
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

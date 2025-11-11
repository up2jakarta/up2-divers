package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.xml.api.SeverityType;

final class BusinessMode extends EventType<BusinessException> {

    public static final BusinessMode INSTANCE = new BusinessMode();

    private BusinessMode() {
        super(BusinessException.class);
    }

    @Override
    public BusinessException of(SeverityType level, String code, String message) {
        return new BusinessException(level, code, message, null);
    }

    @Override
    public BusinessException of(SeverityType level, String code, Throwable cause) {
        return new BusinessException(level, code, cause.getMessage(), cause);
    }

}

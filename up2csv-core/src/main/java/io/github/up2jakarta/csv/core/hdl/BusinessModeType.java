package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.lov.SeverityType;

final class BusinessModeType extends EventModeType<BusinessException> {

    static final BusinessModeType INSTANCE = new BusinessModeType();

    private BusinessModeType() {
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

    @Override
    public BusinessException of(SeverityType level, String code, String message, Throwable cause) {
        return new BusinessException(level, code, message, cause);
    }

}

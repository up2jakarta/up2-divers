package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.SeverityType;

final class BusinessModeType extends EventModeType<IException> {

    static final EventModeType<? extends IException> INSTANCE = new BusinessModeType();

    private BusinessModeType() {
        super(FakeException.class);
    }

    @Override
    public IException of(SeverityType level, String code, String message) {
        return new FakeException(level, code, message, null);
    }

    @Override
    public IException of(SeverityType level, String code, Throwable cause) {
        return new FakeException(level, code, cause.getMessage(), cause);
    }

    @Override
    public IException of(SeverityType level, String code, String message, Throwable cause) {
        return new FakeException(level, code, message, cause);
    }

    private static final class FakeException implements IException {
        private final String code;
        private final String message;
        private final Throwable cause;
        private final SeverityType level;

        private FakeException(SeverityType level, String code, String message, Throwable cause) {
            this.cause = cause;
            this.code = code;
            this.level = level;
            this.message = message;
        }

        @Override
        public String getLocalizedMessage() {
            return String.format(FORMAT, code, message);
        }

        @Override
        public SeverityType getLevel() {
            return level;
        }

        @Override
        public Throwable getCause() {
            return cause;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getCode() {
            return code;
        }
    }
}

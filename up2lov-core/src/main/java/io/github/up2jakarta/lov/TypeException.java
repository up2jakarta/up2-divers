package io.github.up2jakarta.lov;

public class TypeException extends RuntimeException implements IException {

    protected final String code;
    protected final SeverityType level;

    public TypeException(SeverityType level, String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.level = level;
    }

    public TypeException(SeverityType level, String code, String message) {
        super(message);
        this.code = code;
        this.level = level;
    }

    public static TypeException of(SeverityType level, String code, Throwable cause) {
        if (cause instanceof TypeException pex) {
            if (pex.equals(level, code)) {
                return pex;
            } else if (pex.getCause() == null) {
                return new TypeException(level, code, cause.getMessage());
            }
        }
        return new TypeException(level, code, cause.getMessage(), cause);
    }

    public final boolean equals(SeverityType level, String code) {
        return code.equals(this.code) && level.equals(this.level);
    }

    /**
     * @return the default error level
     */
    @Override
    public final SeverityType getLevel() {
        return level;
    }

    /**
     * @return the default error code
     */
    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public String getLocalizedMessage() {
        return String.format(FORMAT, code, super.getMessage());
    }

}
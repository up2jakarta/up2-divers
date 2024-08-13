package io.github.up2jakarta.csv.test.persistence;

import io.github.up2jakarta.csv.misc.SeverityType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("unused")
abstract class AbstractError {

    @Column(name = "ERR_COLUMN_INDEX", nullable = false)
    private Integer offset;

    @Column(name = "ERR_SEVERITY", nullable = false)
    private SeverityType severity;

    @Column(name = "ERR_CODE", length = 63, nullable = false)
    private String code;

    @Column(name = "ERR_MESSAGE", length = 1023, nullable = false)
    private String message;

    public final SeverityType getSeverity() {
        return severity;
    }

    public final void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public final Integer getOffset() {
        return offset;
    }

    public final void setOffset(Integer offset) {
        this.offset = offset;
    }

    public final String getCode() {
        return code;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

}


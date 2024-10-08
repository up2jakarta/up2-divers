package io.github.up2jakarta.csv.test.input;

import io.github.up2jakarta.csv.extension.SeverityType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.LongVarcharJdbcType;

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JdbcType(LongVarcharJdbcType.class)
    @Column(name = "ERR_TRACE", nullable = false)
    private String trace;

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

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}


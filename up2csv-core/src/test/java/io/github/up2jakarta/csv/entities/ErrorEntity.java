package io.github.up2jakarta.csv.entities;

import io.github.up2jakarta.csv.converters.SeverityConverter;
import io.github.up2jakarta.csv.extension.SeverityType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.LongVarcharJdbcType;

@MappedSuperclass
public abstract class ErrorEntity extends ImmutableEntity {

    @Column(name = "ERR_SEVERITY", nullable = false)
    @Convert(converter = SeverityConverter.class)
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

    public SeverityType getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

}

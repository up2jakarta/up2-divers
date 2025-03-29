package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputError.Key;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.*;

@MappedSuperclass
@SuppressWarnings("unused")
abstract class AbstractError<K extends Key<InputRowEntity>> implements InputError<InputRowEntity, K, DataId> {

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
    //@JdbcType(LongVarcharJdbcType.class)
    @Column(name = "ERR_TRACE", nullable = false)
    private String trace;

    @Override
    public final SeverityType getSeverity() {
        return severity;
    }

    @Override
    public final void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    @Override
    public final Integer getOffset() {
        return offset;
    }

    @Override
    public final void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public final void setCode(String code) {
        this.code = code;
    }

    @Override
    public final String getMessage() {
        return message;
    }

    @Override
    public final void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getTrace() {
        return trace;
    }

    @Override
    public void setTrace(String trace) {
        this.trace = trace;
    }

}


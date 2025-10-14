package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.hdl.IErrorEntity;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static io.github.up2jakarta.csv.impl.InputErrorEntity.PKey;

@Entity
@Table(name = "TB_INPUT_ERRORS")
@SuppressWarnings("unused")
public class InputErrorEntity implements IErrorEntity<InputRowEntity, PKey, GroupType> {

    @EmbeddedId
    private PKey key;

    @Transient
    @Column(name = "ERR_DATA_TYPE", nullable = false)
    private GroupType type;

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
    public PKey getKey() {
        return key;
    }

    public void setKey(PKey key) {
        this.key = key;
    }

    @Override
    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    @Override
    public final SeverityType getSeverity() {
        return severity;
    }

    public final void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    @Override
    public final Integer getOffset() {
        return offset;
    }

    public final void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public final String getCode() {
        return code;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    @Override
    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    @Embeddable
    public static class PKey implements Serializable, IKey<InputRowEntity> {

        @ManyToOne(optional = false)
        @JoinColumns(
                value = {
                        @JoinColumn(name = "ERR_FILE_ID", referencedColumnName = "ROW_FILE_ID"),
                        @JoinColumn(name = "ERR_ROW_ORDER", referencedColumnName = "ROW_ORDER")
                },
                foreignKey = @ForeignKey(name = "FK_INPUT_ERROR_ROW")
        )
        private InputRowEntity record;

        @Column(name = "ERR_ORDER", nullable = false)
        private Integer order;

        @Override
        public InputRowEntity getRecord() {
            return record;
        }

        public void setRecord(InputRowEntity record) {
            this.record = record;
        }

        @Override
        public Integer getOrder() {
            return order;
        }

        @Override
        public void setOrder(Integer order) {
            this.order = order;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final PKey that = (PKey) other;
            return Objects.equals(record, that.record) && Objects.equals(order, that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(record, order);
        }

    }

}

package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.EntityList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "TB_SYSTEMS"/*, uniqueConstraints = @UniqueConstraint(name = "UK_SYSTEM", columnNames = {"SYS_CODE"})*/)
@SuppressWarnings("unused")
public class SystemEntity implements EntityList<Integer, SystemEntity> {

    @Id
    @Column(name = "SYS_ID")
    private Integer key;

    @Column(name = "SYS_CODE", length = 2, nullable = false/*, unique = true*/)
    private String code;

    @Column(name = "SYS_LABEL", length = 63, nullable = false)
    private String label;

    @Column(name = "SYS_COMMENT", length = 1023)
    private String comment;

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return label;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SystemEntity that)) {
            return false;
        }
        return Objects.equals(code, that.code); // unique
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return code;
    }

}


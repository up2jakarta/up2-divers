package io.github.up2jakarta.csv.entities.params;

import io.github.up2jakarta.csv.entities.input.InputHeaderEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Immutable
@Table(name = "TB_SYSTEMS")
public class SystemEntity implements Serializable {

    @OneToMany(mappedBy = "system")
    private final List<InputHeaderEntity> inputHeaders = new LinkedList<>();
    @Id
    @Column(name = "SO_ID")
    private Integer id;
    @Column(name = "SO_CODE", length = 3, unique = true)
    private String code;
    @Column(name = "SO_LABEL", length = 63)
    private String label;
    @Column(name = "SO_COMMENT", length = 1023)
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<InputHeaderEntity> getInputHeaders() {
        return inputHeaders;
    }
}

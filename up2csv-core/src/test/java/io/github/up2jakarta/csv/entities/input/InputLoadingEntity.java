package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.entities.ImmutableEntity;
import io.github.up2jakarta.csv.entities.batch.JobExecutionEntity;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "TB_INPUT_LOADINGS")
public class InputLoadingEntity extends ImmutableEntity {

    @OneToMany(mappedBy = "key.loading")
    private final List<InputRowEntity> rows = new LinkedList<>();
    @OneToMany(mappedBy = "key.loading")
    private final List<InputLoadingErrorEntity> errors = new LinkedList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_INPUT_LOADING_ID")
    @SequenceGenerator(name = "SQ_INPUT_LOADING_ID", sequenceName = "SQ_INPUT_LOADING_ID", allocationSize = 1)
    @Column(name = "LOAD_ID")
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "LOAD_EXEC_ID", referencedColumnName = "JOB_EXECUTION_ID",
            foreignKey = @ForeignKey(name = "FK_INPUT_LOADING_EXECUTION")
    )
    private JobExecutionEntity execution;
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "LOAD_FILE_ID", referencedColumnName = "FILE_ID",
            foreignKey = @ForeignKey(name = "FK_INPUT_LOADING_FILE")
    )
    private InputFileEntity file;
    @OneToOne(mappedBy = "loading", fetch = FetchType.LAZY, orphanRemoval = true)
    private InputHeaderEntity header;
    @OneToOne(mappedBy = "loading", fetch = FetchType.LAZY, orphanRemoval = true)
    private InputFooterEntity footer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobExecutionEntity getExecution() {
        return execution;
    }

    public void setExecution(JobExecutionEntity execution) {
        this.execution = execution;
    }

    public InputFileEntity getFile() {
        return file;
    }

    public void setFile(InputFileEntity file) {
        this.file = file;
    }

    public InputHeaderEntity getHeader() {
        return header;
    }

    public void setHeader(InputHeaderEntity header) {
        this.header = header;
    }

    public InputFooterEntity getFooter() {
        return footer;
    }

    public void setFooter(InputFooterEntity footer) {
        this.footer = footer;
    }

    public List<InputRowEntity> getRows() {
        return rows;
    }

    public List<InputLoadingErrorEntity> getErrors() {
        return errors;
    }

}

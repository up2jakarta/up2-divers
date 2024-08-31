package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.entities.ImmutableEntity;
import io.github.up2jakarta.csv.entities.batch.JobExecutionEntity;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

/**
 * {@see https://ibm.github.io/ibm-cos-sdk-java/com/ibm/cloud/objectstorage/services/s3/model/ObjectMetadata.html}
 */
@Entity
@Table(name = "TB_INPUT_FILES")
public class InputFileEntity extends ImmutableEntity {

    @OneToMany(mappedBy = "file")
    private final List<InputLoadingEntity> loadings = new LinkedList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_INPUT_FILE_ID")
    @SequenceGenerator(name = "SQ_INPUT_FILE_ID", sequenceName = "SQ_INPUT_FILE_ID", allocationSize = 1)
    @Column(name = "FILE_ID")
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "FILE_EXEC_ID", referencedColumnName = "JOB_EXECUTION_ID",
            foreignKey = @ForeignKey(name = "FK_INPUT_FILE_EXECUTION")
    )
    private JobExecutionEntity execution;
    @Column(name = "FILE_ARCHIVE_PATH", nullable = false)
    private String archivePath;
    @Column(name = "FILE_CONTENT_LENGTH", nullable = false)
    private Long contentLength;
    @Column(name = "FILE_ARCHIVE_STATUS", length = 15)
    private String archiveStatus;
    @Column(name = "FILE_VERSION", length = 15)
    private String versionId;
    @Column(name = "FILE_MD5", length = 511)
    private String contentMD5;

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

    public String getArchivePath() {
        return archivePath;
    }

    public void setArchivePath(String archivePath) {
        this.archivePath = archivePath;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public String getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(String archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getContentMD5() {
        return contentMD5;
    }

    public void setContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
    }

    public List<InputLoadingEntity> getLoadings() {
        return loadings;
    }

}

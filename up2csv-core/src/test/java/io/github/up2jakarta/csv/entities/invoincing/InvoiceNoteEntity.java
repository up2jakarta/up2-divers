package io.github.up2jakarta.csv.entities.invoincing;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.annotation.Validated;
import io.github.up2jakarta.csv.entities.ParsedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_INVOICE_NOTES")
@AssociationOverride(name = "loading", foreignKey = @ForeignKey(name = "FK_INVOICE_NOTE_INPUT_LOADING"),
        joinColumns = @JoinColumn(insertable = false, updatable = false,
                name = "NOTE_LOAD_ID", referencedColumnName = "LOAD_ID"
        )
)
@AssociationOverride(name = "row", foreignKey = @ForeignKey(name = "FK_INVOICE_NOTE_INPUT_ROW"),
        joinColumns = {
                @JoinColumn(name = "NOTE_LOAD_ID", referencedColumnName = "ROW_LOAD_ID"),
                @JoinColumn(name = "NOTE_ROW_ORDER", referencedColumnName = "ROW_ORDER")
        }
)
@Validated
@Up2EnableJPA
public class InvoiceNoteEntity extends ParsedEntity<InvoiceNoteKey> {

    @EmbeddedId
    private InvoiceNoteKey key;

    @Size(max = 3)
    @Column(name = "NOTE_SUBJECT_CODE", length = 3)
    @Position(0)
    private String subjectCode;

    @NotBlank
    @Size(max = 1024)
    @Position(1)
    @Column(name = "NOTE_CONTENT", length = 1024, nullable = false)
    private String content;

    public InvoiceNoteKey getKey() {
        return key;
    }

    public void setKey(InvoiceNoteKey key) {
        this.key = key;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

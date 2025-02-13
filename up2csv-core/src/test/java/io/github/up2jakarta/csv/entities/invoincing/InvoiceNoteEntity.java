package io.github.up2jakarta.csv.entities.invoincing;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.annotation.Validated;
import io.github.up2jakarta.csv.entities.ParsedEntity;
import io.github.up2jakarta.csv.test.codelist.CountryCodeType;
import io.github.up2jakarta.csv.test.codelist.CountryConverter;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
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
@SuppressWarnings("unused")
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

    @Fragment(2)
    @AttributeOverride(name = "value", column = @Column(name = "SLR_URI_ID", length = 100))
    @AttributeOverride(name = "schemeId", column = @Column(name = "SLR_URI_CODE", length = 4))
    @Embedded
    @Convert(attributeName = "schemeId", converter = CountryConverter.class)
    private Identifier<CountryCodeType> id1;

    @Fragment(2)
    @AttributeOverride(name = "value", column = @Column(name = "SLR_URI_ID", length = 100))
    @AttributeOverride(name = "schemeId", column = @Column(name = "SLR_URI_CODE", length = 4))
    @Embedded
    @Convert(attributeName = "schemeId", converter = CurrencyConverter.class)
    private Identifier<CurrencyCodeType> id2;

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

    public Identifier<CountryCodeType> getId1() {
        return id1;
    }

    public void setId1(Identifier<CountryCodeType> id1) {
        this.id1 = id1;
    }

    public Identifier<CurrencyCodeType> getId2() {
        return id2;
    }

    public void setId2(Identifier<CurrencyCodeType> id2) {
        this.id2 = id2;
    }

}

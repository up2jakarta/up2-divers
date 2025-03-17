package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.annotation.ValidOverride;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.CountryCodeType;
import io.github.up2jakarta.csv.test.codelist.CountryConverter;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TU_NOTES")
@AttributeOverride(name = "test1.value", column = @Column(name = "NOTE_TEST_1_ID", length = 100))
@AttributeOverride(name = "test1.code", column = @Column(name = "NOTE_TEST_1_CODE", length = 2))
@Convert(attributeName = "test1.code", converter = CountryConverter.class)
@AttributeOverride(name = "test2.value", column = @Column(name = "NOTE_TEST_2_ID", length = 200))
@AttributeOverride(name = "test2.code", column = @Column(name = "NOTE_TEST_2_CODE", length = 3))
@Convert(attributeName = "test2.code", converter = CurrencyConverter.class)
@ValidOverride
@Up2EnableJPA
@SuppressWarnings("unused")
public class NoteEntity implements Segment {

    @Id
    @Column(name = "NOTE_ID")
    private long key;

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
    @Embedded
    @Valid
    private NoteId<CountryCodeType> test1;

    @Fragment(4)
    @Valid
    @Embedded
    private NoteId<CurrencyCodeType> test2;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
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

    public NoteId<CountryCodeType> getTest1() {
        return test1;
    }

    public void setTest1(NoteId<CountryCodeType> test1) {
        this.test1 = test1;
    }

    public NoteId<CurrencyCodeType> getTest2() {
        return test2;
    }

    public void setTest2(NoteId<CurrencyCodeType> test2) {
        this.test2 = test2;
    }

}

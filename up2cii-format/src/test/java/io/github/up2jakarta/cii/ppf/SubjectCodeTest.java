package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentType;
import io.github.up2jakarta.cii.format.standard.ram.NoteType;
import io.github.up2jakarta.cii.format.unmapped.udt.CodeType;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SubjectCodeTest extends CodeAdapterTest {

    private static final CodeType WRONG_CODE = new CodeType() {{
        setValue("???");
    }};

    public SubjectCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_subject.xml", (i) -> {
            var doc = i.getExchangedDocument();
            var notes = doc.getIncludedNote();
            var note = notes.get(0);
            note.setSubjectCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final ExchangedDocumentType doc = validInvoice.getExchangedDocument();
        assertNotNull(doc);
        final List<NoteType> notes = doc.getIncludedNote();
        assertNotNull(notes);
        assertEquals(1, notes.size());
        final NoteType note = notes.get(0);
        assertNotNull(note);
        final SubjectCodeType code = note.getSubjectCode();
        assertNotNull(code);
        assertEquals(SubjectCodeType.REG, code);
        assertNotNull(code.getName());
    }

    @Test
    public void read() throws IOException {
        assertThrows(XValidationException.class, () -> reader.read(invalidInvoiceFile, false));
    }

    @Test
    public void validate() throws IOException {
        final List<IValidationError> errors = validator.validate(invalidInvoiceFile);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        {
            final IValidationError error = errors.get(0);
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(19, error.getLineNumber());
            assertEquals(51, error.getColumnNumber());
            assertEquals("ECE-4451: Unknown value [???] for CodeList[SubjectCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}

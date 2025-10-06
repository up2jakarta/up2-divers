package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.edi.CountryIDType;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentType;
import io.github.up2jakarta.cii.format.standard.ram.NoteType;
import io.github.up2jakarta.cii.format.standard.udt.TextType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.api.XValidationException;
import io.github.up2jakarta.xml.clv.CodeListException;
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
public class LanguageCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public LanguageCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_language.xml", (i) -> {
            var doc = i.getExchangedDocument();
            var notes = doc.getIncludedNote();
            var note = notes.getFirst();
            var contents = note.getContent();
            var content = contents.getFirst();
            content.setLanguageID(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final ExchangedDocumentType doc = validInvoice.getExchangedDocument();
        assertNotNull(doc);
        final List<NoteType> notes = doc.getIncludedNote();
        assertNotNull(notes);
        assertEquals(1, notes.size());
        final NoteType note = notes.getFirst();
        assertNotNull(note);
        final List<TextType> contents = note.getContent();
        assertNotNull(contents);
        assertEquals(1, contents.size());
        final TextType content = contents.getFirst();
        assertNotNull(content);
        {
            final LanguageCodeType code = content.getLanguageID();
            assertNotNull(code);
            assertEquals(LanguageCodeType.FR, code);
            assertNotNull(code.getName());
        }
        {
            final CountryIDType code = content.getLanguageLocaleID();
            assertNotNull(code);
            assertEquals(CountryIDType.FR, code);
            assertNotNull(code.getName());
        }
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
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(18, error.getLineNumber());
            assertEquals(65, error.getColumnNumber());
            assertEquals("ISO-639: Unknown value [???] for CodeList[LanguageCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}

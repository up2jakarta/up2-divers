package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentType;
import io.github.up2jakarta.cii.format.unmapped.qdt.DocumentCodeType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.api.XValidationException;
import io.github.up2jakarta.xml.codelist.CodeListException;
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
public class InvoiceCodeTest extends CodeAdapterTest {

    private static final DocumentCodeType WRONG_CODE = new DocumentCodeType() {{
        setValue("50");
    }};

    public InvoiceCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_invoice.xml", (i) -> {
            var doc = i.getExchangedDocument();
            doc.setTypeCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final ExchangedDocumentType doc = validInvoice.getExchangedDocument();
        assertNotNull(doc);
        final InvoiceCodeType code = doc.getTypeCode();
        assertNotNull(code);
        assertEquals(InvoiceCodeType.V_380, code);
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
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(13, error.getLineNumber());
            assertEquals(40, error.getColumnNumber());
            assertEquals("PPF-G101: Unknown value [50] for CodeList[InvoiceCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}

package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentType;
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
public class MessageFunctionCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.MessageFunctionCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.MessageFunctionCodeType() {{
                setValue("???");
            }};

    public MessageFunctionCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "valid2-formatCII.xml", "invalid_message_function.xml", (i) -> {
            var document = i.getExchangedDocument();
            document.setPurposeCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final ExchangedDocumentType document = validInvoice.getExchangedDocument();
        assertNotNull(document);
        final MessageFunctionCodeType code = document.getPurposeCode();
        assertNotNull(code);
        assertEquals(MessageFunctionCodeType.V_22, code);
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
            assertEquals(17, error.getLineNumber());
            assertEquals(47, error.getColumnNumber());
            assertEquals("ECE-1225: Unknown value [???] for CodeList[MessageFunctionCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}

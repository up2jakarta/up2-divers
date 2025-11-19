package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeAgreementType;
import io.github.up2jakarta.cii.format.standard.ram.ReferencedDocumentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.udt.BinaryObjectType;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.XValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class MimeCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public MimeCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_mime.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var agreement = trade.getApplicableHeaderTradeAgreement();
            var documents = agreement.getAdditionalReferencedDocument();
            var document = documents.getFirst();
            var attachments = document.getAttachmentBinaryObject();
            var attachment = attachments.getFirst();
            attachment.setMimeCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeAgreementType agreement = trade.getApplicableHeaderTradeAgreement();
        assertNotNull(agreement);
        final List<ReferencedDocumentType> documents = agreement.getAdditionalReferencedDocument();
        assertNotNull(documents);
        assertEquals(1, documents.size());
        var document = documents.getFirst();
        assertNotNull(document);
        final List<BinaryObjectType> attachments = document.getAttachmentBinaryObject();
        assertNotNull(attachments);
        assertEquals(1, attachments.size());
        var attachment = attachments.getFirst();
        assertNotNull(attachment);
        {
            final MimeCodeType code = attachment.getMimeCode();
            assertNotNull(code);
            assertEquals(MimeCodeType.XML, code);
            assertNotNull(code.getName());
        }
        {
            final Charset code = attachment.getCharset();
            assertEquals(StandardCharsets.UTF_8, code);
        }
        {
            final String value = Base64.getEncoder().encodeToString(attachment.getValue());
            assertEquals("TEST", value);
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
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(175, error.getLineNumber());
            assertEquals(85, error.getLineOffset());
            assertEquals("PPF-G417: Unknown value [???] for CodeList[MimeCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}

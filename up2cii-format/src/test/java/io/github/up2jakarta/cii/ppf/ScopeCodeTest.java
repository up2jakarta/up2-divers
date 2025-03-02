package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentContextType;
import io.github.up2jakarta.cii.format.standard.ram.ScopeContextParameterType;
import io.github.up2jakarta.cii.format.unmapped.udt.IDType;
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
public class ScopeCodeTest extends CodeAdapterTest {

    private static final IDType WRONG_CODE = new IDType() {{
        setValue("???");
    }};

    public ScopeCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_scope.xml", (i) -> {
            var ctx = i.getExchangedDocumentContext();
            var parameters = ctx.getBusinessProcessSpecifiedDocumentContextParameter();
            var parameter = parameters.get(0);
            parameter.setID(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final ExchangedDocumentContextType context = validInvoice.getExchangedDocumentContext();
        assertNotNull(context);
        final List<ScopeContextParameterType> parameters = context.getBusinessProcessSpecifiedDocumentContextParameter();
        assertNotNull(parameters);
        assertEquals(1, parameters.size());
        final ScopeContextParameterType parameter = parameters.get(0);
        assertNotNull(parameter);
        final ScopeType code = parameter.getID();
        assertNotNull(code);
        assertEquals(ScopeType.S1, code);
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
            assertEquals(5, error.getLineNumber());
            assertEquals(33, error.getColumnNumber());
            assertEquals("PPF-G102: Unknown value [???] for CodeList[ScopeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}

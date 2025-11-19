package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentContextType;
import io.github.up2jakarta.cii.format.standard.ram.ProfileContextParameterType;
import io.github.up2jakarta.cii.format.unmapped.udt.IDType;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ProfileCodeTest extends CodeAdapterTest {

    private static final IDType WRONG_CODE = new IDType() {{
        setValue("???");
    }};

    public ProfileCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_profile.xml", (i) -> {
            var ctx = i.getExchangedDocumentContext();
            var parameters = ctx.getGuidelineSpecifiedDocumentContextParameter();
            var parameter = parameters.getFirst();
            parameter.setID(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final ExchangedDocumentContextType context = validInvoice.getExchangedDocumentContext();
        assertNotNull(context);
        final List<ProfileContextParameterType> parameters = context.getGuidelineSpecifiedDocumentContextParameter();
        assertNotNull(parameters);
        assertEquals(1, parameters.size());
        final ProfileContextParameterType parameter = parameters.getFirst();
        assertNotNull(parameter);
        final ProfileType code = parameter.getID();
        assertNotNull(code);
        assertEquals(ProfileType.EN_16931, code);
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
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(8, error.getLineNumber());
            assertEquals(33, error.getLineOffset());
            assertEquals("PPF-S106: Unknown value [???] for CodeList[ProfileType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}

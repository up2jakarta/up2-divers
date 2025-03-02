package io.github.up2jakarta.cii.api;

import io.github.up2jakarta.cii.CII;
import io.github.up2jakarta.cii.InvoiceValidator;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@Named
@Singleton
public class TestUtil {

    static {
        Locale.setDefault(Locale.US);
    }

    private final ApplicationContext context;

    public TestUtil(@Autowired final ApplicationContext springContext) {
        this.context = springContext;
    }

    private static <I extends Enum<I>> void assertNaming(I constant, String code) {
        assertNotNull(code);
        assertFalse(code.isBlank());
        assertEquals(code, CII.TOKEN_ADAPTER.unmarshal(code));
        assertEquals(CII.codeConstant(code), constant.name());
    }

    private static <I extends Enum<I>> Documented assertCodeList(Class<I> implClass) {
        var codeList = implClass.getAnnotation(Documented.class);
        assertNotNull(codeList);
        assertNotNull(codeList.value());
        assertNotNull(codeList.version());
        assertNotNull(codeList.agency());
        return codeList;
    }

    private static <I extends Enum<I>> void assertSubList(Class<I> implClass) {
        var subList = implClass.getAnnotation(SubList.class);
        if (subList != null && !CodeList.class.equals(subList.type())) {
            var parentClass = subList.type();
            var parentSL = parentClass.getAnnotation(SubList.class);
            assertNull(parentSL);
        }
    }

    private static <I extends Enum<I>> void assertSchema(Class<I> implClass, Agency agency) {
        // Find CodeListAccessor
        var schema = implClass.getAnnotation(Schema.class);
        if (agency == Agency.EN_16931) {
            assertNull(schema);
            return;
        }
        assertNotNull(schema);
        // Find SubList
        var subList = implClass.getAnnotation(SubList.class);
        if (subList != null && !CodeList.class.equals(subList.type())) {
            var parentClass = subList.type();
            var parentSchema = parentClass.getAnnotation(Schema.class);
            assertNotNull(parentSchema);
            assertEquals(schema.agency(), parentSchema.agency(), implClass.getName());
        }
    }

    public static <I extends Enum<I>> void assertUniqueness(Class<I> implClass, Function<I, String> codeFunction, boolean includeNaming) {
        // Checking uniqueness
        var codes = new HashSet<String>();
        for (var impl : implClass.getEnumConstants()) {
            var code = (codeFunction.apply(impl));
            codes.add(code);
            if (includeNaming) {
                assertNaming(impl, code);
            }
        }
        var size = implClass.getEnumConstants().length;
        assertEquals(size, codes.size());
        // Checking Annotations
        var cl = assertCodeList(implClass);
        assertSubList(implClass);
        assertSchema(implClass, cl.agency());
    }

    public static <I extends Enum<I>> void assertUniqueness(Class<I> implClass, Function<I, String> codeFunction) {
        assertUniqueness(implClass, codeFunction, true);
    }

    public static File loadResource(final String resourcePath) throws FileNotFoundException {
        var url = CII.CLASS_LOADER.getResource(resourcePath);
        try {
            assert url != null;
            return new File(url.toURI());
        } catch (Throwable e) {
            throw new FileNotFoundException(resourcePath);
        }
    }

    public static <T> void assertInvoice(File file, XValidator<T> validator, XReader<T> reader) throws IOException {
        assertInvoice(file, validator, reader, false);
    }

    public static <T> void assertInvoice(File file, XValidator<T> validator, XReader<T> reader, boolean lenient) throws IOException {
        // When Validate
        final List<IValidationError> errors = validator.validate(file);
        assertNotNull(errors);
        // When Read
        final List<Throwable> causes = new ArrayList<>();
        try {
            final T invoice = reader.read(file, false, lenient);
            assertNotNull(invoice);
            assertTrue(errors.isEmpty());
        } catch (XMultipleException ex) {
            causes.addAll(ex.getCauses());
            assertNotNull(ex.getCause());
        } catch (XValidationException ex) {
            causes.add(ex);
            assertNotNull(ex.getCause());
        }
        // Then
        assertEquals(causes.size(), errors.size());
        for (var i = 0; i < causes.size(); i++) {
            var cause = causes.get(i).getCause().getMessage();
            var error = errors.get(i).getLinkedException().getMessage();
            assertEquals(cause, error);
        }
    }

    @SuppressWarnings("unchecked")
    public <I extends Enum<I>> void assertSameBinding(Class<I> implClass) throws Exception {
        // Find Adapter
        var adapterClass = implClass.getAnnotation(XmlJavaTypeAdapter.class).value();
        assertNotNull(adapterClass);
        final XmlAdapter<String, I> adapter = context.getBean(adapterClass);
        // Checking error code {
        try {
            adapter.unmarshal("???");
            fail();
        } catch (CodeListException ignore) {
        }
    }

    public <T> void assertInvoice(File file, XValidator<T> validator, XReader<T> reader, XWriter<T> writer) throws Exception {
        assertInvoice(file, validator, reader, writer, false);
    }

    public <T> void assertInvoice(File file, XValidator<T> validator, XReader<T> reader, XWriter<T> writer, boolean lenient) throws Exception {
        final Document document = parseDocument(file);
        assertNotNull(document);
        // Read

        assertNotNull(file);
        final T invoice = reader.read(file, false, lenient);
        assertNotNull(invoice);
        // Validate
        {
            final List<IValidationError> aErrors = validator.validate(invoice);
            assertNotNull(aErrors);
            assertEquals(0, aErrors.size());
        }
        // Write again
        final Document document2 = writer.write(invoice, false);
        assertNotNull(document2);
        assertEquals(document.getDocumentElement().getNamespaceURI(), document2.getDocumentElement().getNamespaceURI());
        assertEquals(document.getDocumentElement().getLocalName(), document2.getDocumentElement().getLocalName());
        // Read again
        final T invoice2 = reader.read(document2, false, lenient);
        assertNotNull(invoice2);
        // Validate
        {
            final List<IValidationError> aErrors = validator.validate(invoice2);
            assertNotNull(aErrors);
            assertEquals(0, aErrors.size());
        }
        // Both invoices are equals
        Assertions.assertThat(invoice).usingRecursiveComparison().isEqualTo(invoice2);
    }

    public Document parseDocument(File xmlFile) throws Exception {
        final DocumentBuilder documentBuilder = context.getBean(InvoiceValidator.class).getDocumentBuilder();
        return documentBuilder.parse(xmlFile);
    }

}

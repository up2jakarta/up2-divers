package io.github.up2jakarta.cii.api;

import io.github.up2jakarta.cii.format.standard.CrossIndustryInvoiceType;
import io.github.up2jakarta.xml.api.XReader;
import io.github.up2jakarta.xml.api.XValidator;
import io.github.up2jakarta.xml.codelist.CodeListException;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static io.github.up2jakarta.cii.TUConfiguration.xmlOutput;
import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceReaderTest.READER;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceWriterTest.WRITER;
import static java.nio.file.Files.createFile;
import static java.nio.file.Files.deleteIfExists;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class CodeAdapterTest {

    static final File XML_DIR = xmlOutput();
    private static final String XML_CLASS_PATH = "xml/unece/";

    protected final File invalidInvoiceFile;
    protected CrossIndustryInvoiceType validInvoice;
    protected XValidator<CrossIndustryInvoiceType> validator;
    protected XReader<CrossIndustryInvoiceType> reader;

    public CodeAdapterTest(ApplicationContext context, String invalid, Consumer<io.github.up2jakarta.cii.format.unmapped.CrossIndustryInvoiceType> error) throws IOException {
        this(context, "valid1-formatCII.xml", invalid, error);
    }

    @SuppressWarnings("unchecked")
    public CodeAdapterTest(ApplicationContext context, String valid, String invalid, Consumer<io.github.up2jakarta.cii.format.unmapped.CrossIndustryInvoiceType> error) throws IOException {
        var xml = loadResource(XML_CLASS_PATH + valid);
        validator = context.getBean(XValidator.class);
        reader = context.getBean(XReader.class);
        invalidInvoiceFile = generateInvalid(xml, invalid, error);
        validInvoice = reader.read(xml, true);
    }

    private File generateInvalid(File xml, String name, Consumer<io.github.up2jakarta.cii.format.unmapped.CrossIndustryInvoiceType> error) throws IOException {
        assertNotNull(xml);
        var invalid = READER.read(xml, true);
        error.accept(invalid);
        var generatedFile = new File(XML_DIR, name);
        deleteIfExists(generatedFile.toPath());
        createFile(generatedFile.toPath());
        WRITER.write(invalid, generatedFile, true);
        var invalidInvoiceFile = new File(generatedFile.toURI());
        assertNotNull(invalidInvoiceFile);
        assertTrue(invalidInvoiceFile.exists());
        assertTrue(invalidInvoiceFile.canRead());
        return invalidInvoiceFile;
    }

    public abstract void read() throws IOException;

    public abstract void value() throws CodeListException;

    public abstract void validate() throws IOException;
}

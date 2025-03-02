package io.github.up2jakarta.cii.format.unmapped;

import io.github.up2jakarta.cii.InvoiceReader;
import io.github.up2jakarta.cii.api.XReader;
import io.github.up2jakarta.cii.api.XValidationException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvoiceReaderTest {

    public static final XReader<CrossIndustryInvoiceType> READER;

    static {
        READER = new InvoiceReader<>(CrossIndustryInvoiceType.class, new XmlAdapter[0]);
    }

    @Test
    public void testFailSafeException() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC4-01-FactureCII.xml");
        assertNotNull(xmlFile);
        READER.read(xmlFile, false);
    }

    @Test
    public void testFailFastException() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC4-01-FactureCII.xml");
        assertNotNull(xmlFile);
        READER.read(xmlFile, true);
    }

    @Test
    public void testEmptyFile() throws FileNotFoundException {
        final File xmlFile = loadResource("xml/empty.xml");
        assertThrows(XValidationException.class, () -> READER.read(xmlFile, false));
    }

    @Test
    public void testNotFound() {
        final File xmlFile = new File("xml/boom.xml");
        assertThrows(FileNotFoundException.class, () -> READER.read(xmlFile, false));
    }
}

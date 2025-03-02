package io.github.up2jakarta.cii.format.standard;

import io.github.up2jakarta.cii.InvoiceReader;
import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.XConfigurationException;
import io.github.up2jakarta.cii.api.XMultipleException;
import io.github.up2jakarta.cii.api.XReader;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class InvoiceReaderTest {

    @Autowired
    private XReader<CrossIndustryInvoiceType> reader;

    @Test
    public void testFailSafeException() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC4-01-FactureCII.xml");
        assertNotNull(xmlFile);
        assertThrows(XMultipleException.class, () -> reader.read(xmlFile, false));
    }

    @Test
    public void testFailFastException() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC4-01-FactureCII.xml");
        assertNotNull(xmlFile);
        assertThrows(XValidationException.class, () -> reader.read(xmlFile, true));
    }

    @Test
    public void testEmptyFile() throws IOException {
        final File xmlFile = loadResource("xml/empty.xml");
        assertThrows(XValidationException.class, () -> reader.read(xmlFile, true));
    }

    @Test
    public void testNotFound() {
        final File xmlFile = new File("xml/boom.xml");
        assertThrows(FileNotFoundException.class, () -> reader.read(xmlFile, true));
    }

    @Test
    public void testConfigClass() {
        assertThrows(XConfigurationException.class, () -> new InvoiceReader<>(CodeList.class, new XmlAdapter[0]));
    }
}

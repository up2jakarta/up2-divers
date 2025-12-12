package io.github.up2jakarta.test.api;

import io.github.up2jakarta.cii.InvoiceReader;
import io.github.up2jakarta.cii.InvoiceValidator;
import io.github.up2jakarta.cii.InvoiceWriter;
import io.github.up2jakarta.cii.format.standard.CrossIndustryInvoiceType;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.xml.api.XValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

import static io.github.up2jakarta.test.api.CodeAdapterTest.XML_DIR;
import static io.github.up2jakarta.test.api.TestUtil.assertInvoice;
import static io.github.up2jakarta.test.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class MultiThreadTest {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);
    private static final List<String> VALID_FILES = Arrays.asList(
            "xml/ppf/UC1-01-Facture-formatCII.xml",
            "xml/ppf/UC1-05-Avoir-formatCII.xml",
            "xml/ppf/UC1-10-Rectificative-formatCII.xml",
            "xml/ppf/UC2-01-Facture-formatCII.xml",
            "xml/ppf/UC2-04-Rectificative-formatCII.xml",
            //"xml/ppf/UC4-01-FactureCII.xml",
            "xml/ppf/UC5-01-Autofacturation-formatCII.xml",
            "xml/ppf/UC5-04-Avoir-formatCII.xml",
            "xml/ppf/UC6-01-Acompte-formatCII.xml",
            "xml/ppf/UC11-02-Facture-formatCII.xml",
            "xml/unece/valid1-formatCII.xml",
            "xml/unece/valid2-formatCII.xml",
            "xml/unece/valid3-formatCII.xml",
            "xml/unece/valid4-formatCII.xml"
    );

    @Autowired
    private InvoiceReader<CrossIndustryInvoiceType> reader;
    @Autowired
    private InvoiceWriter<CrossIndustryInvoiceType> writer;
    @Autowired
    private InvoiceValidator<CrossIndustryInvoiceType> validator;
    @Autowired
    private TestUtil util;

    @Test
    public void testReaderWriter() throws FileNotFoundException, InterruptedException {
        final Map<Future<Boolean>, Boolean> futures = new LinkedHashMap<>();
        // ADD valid invoices
        for (var file : VALID_FILES) {
            var xmlFile = loadResource(file);
            var future = EXECUTOR_SERVICE.submit(new ReaderWriterCallable(xmlFile));
            futures.put(future, true);
        }
        // ADD invalid invoices
        for (var xmlFile : Objects.requireNonNull(XML_DIR.listFiles())) {
            var future = EXECUTOR_SERVICE.submit(new ReaderWriterCallable(xmlFile));
            futures.put(future, false);
        }
        // WAIT and TEST results
        for (var future : futures.entrySet()) {
            try {
                var result = future.getKey().get();
                assertEquals(future.getValue(), result);
            } catch (ExecutionException ex) {
                assertFalse(future.getValue());
                assertNotNull(ex.getCause());
                assertInstanceOf(XValidationException.class, ex.getCause());
            }
        }
    }

    @Test
    public void testReaderValidator() throws FileNotFoundException, InterruptedException, ExecutionException {
        final List<Future<Boolean>> futures = new LinkedList<>();
        // ADD valid invoices
        for (var file : VALID_FILES) {
            var xmlFile = loadResource(file);
            var future = EXECUTOR_SERVICE.submit(new ReaderValidatorCallable(xmlFile));
            futures.add(future);
        }
        // ADD invalid invoices
        for (var xmlFile : Objects.requireNonNull(XML_DIR.listFiles())) {
            var future = EXECUTOR_SERVICE.submit(new ReaderValidatorCallable(xmlFile));
            futures.add(future);
        }
        // WAIT and TEST validation results
        for (var future : futures) {
            var result = future.get();
            assertTrue(result);
        }
    }

    @Test
    public void testValidator() throws FileNotFoundException, InterruptedException, ExecutionException {
        final Map<Future<Integer>, Integer> futures = new LinkedHashMap<>();
        // ADD valid invoices
        for (var file : VALID_FILES) {
            var xmlFile = loadResource(file);
            var future = EXECUTOR_SERVICE.submit(new ValidatorCallable(xmlFile));
            futures.put(future, 0);
        }
        // ADD invalid invoices
        for (var xmlFile : Objects.requireNonNull(XML_DIR.listFiles())) {
            var future = EXECUTOR_SERVICE.submit(new ValidatorCallable(xmlFile));
            var nbErrors = 1;
            if ("invalid_allowance_charge_reason.xml".equals(xmlFile.getName())) {
                nbErrors = 3;
            }
            futures.put(future, nbErrors);
        }
        // WAIT and TEST validation errors
        for (var future : futures.entrySet()) {
            var result = future.getKey().get();
            assertEquals(future.getValue(), result);
        }
    }

    private class ReaderWriterCallable implements Callable<Boolean> {

        private final File xmlPath;

        public ReaderWriterCallable(File xmlPath) {
            this.xmlPath = xmlPath;
        }

        @Override
        public Boolean call() throws Exception {
            util.assertInvoice(xmlPath, validator, reader, writer);
            return true;
        }
    }

    private class ReaderValidatorCallable implements Callable<Boolean> {

        private final File xmlPath;

        public ReaderValidatorCallable(File xmlPath) {
            this.xmlPath = xmlPath;
        }

        @Override
        public Boolean call() throws Exception {
            assertInvoice(xmlPath, validator, reader);
            return true;
        }
    }

    private class ValidatorCallable implements Callable<Integer> {

        private final File xmlPath;

        public ValidatorCallable(File xmlPath) {
            this.xmlPath = xmlPath;
        }

        @Override
        public Integer call() throws Exception {
            return validator.validate(xmlPath).size();
        }
    }

}

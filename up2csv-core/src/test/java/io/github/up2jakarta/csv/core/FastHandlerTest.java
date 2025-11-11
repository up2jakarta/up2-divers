package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.hdl.FastHandler;
import io.github.up2jakarta.csv.core.misc.DummyException;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyConverter;
import io.github.up2jakarta.csv.core.misc.clv.MeasurementUnitConverter;
import io.github.up2jakarta.csv.core.misc.cvr.Test1Converter;
import io.github.up2jakarta.csv.core.misc.cvr.Test1Resolver;
import io.github.up2jakarta.csv.core.misc.cvr.Test1Validator;
import io.github.up2jakarta.csv.core.misc.ext.Dummy1Processor;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.core.misc.map.ValidBean;
import io.github.up2jakarta.csv.core.misc.prc.Test3Processor;
import io.github.up2jakarta.csv.core.misc.vld.Up2Warn;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeParseException;

import static io.github.up2jakarta.csv.api.IEvent.*;
import static io.github.up2jakarta.csv.core.Up2ErrorTests.EX_CAUSE;
import static io.github.up2jakarta.csv.fmt.misc.Tests.ERROR_CODE;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static io.github.up2jakarta.csv.impl.GroupType.NONE;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class FastHandlerTest {

    private final Up2Factory<GroupType> factory;

    @Autowired
    FastHandlerTest(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testNull() throws BeanException {
        // Given
        final Up2Mapper<ValidBean, GroupType> mapper = factory.build(ValidBean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final EventHandler<GroupType> handler = null;
        final NullPointerException npe1 = assertThrows(NullPointerException.class, () -> mapper.map(row, null));
        final NullPointerException npe2 = assertThrows(NullPointerException.class, () -> mapper.map(handler, ""));
        // Then
        assertEquals("handler is required", npe1.getMessage());
        assertEquals("handler is required", npe2.getMessage());
    }

    /**
     * @see ErrorSupportTest#testValidatorWithoutError()
     */
    @Test
    void testValidator() throws BeanException {
        // Given
        final Up2Mapper<Test1Validator, GroupType> mapper = factory.build(Test1Validator.class, factory.resolver.or(NONE));
        final EventHandler<GroupType> handler = FastHandler.of(WARNING);
        {
            // When
            final String[] row = new String[]{"+1", "1", "1", "1", "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("size must be between 0 and 1", error.getMessage());
            assertEquals(NONE, error.getType());
        }
        {
            // When
            final String[] row = new String[]{"1", "101", "1", "1", "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must be less than or equal to 100", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "", "1", "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "1", null, "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be null", error.getMessage());
            assertNull(error.getCause());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "1", "1", "-1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must be greater than 0", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "1", "1", "1", "", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_CODE, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "1", "1", "1", "1", ""};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
    }

    /**
     * @see ErrorSupportTest#testResolverWithoutError()
     */
    @Test
    void testResolver() throws BeanException {
        // Given
        final Up2Mapper<Test1Resolver, GroupType> mapper = factory.build(Test1Resolver.class);
        final EventHandler<GroupType> handler = FastHandler.of(WARNING);
        {
            // When
            final String[] row = new String[]{"ISL", "KGM", "PT24H"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_CODE_LIST, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [ISL] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"TND", "XGM", "PT24H"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [XGM] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"TND", "KGM", "XPT24H"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_CONVERTER, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(DateTimeParseException.class, error.getCause());
            assertNull(error.getCause().getCause());
            assertEquals("Text cannot be parsed to a Duration", error.getCause().getMessage());
        }
    }

    /**
     * @see ErrorSupportTest#testConverterWithoutError()
     */
    @Test
    void testConverter() throws BeanException {
        // Given
        final Up2Mapper<Test1Converter, GroupType> mapper = factory.build(Test1Converter.class);
        final EventHandler<GroupType> handler = FastHandler.of(WARNING);
        {
            final String[] row = new String[]{"ILS", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"TND", "int"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(DummyConverter.TU_P_005, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("For input string: \"int\"", error.getCause().getMessage());
            assertNotNull(error.getCause().getCause());
        }
    }

    /**
     * @see ErrorSupportTest#testProcessorWithoutError()
     */
    @Test
    void testProcessor() throws BeanException {
        // Given
        final Up2Mapper<Test3Processor, GroupType> mapper = factory.build(Test3Processor.class);
        final EventHandler<GroupType> handler = FastHandler.of(WARNING);
        {
            final String[] row = new String[]{"property"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("property message", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"dummy"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertInstanceOf(DummyException.class, error.getCause());
            assertEquals(EX_CAUSE + ": dummy message", error.getMessage());
            assertNotNull(error.getCause());
        }
    }

}

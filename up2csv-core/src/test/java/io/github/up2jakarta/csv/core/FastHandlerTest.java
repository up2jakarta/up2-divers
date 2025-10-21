package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
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
import io.github.up2jakarta.csv.fmt.hdl.FastException;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.PropertyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeParseException;

import static io.github.up2jakarta.csv.core.EventHandler.*;
import static io.github.up2jakarta.csv.core.Up2ErrorTests.DUMMY;
import static io.github.up2jakarta.csv.fmt.misc.Tests.ERROR_CODE;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
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
        final EventHandler<InputRecord, GroupType, InputError> handler = null;
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
        final Up2Mapper<Test1Validator, GroupType> mapper = factory.build(Test1Validator.class, GroupType.NONE);
        final EventHandler<InputRecord, GroupType, InputError> handler = FastHandler.of(WARNING);
        {
            // When
            final InputRecord row = record(SegmentType.S00, "+1", "1", "1", "1", "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("size must be between 0 and 1", error.getMessage());
            assertEquals(GroupType.NONE, error.getDataType());
        }
        {
            // When
            final InputRecord row = record(SegmentType.S00, "1", "101", "1", "1", "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must be less than or equal to 100", error.getMessage());
        }
        {
            // When
            final InputRecord row = record(SegmentType.S00, "1", "1", "", "1", "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final InputRecord row = record(SegmentType.S00, "1", "1", "1", null, "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be null", error.getMessage());
            assertNull(error.getCause());
        }
        {
            // When
            final InputRecord row = record(SegmentType.S00, "1", "1", "1", "1", "-1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must be greater than 0", error.getMessage());
        }
        {
            // When
            final InputRecord row = record(SegmentType.S00, "1", "1", "1", "1", "1", "", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_CODE, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final InputRecord row = record(SegmentType.S00, "1", "1", "1", "1", "1", "1", "");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
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
        final EventHandler<InputRecord, GroupType, InputError> handler = FastHandler.of(WARNING);
        {
            // When
            final InputRecord row = record(SegmentType.S00, "ISL", "KGM", "PT24H");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_CODE_LIST, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [ISL] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRecord row = record(SegmentType.S00, "TND", "XGM", "PT24H");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [XGM] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRecord row = record(SegmentType.S00, "TND", "KGM", "XPT24H");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
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
        final EventHandler<InputRecord, GroupType, InputError> handler = FastHandler.of(WARNING);
        {
            final InputRecord row = record(SegmentType.S00, "ILS", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRecord row = record(SegmentType.S00, "TND", "int");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(DummyConverter.TU_P_005, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("java.lang.NumberFormatException: For input string: \"int\"", error.getCause().getMessage());
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
        final EventHandler<InputRecord, GroupType, InputError> handler = FastHandler.of(WARNING);
        {
            final InputRecord row = record(SegmentType.S00, "property");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("property", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRecord row = record(SegmentType.S00, "dummy");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals(DUMMY + ": dummy", error.getCause().getMessage());
            assertNotNull(error.getCause().getCause());
        }
    }

}

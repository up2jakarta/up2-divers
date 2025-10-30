package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyConverter;
import io.github.up2jakarta.csv.core.misc.clv.MeasurementUnitConverter;
import io.github.up2jakarta.csv.core.misc.cvr.*;
import io.github.up2jakarta.csv.core.misc.ext.Dummy1Processor;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.core.misc.prc.Test3Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test4Processor;
import io.github.up2jakarta.csv.core.misc.vld.Up2Warn;
import io.github.up2jakarta.csv.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.*;
import static io.github.up2jakarta.csv.fmt.misc.Tests.ERROR_CODE;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ErrorSupportTest {

    private final Up2Factory<GroupType> factory;

    @Autowired
    ErrorSupportTest(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testProcessorWithoutError() throws BeanException {
        // Given
        final Up2Mapper<Test3Processor, GroupType> mapper = factory.build(Test3Processor.class);
        final InputRecord row = record(SegmentType.S00, "property", "dummy");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test3Processor bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals("property message", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(ERROR_PROCESSOR, error.getCode());
            assertEquals("dummy message", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testProcessorWithinError() throws BeanException {
        // Given
        final Up2Mapper<Test4Processor, GroupType> mapper = factory.build(Test4Processor.class);
        final InputRecord row = record(SegmentType.S00, "property", "dummy");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test4Processor bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Test4Processor.TU_P_002, error.getCode());
            assertEquals("property message", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(Test4Processor.TU_P_003, error.getCode());
            assertEquals("dummy message", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testConverterWithoutError() throws BeanException {
        // Given
        final Up2Mapper<Test1Converter, GroupType> mapper = factory.build(Test1Converter.class);
        final InputRecord row = record(SegmentType.S00, "ILS", "int");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test1Converter bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(DummyConverter.TU_P_005, error.getCode());
            assertEquals("java.lang.NumberFormatException: For input string: \"int\"", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testConverterWithinError() throws BeanException {
        // Given
        final Up2Mapper<Test2Converter, GroupType> mapper = factory.build(Test2Converter.class);
        final InputRecord row = record(SegmentType.S00, "ILS", "int");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test2Converter bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Test2Converter.TU_P_004, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(Test2Converter.TU_P_006, error.getCode());
            assertEquals("java.lang.NumberFormatException: For input string: \"int\"", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testResolverWithoutError() throws BeanException {
        // Given
        final Up2Mapper<Test1Resolver, GroupType> mapper = factory.build(Test1Resolver.class);
        final InputRecord row = record(SegmentType.S00, "ISL", "XGM", "XPT24H");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test1Resolver bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(3, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(ERROR_CODE_LIST, error.getCode());
            assertEquals("Unknown value [ISL] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getCode());
            assertEquals("Unknown value [XGM] for CodeList[MeasurementUnitCode]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(2);
            assertSame(row, error.getKey().getRecord());
            assertEquals(2, error.getKey().getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(ERROR_CONVERTER, error.getCode());
            assertEquals("Text cannot be parsed to a Duration", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testResolverWithinError() throws BeanException {
        // Given
        final Up2Mapper<Test2Resolver, GroupType> mapper = factory.build(Test2Resolver.class);
        final InputRecord row = record(SegmentType.S00, "date", "duration");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test2Resolver bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(Test2Resolver.TU_P_007, error.getCode());
            assertEquals("Unknown value [date] for CodeList[MeasurementUnitCode]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Test2Resolver.TU_P_008, error.getCode());
            assertEquals("java.time.format.DateTimeParseException: Text cannot be parsed to a Duration", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testValidatorWithoutError() throws BeanException {
        // Given
        final Up2Mapper<Test1Validator, GroupType> mapper = factory.build(Test1Validator.class);
        final InputRecord row = record(SegmentType.S00, "+1", "101", "", null, "-1");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test1Validator bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        errors.sort(Comparator.comparingInt(InputError::getOffset));
        assertEquals(7, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getOffset());
            assertEquals(ERROR, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("size must be between 0 and 1", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("must be less than or equal to 100", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(2);
            assertSame(row, error.getKey().getRecord());
            assertEquals(2, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(3);
            assertSame(row, error.getKey().getRecord());
            assertEquals(3, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("must not be null", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(4);
            assertSame(row, error.getKey().getRecord());
            assertEquals(4, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals("must be greater than 0", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(5);
            assertSame(row, error.getKey().getRecord());
            assertEquals(5, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(ERROR_CODE, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(6);
            assertSame(row, error.getKey().getRecord());
            assertEquals(6, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testValidatorWithinError() throws BeanException {
        // Given
        final Up2Mapper<Test2Validator, GroupType> mapper = factory.build(Test2Validator.class);
        final InputRecord row = record(SegmentType.S00, "", null);
        // When
        final InputCollector handler = new InputCollector(row);
        final Test2Validator bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        errors.sort(Comparator.comparingInt(InputError::getOffset));
        assertNotNull(bean);
        assertEquals(3, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Test2Validator.TU_P_009, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Test2Validator.TU_P_010, error.getCode());
            assertEquals("must not be null", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(2);
            assertSame(row, error.getKey().getRecord());
            assertEquals(2, error.getOffset());
            assertEquals(WARNING, error.getSeverity());
            assertEquals(Test2Validator.TU_P_021, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
    }

}

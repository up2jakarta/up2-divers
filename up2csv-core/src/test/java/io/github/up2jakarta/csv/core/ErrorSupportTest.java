package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.converter.*;
import io.github.up2jakarta.csv.test.bean.processor.Test3Processor;
import io.github.up2jakarta.csv.test.bean.processor.Test4Processor;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.codelist.MeasurementUnitConverter;
import io.github.up2jakarta.csv.test.ext.Dummy1Processor;
import io.github.up2jakarta.csv.test.ext.DummyConverter;
import io.github.up2jakarta.csv.test.validation.Up2Warn;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Comparator;
import java.util.List;

import static io.github.up2jakarta.csv.misc.Errors.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ErrorSupportTest {

    private final SimpleCreator creator;
    private final MapperFactory<DataId> factory;

    @Autowired
    ErrorSupportTest(MapperFactory<DataId> factory, SimpleCreator creator) {
        this.factory = factory;
        this.creator = creator;
    }

    @Test
    void testProcessorWithoutError() throws BeanException {
        // Given
        final Mapper<Test3Processor, DataId> mapper = factory.build(Test3Processor.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "property", "dummy");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test3Processor bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals("property", error.getMessage());
            assertNotNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(ERROR_PROCESSOR, error.getCode());
            assertEquals("io.github.up2jakarta.csv.test.ext.DummyException: dummy", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testProcessorWithinError() throws BeanException {
        // Given
        final Mapper<Test4Processor, DataId> mapper = factory.build(Test4Processor.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "property", "dummy");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test4Processor bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test4Processor.TU_P_002, error.getCode());
            assertEquals("property", error.getMessage());
            assertNotNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Test4Processor.TU_P_003, error.getCode());
            assertEquals("io.github.up2jakarta.csv.test.ext.DummyException: dummy", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testConverterWithoutError() throws BeanException {
        // Given
        final Mapper<Test1Converter, DataId> mapper = factory.build(Test1Converter.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "ILS", "int");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test1Converter bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(DummyConverter.TU_P_005, error.getCode());
            assertEquals("java.lang.NumberFormatException: For input string: \"int\"", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testConverterWithinError() throws BeanException {
        // Given
        final Mapper<Test2Converter, DataId> mapper = factory.build(Test2Converter.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "ILS", "int");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test2Converter bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Converter.TU_P_004, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Test2Converter.TU_P_006, error.getCode());
            assertEquals("java.lang.NumberFormatException: For input string: \"int\"", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testResolverWithoutError() throws BeanException {
        // Given
        final Mapper<Test1Resolver, DataId> mapper = factory.build(Test1Resolver.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "ISL", "XGM", "XPT24H");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test1Resolver bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(3, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(ERROR_CODE_LIST, error.getCode());
            assertEquals("Unknown value [ISL] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getCode());
            assertEquals("Unknown value [XGM] for CodeList[MeasurementUnitCode]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(2);
            assertSame(row, error.getRow());
            assertEquals(2, error.getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(ERROR_CONVERTER, error.getCode());
            assertEquals("Text cannot be parsed to a Duration", error.getMessage());
            assertNotNull(error.getTrace());
        }
    }

    @Test
    void testResolverWithinError() throws BeanException {
        // Given
        final Mapper<Test2Resolver, DataId> mapper = factory.build(Test2Resolver.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "date", "duration");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test2Resolver bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Test2Resolver.TU_P_007, error.getCode());
            assertEquals("Unknown value [date] for CodeList[MeasurementUnitCode]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Resolver.TU_P_008, error.getCode());
            assertEquals("java.time.format.DateTimeParseException: Text cannot be parsed to a Duration", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testValidatorWithoutError() throws BeanException {
        // Given
        final Mapper<Test1Validator, DataId> mapper = factory.build(Test1Validator.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "+1", "101", "", null, "-1");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test1Validator bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        errors.sort(Comparator.comparingInt(SimpleErrorEntity::getOffset));
        assertEquals(7, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("size must be between 0 and 1", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("must be less than or equal to 100", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(2);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(3);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(3, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("must not be null", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(4);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(4, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals("must be greater than 0", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(5);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(5, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Tests.TU_V_001, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(6);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(6, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testValidatorWithinError() throws BeanException {
        // Given
        final Mapper<Test2Validator, DataId> mapper = factory.build(Test2Validator.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "", null);
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test2Validator bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        errors.sort(Comparator.comparingInt(SimpleErrorEntity::getOffset));
        assertNotNull(bean);
        assertEquals(3, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Test2Validator.TU_P_009, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Validator.TU_P_010, error.getCode());
            assertEquals("must not be null", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(2);
            assertSame(row, error.getRow());
            assertNotNull(error.getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(Test2Validator.TU_P_021, error.getCode());
            assertEquals("must not be empty", error.getMessage());
            assertNull(error.getTrace());
        }
    }

}

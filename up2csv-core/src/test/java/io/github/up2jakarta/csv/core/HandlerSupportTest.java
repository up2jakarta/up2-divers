package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.impl.DataId;
import io.github.up2jakarta.csv.impl.InputErrorEntity;
import io.github.up2jakarta.csv.impl.InputRowEntity;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.MapperException;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.converter.Test1Converter;
import io.github.up2jakarta.csv.test.bean.converter.Test1Resolver;
import io.github.up2jakarta.csv.test.bean.converter.Test1Validator;
import io.github.up2jakarta.csv.test.bean.mapper.ValidBean;
import io.github.up2jakarta.csv.test.bean.processor.Test3Processor;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.codelist.MeasurementUnitConverter;
import io.github.up2jakarta.csv.test.ext.Dummy1Processor;
import io.github.up2jakarta.csv.test.ext.DummyConverter;
import io.github.up2jakarta.csv.test.validation.Up2Warn;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.CodeListException;
import io.github.up2jakarta.xml.codelist.PropertyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.misc.Errors.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class HandlerSupportTest {

    private final MapperFactory<DataId> factory;

    @Autowired
    HandlerSupportTest(MapperFactory<DataId> factory) {
        this.factory = factory;
    }

    @Test
    void testNull() throws BeanException {
        // Given
        final Mapper<ValidBean, DataId> mapper = factory.build(ValidBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "");
        // When
        final EventHandler<InputRowEntity, ?, DataId, InputErrorEntity> handler = null;
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
        final Mapper<Test1Validator, DataId> mapper = factory.build(Test1Validator.class);
        final EventHandler<InputRowEntity, ?, DataId, InputErrorEntity> handler = EventHandler.failFast(false);
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "+1", "1", "1", "1", "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("size must be between 0 and 1", error.getCause().getMessage());
            assertEquals(DataId.NONE, error.getDataType());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "101", "1", "1", "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must be less than or equal to 100", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "", "1", "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be empty", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", null, "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be null", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "-1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must be greater than 0", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "1", "", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Tests.ERROR_CODE, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be empty", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "1", "1", "");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be empty", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
    }

    /**
     * @see ErrorSupportTest#testResolverWithoutError()
     */
    @Test
    void testResolver() throws BeanException {
        // Given
        final Mapper<Test1Resolver, DataId> mapper = factory.build(Test1Resolver.class);
        final EventHandler<InputRowEntity, ?, DataId, InputErrorEntity> handler = EventHandler.failFast(false);
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "ISL", "KGM", "PT24H");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_CODE_LIST, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [ISL] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "XGM", "PT24H");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [XGM] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "KGM", "XPT24H");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_CONVERTER, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertNotNull(error.getCause().getCause());
            assertEquals("Text cannot be parsed to a Duration", error.getCause().getCause().getMessage());
        }
    }

    /**
     * @see ErrorSupportTest#testConverterWithoutError()
     */
    @Test
    void testConverter() throws BeanException {
        // Given
        final Mapper<Test1Converter, DataId> mapper = factory.build(Test1Converter.class);
        final EventHandler<InputRowEntity, ?, DataId, InputErrorEntity> handler = EventHandler.failFast(false);
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "ILS", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(CurrencyConverter.ISO_4217, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "int");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(DummyConverter.TU_P_005, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
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
        final Mapper<Test3Processor, DataId> mapper = factory.build(Test3Processor.class);
        final EventHandler<InputRowEntity, ?, DataId, InputErrorEntity> handler = EventHandler.failFast(false);
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "property");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("property", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "dummy");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("io.github.up2jakarta.csv.test.ext.DummyException: dummy", error.getCause().getMessage());
            assertNotNull(error.getCause().getCause());
        }
    }

}

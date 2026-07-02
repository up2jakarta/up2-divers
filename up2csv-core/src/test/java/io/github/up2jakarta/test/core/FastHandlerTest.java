package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.hdl.FastHandler;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.DummyException;
import io.github.up2jakarta.test.core.misc.cvr.Test1Converter;
import io.github.up2jakarta.test.core.misc.cvr.Test1Resolver;
import io.github.up2jakarta.test.core.misc.cvr.Test1Validator;
import io.github.up2jakarta.test.core.misc.ext.Dummy1Processor;
import io.github.up2jakarta.test.core.misc.ext.DummyConverter;
import io.github.up2jakarta.test.core.misc.lov.CurrencyConverter;
import io.github.up2jakarta.test.core.misc.lov.MeasurementUnitConverter;
import io.github.up2jakarta.test.core.misc.map.ValidBean;
import io.github.up2jakarta.test.core.misc.prc.Test3Processor;
import io.github.up2jakarta.test.core.misc.vld.Up2Warn;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeParseException;

import static io.github.up2jakarta.csv.api.IEvent.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.core.Up2ErrorTests.EX_CAUSE;
import static io.github.up2jakarta.test.fmt.misc.Tests.ERROR_CODE;
import static io.github.up2jakarta.test.fmt.misc.Tests.record;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class FastHandlerTest {

    private final Up2Factory<TermType> factory;

    @Autowired
    FastHandlerTest(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    @Test
    void testNull() throws BeanException {
        // Given
        final Up2Mapper<ValidBean, TermType> mapper = factory.mapper(ValidBean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final EventHandler<TermType> handler = null;
        final AccessException npe1 = assertThrows(AccessException.class, () -> mapper.map(row, null));
        final AccessException npe2 = assertThrows(AccessException.class, () -> mapper.map(handler, ""));
        // Then
        assertEquals("Up2Mapper[handler] must not be null", npe1.getLocalizedMessage());
        assertEquals("Up2Mapper[handler] must not be null", npe2.getLocalizedMessage());
    }

    /**
     * @see ErrorSupportTest#testValidatorWithoutError()
     */
    @Test
    void testValidator() throws BeanException {
        // Given
        final Up2Mapper<Test1Validator, TermType> mapper = factory.mapper(Test1Validator.class);
        final EventHandler<TermType> handler = FastHandler.of(WARNING);
        {
            // When
            final String[] row = new String[]{"+1", "1", "1", "1", "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(ERROR, error.getLevel());
            assertNull(error.getCause());
            assertEquals("size must be between 0 and 1", error.getMessage());
            assertNull(error.getType());
        }
        {
            // When
            final String[] row = new String[]{"1", "101", "1", "1", "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(WARNING, error.getLevel());
            assertNull(error.getCause());
            assertEquals("must be less than or equal to 100", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "", "1", "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(WARNING, error.getLevel());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "1", null, "1", "1", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(WARNING, error.getLevel());
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
            assertEquals(WARNING, error.getLevel());
            assertNull(error.getCause());
            assertEquals("must be greater than 0", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "1", "1", "1", "", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(ERROR_CODE, error.getCode());
            assertEquals(WARNING, error.getLevel());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final String[] row = new String[]{"1", "1", "1", "1", "1", "1", ""};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals(WARNING, error.getLevel());
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
        final Up2Mapper<Test1Resolver, TermType> mapper = factory.mapper(Test1Resolver.class);
        final EventHandler<TermType> handler = FastHandler.of(WARNING);
        {
            // When
            final String[] row = new String[]{"ISL", "KGM", "PT24H"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(EC_CODE_LIST, error.getCode());
            assertEquals(ERROR, error.getLevel());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown input [ISL] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"TND", "XGM", "PT24H"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getCode());
            assertEquals(ERROR, error.getLevel());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown input [XGM] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"TND", "KGM", "XPT24H"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(EC_CONVERTER, error.getCode());
            assertEquals(ERROR, error.getLevel());
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
        final Up2Mapper<Test1Converter, TermType> mapper = factory.mapper(Test1Converter.class);
        final EventHandler<TermType> handler = FastHandler.of(WARNING);
        {
            final String[] row = new String[]{"ILS", "1"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals(ERROR, error.getLevel());
            assertInstanceOf(TypeException.class, error.getCause());
            assertEquals("Unknown input [ILS] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"TND", "int"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(DummyConverter.TU_P_005, error.getCode());
            assertEquals(ERROR, error.getLevel());
            assertInstanceOf(TypeException.class, error.getCause());
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
        final Up2Mapper<Test3Processor, TermType> mapper = factory.mapper(Test3Processor.class);
        final EventHandler<TermType> handler = FastHandler.of(WARNING);
        {
            final String[] row = new String[]{"property"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getLevel());
            assertInstanceOf(TypeException.class, error.getCause());
            assertEquals("property message", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final String[] row = new String[]{"dummy"};
            final FailureException error = assertThrows(FailureException.class, () -> mapper.map(handler, row));
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getLevel());
            assertInstanceOf(DummyException.class, error.getCause());
            assertEquals(EX_CAUSE + ": dummy message", error.getMessage());
            assertNotNull(error.getCause());
        }
    }

}

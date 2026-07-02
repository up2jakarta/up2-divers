package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.cvr.*;
import io.github.up2jakarta.test.core.misc.lov.CountryCodeType;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.test.core.misc.lov.MeasurementUnitCode;
import io.github.up2jakarta.test.core.misc.lov.Test4CodeList;
import io.github.up2jakarta.test.impl.InputCollector;
import io.github.up2jakarta.test.impl.InputError;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.SegmentType.S00;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2ConverterTests {

    private final Up2Factory<TermType> factory;

    @Autowired
    Up2ConverterTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    @Test
    void testSupport() throws BeanException {
        // GIVEN
        final String[] data = {"100", "Test\t 100", "2024-07-25", "57.000001", "TND", "4.06250001", "C62", "Y", "P1W", "TN"};
        final Up2Mapper<SupportEntity, TermType> parser = factory.mapper(SupportEntity.class);
        final InputRecord row = new InputRecord(null, S00, null, data);
        final InputCollector handler = new InputCollector(row);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        assertEquals(0, handler.toList().size());
        // THEN
        assertEquals(100, entity.getKey());
        assertEquals("Test 100", entity.getReference());
        assertEquals(LocalDate.of(2024, 7, 25), entity.getDate());
        assertEquals(new BigDecimal("57.00"), entity.getAmount());
        assertEquals(CurrencyCodeType.TND, entity.getCurrency());
        assertEquals(new BigDecimal("4.0625"), entity.getQuantity());
        assertEquals(MeasurementUnitCode.C62, entity.getUnit());
        assertEquals(true, entity.isValid());
        assertEquals(Period.ofWeeks(1), entity.getShippingPeriod());
        assertEquals(CountryCodeType.TN, entity.getShippingCountry());
    }

    @Test
    void testUp2Date() throws BeanException {
        // Given
        final Up2Mapper<Test4Converter, ?> parser = factory.mapper(Test4Converter.class);
        final Up2Flatter<Test4Converter, ?> format = parser.toFlatter();
        final String[] data = new String[]{"2026-08-21 15:32:49", "2026-08-21", "15:32:49", "2026-08-21 15:32:49.555"};
        // When
        final Test4Converter bean = parser.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void testJSR_303_Validation() throws BeanException {
        // GIVEN
        final String[] data = {"100", "8888_8888", "2024-07-25", "57.000001", "EUR", "4.06250001", "KGM", "Y", "P1W", "FR"};
        final Up2Mapper<SupportEntity, TermType> parser = factory.mapper(SupportEntity.class);
        final InputRecord row = new InputRecord(null, S00, null, data);
        final InputCollector handler = new InputCollector(row);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        final List<InputError> errors = handler.toList();
        assertEquals(1, errors.size());
        // THEN
        assertEquals(100, entity.getKey());
        assertEquals("8888_8888", entity.getReference());
        assertEquals(LocalDate.of(2024, 7, 25), entity.getDate());
        assertEquals(new BigDecimal("57.00"), entity.getAmount());
        assertEquals(CurrencyCodeType.EUR, entity.getCurrency());
        assertEquals(new BigDecimal("4.0625"), entity.getQuantity());
        assertEquals(MeasurementUnitCode.KGM, entity.getUnit());
        assertEquals(true, entity.isValid());
        assertEquals(Period.ofWeeks(1), entity.getShippingPeriod());
        assertEquals(CountryCodeType.FR, entity.getShippingCountry());
        // Error
        final InputError error = errors.getFirst();
        assertEquals(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(WARNING, error.getLevel());
        assertEquals(EC_COMPLIANCE, error.getCode());
        assertEquals(1 + 1, error.getOffset());
        assertEquals("size must be between 0 and 8", error.getMessage());
        assertNull(error.getTrace());
    }

    @Test
    void testError() throws BeanException {
        // GIVEN
        final String[] data = {"100", "99998888", "2024-07-25", "57.000001", "ILS", "4.06250001", "KGM", "Y", "P1W", "IL"};
        final Up2Mapper<SupportEntity, TermType> parser = factory.mapper(SupportEntity.class);
        final InputRecord row = new InputRecord(null, S00, null, data);
        final InputCollector handler = new InputCollector(row);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        final List<InputError> errors = handler.toList();
        assertEquals(2, errors.size());
        // THEN
        assertEquals(100, entity.getKey());
        assertEquals("99998888", entity.getReference());
        assertEquals(LocalDate.of(2024, 7, 25), entity.getDate());
        assertEquals(new BigDecimal("57.00"), entity.getAmount());
        assertNull(entity.getCurrency());
        assertEquals(new BigDecimal("4.0625"), entity.getQuantity());
        assertEquals(MeasurementUnitCode.KGM, entity.getUnit());
        assertEquals(true, entity.isValid());
        assertEquals(Period.ofWeeks(1), entity.getShippingPeriod());
        assertNull(entity.getShippingCountry());
        // Error Currency
        {
            final InputError error = errors.getFirst();
            assertEquals(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(ERROR, error.getLevel());
            assertEquals(SupportEntity.ISO_4217, error.getCode());
            assertEquals(1 + 4, error.getOffset());
            assertEquals("Unknown input [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        // Error Country
        {
            final InputError error = errors.get(1);
            assertEquals(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(ERROR, error.getLevel());
            assertEquals(SupportEntity.ISO_3166, error.getCode());
            assertEquals(1 + 9, error.getOffset());
            assertEquals("Unknown input [IL] for CodeList[CountryCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }

    }

    @Test
    void testMultipleExtensions() throws BeanException {
        // GIVEN
        final Up2Mapper<Test7Resolver, TermType> mapper = factory.mapper(Test7Resolver.class);
        // WHEN
        final Test7Resolver bean = mapper.map("1");
        // THEN
        assertNotNull(bean);
        assertEquals(BigDecimal.ONE, bean.test);
    }

    // Checking

    @Test
    void testInvalidCodeList1Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(CodeList1Entity.class));
        // THEN
        assertEquals(CodeList1Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("type must implements CodeList<Test1CodeList>", error.getMessage());
    }

    @Test
    void testInvalidCodeList2Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(CodeList2Entity.class));
        // THEN
        assertEquals(CodeList2Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("type must implements CodeList<Test2CodeList>", error.getMessage());
    }

    @Test
    void testInvalidCodeList3Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(CodeList3Entity.class));
        // THEN
        assertEquals(CodeList3Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("type must implements CodeList<Test3CodeList>", error.getMessage());
    }

    @Test
    void testInvalidCodeList4Entity() {
        // GIVEN
        final AccessException error = assertThrows(AccessException.class, () -> factory.mapper(CodeList4Entity.class));
        // THEN
        assertEquals(Test4CodeList.class, error.getSource());
        assertEquals("*", error.getLocator());
        assertEquals("must be unique", error.getMessage());
        assertEquals("Test4CodeList[*] must be unique", error.getLocalizedMessage());
    }

    @Test
    void testDefaultValue() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(DefaultBean.class));
        // THEN
        assertEquals(DefaultBean.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Position[defaultValue] cannot be parsed", error.getMessage());
    }

    @Test
    void testConverterArgument() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test3Converter.class));
        final String cn = CurrencyCodeType.class.getTypeName();
        // THEN
        assertEquals(Test3Converter.class, error.getSource());
        assertEquals("test", error.getLocator());
        assertEquals("@Position[converter] does not support class " + cn, error.getMessage());
    }

    @Test
    void testResolver1Support() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test4Resolver.class));
        // THEN
        assertEquals(Test4Resolver.class, error.getSource());
        assertEquals("test", error.getLocator());
        assertEquals("must not be annotated with @Up2CodeList", error.getMessage());
    }

    @Test
    void testResolver2Support() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test5Resolver.class));
        // THEN
        assertEquals(Test5Resolver.class, error.getSource());
        assertEquals("test", error.getLocator());
        assertEquals("must not be annotated with @Up2Decimal", error.getMessage());
    }

    @Test
    void testResolver3Support() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test6Resolver.class));
        // THEN
        assertEquals(Test6Resolver.class, error.getSource());
        assertEquals("test", error.getLocator());
        assertEquals("must be annotated with one and only one of shortcuts: Up2Number, Up2Dummy", error.getMessage());
    }

    @Test
    void testResolver4Support() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test8Resolver.class));
        // THEN
        assertEquals(Test8Resolver.class, error.getSource());
        assertEquals("test", error.getLocator());
        assertEquals("must not be annotated with @Enumerated", error.getMessage());
    }

    @Test
    void testResolver5Support() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test9Resolver.class));
        // THEN
        assertEquals(Test9Resolver.class, error.getSource());
        assertEquals("test", error.getLocator());
        assertEquals("type must not be annotated with @XmlEnum", error.getMessage());
    }

}

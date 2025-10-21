package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.misc.clv.CountryCodeType;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.clv.MeasurementUnitCode;
import io.github.up2jakarta.csv.core.misc.cvr.*;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputCollector;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_VALIDATOR;
import static io.github.up2jakarta.csv.impl.SegmentType.S00;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2ConverterTests {

    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2ConverterTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testCache() throws BeanException {
        // GIVEN
        final Up2Mapper<ValidEntity, GroupType> instance1 = factory.build(ValidEntity.class);
        final Up2Mapper<ValidEntity, GroupType> instance2 = factory.build(ValidEntity.class);
        // THEN
        assertNotSame(instance1, instance2);
    }

    @Test
    void testSupport() throws BeanException {
        // GIVEN
        final String[] data = {"100", "Test\t 100", "2024-07-25", "57.000001", "TND", "4.06250001", "C62", "Y", "P1W", "TN"};
        final Up2Mapper<SupportEntity, GroupType> parser = factory.build(SupportEntity.class);
        final InputRecord row = new InputRecord(null, S00, null, data);
        final InputCollector handler = new InputCollector(row);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        assertEquals(0, handler.toCollection().size());
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
    void testJSR_303_Validation() throws BeanException {
        // GIVEN
        final String[] data = {"100", "8888_8888", "2024-07-25", "57.000001", "EUR", "4.06250001", "KGM", "Y", "P1W", "FR"};
        final Up2Mapper<SupportEntity, GroupType> parser = factory.build(SupportEntity.class);
        final InputRecord row = new InputRecord(null, S00, null, data);
        final InputCollector handler = new InputCollector(row);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        final Collection<InputError> errors = handler.toCollection();
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
        final InputError error = errors.iterator().next();
        assertEquals(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(WARNING, error.getSeverity());
        assertEquals(ERROR_VALIDATOR, error.getCode());
        assertEquals(1 + 1, error.getOffset());
        assertEquals("size must be between 0 and 8", error.getMessage());
        assertNull(error.getTrace());
    }

    @Test
    void testError() throws BeanException {
        // GIVEN
        final String[] data = {"100", "99998888", "2024-07-25", "57.000001", "ILS", "4.06250001", "KGM", "Y", "P1W", "IL"};
        final Up2Mapper<SupportEntity, GroupType> parser = factory.build(SupportEntity.class);
        final InputRecord row = new InputRecord(null, S00, null, data);
        final InputCollector handler = new InputCollector(row);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
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
            assertEquals(ERROR, error.getSeverity());
            assertEquals("ISO-4217", error.getCode());
            assertEquals(1 + 4, error.getOffset());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        // Error Country
        {
            final InputError error = errors.get(1);
            assertEquals(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(ERROR, error.getSeverity());
            assertEquals("ISO-3166", error.getCode());
            assertEquals(1 + 9, error.getOffset());
            assertEquals("Unknown value [IL] for CodeList[CountryCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }

    }

    // Checking

    @Test
    void testInvalidCodeList1Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(CodeList1Entity.class));
        // THEN
        assertEquals(CodeList1Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("CodeList1Entity[key] - type must implements CodeList<Test1CodeList>", error.getMessage());
    }

    @Test
    void testInvalidCodeList2Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(CodeList2Entity.class));
        // THEN
        assertEquals(CodeList2Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("CodeList2Entity[key] - type must implements CodeList<Test2CodeList>", error.getMessage());
    }

    @Test
    void testInvalidCodeList3Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(CodeList3Entity.class));
        // THEN
        assertEquals(CodeList3Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("CodeList3Entity[key] - type must be enum", error.getMessage());
    }

    @Test
    void testDefaultValue() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(DefaultBean.class));
        // THEN
        assertEquals(DefaultBean.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("DefaultBean[key] - @Up2Default[value] cannot be converted", error.getMessage());
    }

    @Test
    void testConverterArgument() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test3Converter.class));
        // THEN
        assertEquals(Test3Converter.class, error.getSource());
        assertEquals("test", error.getLocator());
        assertEquals("Test3Converter[test] - @Converter[value] does not support CurrencyCodeType", error.getMessage());
    }

}

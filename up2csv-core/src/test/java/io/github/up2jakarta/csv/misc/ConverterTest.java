package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.core.EventHandler.SimpleHandler;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.test.bean.converter.*;
import io.github.up2jakarta.csv.test.codelist.CountryCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.MeasurementUnitCode;
import io.github.up2jakarta.csv.test.persistence.InputRowEntity;
import io.github.up2jakarta.csv.test.persistence.SegmentType;
import io.github.up2jakarta.csv.test.persistence.SimpleErrorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static io.github.up2jakarta.csv.extension.SeverityType.FATAL;
import static io.github.up2jakarta.csv.extension.SeverityType.WARNING;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_VALIDATOR;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class ConverterTest {

    private final InputRepository<InputRowEntity> repository = (r -> 0);
    private final SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> creator;
    private final MapperFactory factory;

    @Autowired
    ConverterTest(MapperFactory factory, SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> creator) {
        this.factory = factory;
        this.creator = creator;
    }

    public static InputRowEntity create(SegmentType type, String[] columns) {
        final InputRowEntity entity = new InputRowEntity();
        entity.setColumns(columns);
        entity.setType(type);
        return entity;
    }

    @Test
    void testCache() throws BeanException {
        // GIVEN
        final Mapper<ValidEntity> instance1 = factory.build(ValidEntity.class);
        final Mapper<ValidEntity> instance2 = factory.build(ValidEntity.class);
        // THEN
        assertNotSame(instance1, instance2);
    }

    @Test
    void testSupport() throws BeanException {
        // GIVEN
        final String[] data = {"100", "Test\t 100", "2024-07-25", "57.000001", "TND", "4.06250001", "C62", "Y", "P1W", "TN"};
        final Mapper<SupportEntity> parser = factory.build(SupportEntity.class);
        final InputRowEntity row = create(SegmentType.S00, data);
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
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
        assertEquals(true, entity.getValid());
        assertEquals(Period.ofWeeks(1), entity.getShippingPeriod());
        assertEquals(CountryCodeType.TN, entity.getShippingCountry());
    }

    @Test
    void testJSR_303_Validation() throws BeanException {
        // GIVEN
        final String[] data = {"100", "8888_8888", "2024-07-25", "57.000001", "EUR", "4.06250001", "KGM", "Y", "P1W", "FR"};
        final Mapper<SupportEntity> parser = factory.build(SupportEntity.class);
        final InputRowEntity row = create(SegmentType.S00, data);
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        final List<SimpleErrorEntity> errors = handler.toList();
        assertEquals(1, errors.size());
        // THEN
        assertEquals(100, entity.getKey());
        assertEquals("8888_8888", entity.getReference());
        assertEquals(LocalDate.of(2024, 7, 25), entity.getDate());
        assertEquals(new BigDecimal("57.00"), entity.getAmount());
        assertEquals(CurrencyCodeType.EUR, entity.getCurrency());
        assertEquals(new BigDecimal("4.0625"), entity.getQuantity());
        assertEquals(MeasurementUnitCode.KGM, entity.getUnit());
        assertEquals(true, entity.getValid());
        assertEquals(Period.ofWeeks(1), entity.getShippingPeriod());
        assertEquals(CountryCodeType.FR, entity.getShippingCountry());
        // Error
        final SimpleErrorEntity error = errors.get(0);
        assertEquals(row, error.getKey().getRow());
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
        final Mapper<SupportEntity> parser = factory.build(SupportEntity.class);
        final InputRowEntity row = create(SegmentType.S00, data);
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
        // WHEN
        final SupportEntity entity = parser.map(row, handler);
        assertNotNull(entity);
        final List<SimpleErrorEntity> errors = handler.toList();
        assertEquals(2, errors.size());
        // THEN
        assertEquals(100, entity.getKey());
        assertEquals("99998888", entity.getReference());
        assertEquals(LocalDate.of(2024, 7, 25), entity.getDate());
        assertEquals(new BigDecimal("57.00"), entity.getAmount());
        assertNull(entity.getCurrency());
        assertEquals(new BigDecimal("4.0625"), entity.getQuantity());
        assertEquals(MeasurementUnitCode.KGM, entity.getUnit());
        assertEquals(true, entity.getValid());
        assertEquals(Period.ofWeeks(1), entity.getShippingPeriod());
        assertNull(entity.getShippingCountry());
        // Error Currency
        {
            final SimpleErrorEntity error = errors.get(0);
            assertEquals(row, error.getKey().getRow());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(FATAL, error.getSeverity());
            assertEquals("ISO-4217", error.getCode());
            assertEquals(1 + 4, error.getOffset());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        // Error Country
        {
            final SimpleErrorEntity error = errors.get(1);
            assertEquals(row, error.getKey().getRow());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(FATAL, error.getSeverity());
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
        assertEquals(CodeList1Entity.class, error.getBeanType());
        assertEquals("key", error.getAttribute());
        assertEquals("CodeList1Entity[key] - type must implements CodeList<Test1CodeList>", error.getMessage());
    }

    @Test
    void testInvalidCodeList2Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(CodeList2Entity.class));
        // THEN
        assertEquals(CodeList2Entity.class, error.getBeanType());
        assertEquals("key", error.getAttribute());
        assertEquals("CodeList2Entity[key] - type must implements CodeList<Test2CodeList>", error.getMessage());
    }

    @Test
    void testInvalidCodeList3Entity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(CodeList3Entity.class));
        // THEN
        assertEquals(CodeList3Entity.class, error.getBeanType());
        assertEquals("key", error.getAttribute());
        assertEquals("CodeList3Entity[key] - type must be enum", error.getMessage());
    }

    @Test
    void testDefaultValue() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(DefaultBean.class));
        // THEN
        assertEquals(DefaultBean.class, error.getBeanType());
        assertEquals("key", error.getAttribute());
        assertEquals("DefaultBean[key] - @Up2Default[value] cannot be converted", error.getMessage());
    }

}

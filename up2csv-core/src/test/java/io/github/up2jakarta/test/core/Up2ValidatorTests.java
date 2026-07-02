package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.vld.*;
import io.github.up2jakarta.test.impl.InputCollector;
import io.github.up2jakarta.test.impl.InputError;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.api.IEvent.EC_CONVERTER;
import static io.github.up2jakarta.lov.SeverityType.*;
import static io.github.up2jakarta.test.core.misc.lov.CurrencyConverter.ISO_4217;
import static io.github.up2jakarta.test.fmt.misc.Tests.record;
import static io.github.up2jakarta.test.impl.SegmentType.S00;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2ValidatorTests {

    private final Up2Factory<TermType> factory;

    @Autowired
    Up2ValidatorTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    @Test
    void testUniqueError() throws BeanException {
        // Given
        final Up2Mapper<Validator3Bean, TermType> mapper = factory.mapper(Validator3Bean.class);
        final InputRecord row = record(S00, ".");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator3Bean bean = mapper.map(row, handler);
        final List<InputError> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputError error = errors.getFirst();
        assertSame(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(ERROR, error.getLevel());
        assertEquals(EC_CONVERTER, error.getCode());
        assertEquals("No digits found.", error.getMessage());
    }

    @Test
    void testValidationGroupsAnnotation() throws BeanException {
        // Given
        final Up2Mapper<ValidatedGroupsBean, TermType> mapper = factory.mapper(ValidatedGroupsBean.class);
        final InputRecord row = record(S00, "\t\n");
        // When
        final InputCollector handler = new InputCollector(row);
        final ValidatedGroupsBean bean = mapper.map(row, handler);
        final List<InputError> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputError error = errors.getFirst();
        assertSame(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(WARNING, error.getLevel());
        assertEquals(EC_COMPLIANCE, error.getCode());
        assertEquals("size must be between 1 and 3", error.getMessage());
    }

    @Test
    void testValidAnnotation() throws BeanException {
        // Given
        final Up2Mapper<Validator1Bean, TermType> mapper = factory.mapper(Validator1Bean.class);
        final InputRecord row = record(S00, "eTND");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator1Bean bean = mapper.map(row, handler);
        final List<InputError> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputError error = errors.getFirst();
        assertSame(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(ERROR, error.getLevel());
        assertEquals(ISO_4217, error.getCode());
        assertEquals("size must be between 0 and 3", error.getMessage());
    }

    @Test
    void testValidOverrideAnnotation() throws BeanException {
        // Given
        final Up2Mapper<Validator2Bean, TermType> mapper = factory.mapper(Validator2Bean.class);
        final InputRecord row = record(S00, "\n\t");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator2Bean bean = mapper.map(row, handler);
        final List<InputError> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        for (final InputError error : errors) {
            assertSame(row, error.getKey().getRecord());
            assertTrue(error.getKey().getOrder() >= 0);
            assertEquals(EC_COMPLIANCE, error.getCode());
            if (error.getLevel() == FATAL) {
                assertEquals("must not be empty", error.getMessage());
            } else {
                assertEquals(WARNING, error.getLevel());
                assertEquals("size must be between 1 and 3", error.getMessage());
            }
        }
    }

    @Test
    void testValid4Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator4Bean, TermType> mapper = factory.mapper(Validator4Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator4Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid5Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator5Bean, TermType> mapper = factory.mapper(Validator5Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator5Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid6Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator6Bean, TermType> mapper = factory.mapper(Validator6Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator6Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid7Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator7Bean, TermType> mapper = factory.mapper(Validator7Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator7Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid8Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator8Bean, TermType> mapper = factory.mapper(Validator8Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator8Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid9Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator9Bean, TermType> mapper = factory.mapper(Validator9Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator9Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid10Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator10Bean, TermType> mapper = factory.mapper(Validator10Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator10Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid11Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator11Bean, TermType> mapper = factory.mapper(Validator11Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator11Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid12Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator12Bean, TermType> mapper = factory.mapper(Validator12Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator12Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid13Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator13Bean, TermType> mapper = factory.mapper(Validator13Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator13Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid14Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator14Bean, TermType> mapper = factory.mapper(Validator14Bean.class);
        final InputRecord row = record(S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator14Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

    @Test
    void testValid15Fragment() throws BeanException {
        // Given
        final Up2Flatter<Validator15Bean, TermType> mapper = factory.flatter(Validator15Bean.class);
        // When
        final List<? extends IComplianceEvent<TermType>> events = mapper.validate(new Validator15Bean());
        // Then
        assertEquals(1, events.size());
        final IComplianceEvent<TermType> event = events.getFirst();
        assertNotNull(event);
        assertEquals(0, event.getOffset());
        assertEquals(WARNING, event.getLevel());
        assertEquals(EC_COMPLIANCE, event.getCode());
    }

    @Test
    void testValid16Fragment() throws BeanException {
        // Given
        final Up2Flatter<Validator16Bean, TermType> mapper = factory.flatter(Validator16Bean.class);
        // When
        final List<? extends IComplianceEvent<TermType>> events = mapper.validate(new Validator16Bean());
        // Then
        assertEquals(1, events.size());
        final IComplianceEvent<TermType> event = events.getFirst();
        assertNotNull(event);
        assertEquals(1, event.getOffset());
        assertEquals(WARNING, event.getLevel());
        assertEquals(EC_COMPLIANCE, event.getCode());
    }

    @Test
    void testValid17Disable() throws BeanException {
        // Given
        final Up2Mapper<Validator17Bean, TermType> mapper = factory.mapper(Validator17Bean.class);
        final InputRecord row = record(S00);
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator17Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        assertEquals(1, evs.getFirst().getOffset());
        assertEquals(1, handler.toList().getFirst().getOffset());
    }

    @Test
    void testValid18DoubleValidation() throws BeanException {
        // Given
        final Up2Mapper<Validator18Bean, TermType> mapper = factory.mapper(Validator18Bean.class);
        final InputRecord row = record(S00);
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator18Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<TermType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(2, evs.size());
        assertEquals(evs.size(), handler.toList().size());
    }

}

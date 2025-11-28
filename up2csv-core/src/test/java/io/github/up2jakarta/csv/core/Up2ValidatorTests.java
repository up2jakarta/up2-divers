package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.core.BSContext.VContext;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.misc.vld.*;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.lov.core.BeanException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.api.IEvent.EC_CONVERTER;
import static io.github.up2jakarta.csv.core.misc.lov.CurrencyConverter.ISO_4217;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static io.github.up2jakarta.lov.SeverityType.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2ValidatorTests {

    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2ValidatorTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testUniqueError() throws BeanException {
        // Given
        final Up2Mapper<Validator3Bean, GroupType> mapper = factory.build(Validator3Bean.class);
        final InputRecord row = record(SegmentType.S00, ".");
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
        final Up2Mapper<ValidatedGroupsBean, GroupType> mapper = factory.build(ValidatedGroupsBean.class);
        final InputRecord row = record(SegmentType.S00, "\t\n");
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
        final Up2Mapper<Validator1Bean, GroupType> mapper = factory.build(Validator1Bean.class);
        final InputRecord row = record(SegmentType.S00, "eTND");
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
        final Up2Mapper<Validator2Bean, GroupType> mapper = factory.build(Validator2Bean.class);
        final InputRecord row = record(SegmentType.S00, "\n\t");
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
        final Up2Mapper<Validator4Bean, GroupType> mapper = factory.build(Validator4Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator4Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        assertFalse(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid5Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator5Bean, GroupType> mapper = factory.build(Validator5Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator5Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertFalse(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid6Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator6Bean, GroupType> mapper = factory.build(Validator6Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator6Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertTrue(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid7Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator7Bean, GroupType> mapper = factory.build(Validator7Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator7Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertTrue(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid8Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator8Bean, GroupType> mapper = factory.build(Validator8Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator8Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertTrue(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid9Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator9Bean, GroupType> mapper = factory.build(Validator9Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator9Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertTrue(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid10Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator10Bean, GroupType> mapper = factory.build(Validator10Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator10Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertTrue(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid11Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator11Bean, GroupType> mapper = factory.build(Validator11Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator11Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertFalse(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid12Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator12Bean, GroupType> mapper = factory.build(Validator12Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator12Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        assertTrue(((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context.enabled);
    }

    @Test
    void testValid13Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator13Bean, GroupType> mapper = factory.build(Validator13Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator13Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        final VContext context = ((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context;
        assertEquals(1, context.groups.length);
        assertTrue(context.enabled);
    }

    @Test
    void testValid14Fragment() throws BeanException {
        // Given
        final Up2Mapper<Validator14Bean, GroupType> mapper = factory.build(Validator14Bean.class);
        final InputRecord row = record(SegmentType.S00, "");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator14Bean bean = mapper.map(row, handler);
        final List<? extends IComplianceEvent<GroupType>> evs = mapper.toFlatter().validate(bean);
        // Then
        assertEquals(1, evs.size());
        assertEquals(evs.size(), handler.toList().size());
        // Context
        final VContext context = ((PFragment<?, ?, ?>) mapper.node.properties.getFirst()).node.context;
        assertEquals(2, mapper.node.context.groups.length);
        assertFalse(context.enabled);
    }

    @Test
    void testValid15Fragment() throws BeanException {
        // Given
        final Up2Flatter<Validator15Bean, GroupType> mapper = factory.format(Validator15Bean.class);
        // When
        final List<? extends IComplianceEvent<GroupType>> events = mapper.validate(new Validator15Bean());
        // Then
        assertEquals(1, events.size());
        final IComplianceEvent<GroupType> event = events.getFirst();
        assertNotNull(event);
        assertEquals(0, event.getOffset());
        assertEquals(WARNING, event.getLevel());
        assertEquals(EC_COMPLIANCE, event.getCode());
    }

    @Test
    void testValid16Fragment() throws BeanException {
        // Given
        final Up2Flatter<Validator16Bean, GroupType> mapper = factory.format(Validator16Bean.class);
        // When
        final List<? extends IComplianceEvent<GroupType>> events = mapper.validate(new Validator16Bean());
        // Then
        assertEquals(1, events.size());
        final IComplianceEvent<GroupType> event = events.getFirst();
        assertNotNull(event);
        assertEquals(1, event.getOffset());
        assertEquals(WARNING, event.getLevel());
        assertEquals(EC_COMPLIANCE, event.getCode());
    }

}

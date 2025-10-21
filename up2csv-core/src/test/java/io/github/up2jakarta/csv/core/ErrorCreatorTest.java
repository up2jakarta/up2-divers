package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyConverter;
import io.github.up2jakarta.csv.core.misc.map.Validator1Bean;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class ErrorCreatorTest {

    private final Up2Factory<GroupType> factory;

    @Autowired
    ErrorCreatorTest(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testOneShot() throws BeanException {
        // Given
        final Up2Mapper<Validator1Bean, GroupType> mapper = factory.build(Validator1Bean.class);
        final InputRecord row = record(SegmentType.S00, "eTND");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator1Bean bean = mapper.map(row, handler);
        final Collection<InputError> errors = handler.toCollection();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputError error = errors.iterator().next();
        assertSame(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(CurrencyConverter.ISO_4217, error.getCode());
        assertEquals("size must be between 0 and 3", error.getMessage());
    }

}
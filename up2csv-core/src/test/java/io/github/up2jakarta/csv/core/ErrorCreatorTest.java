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

import static io.github.up2jakarta.csv.ops.misc.Tests.record;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class ErrorCreatorTest {

    private final SimpleCreator creator;
    private final MapperFactory<GroupType> factory;

    @Autowired
    ErrorCreatorTest(MapperFactory<GroupType> factory, SimpleCreator creator) {
        this.factory = factory;
        this.creator = creator;
    }

    @Test
    void testOneShot() throws BeanException {
        // Given
        final Mapper<Validator1Bean, GroupType> mapper = factory.build(Validator1Bean.class);
        final InputRowEntity row = record(SegmentType.S00, "eTND");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Validator1Bean bean = mapper.map(row, handler);
        final Collection<InputErrorEntity> errors = handler.toCollection();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputErrorEntity error = errors.iterator().next();
        assertSame(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(CurrencyConverter.ISO_4217, error.getCode());
        assertEquals("size must be between 0 and 3", error.getMessage());
    }

}
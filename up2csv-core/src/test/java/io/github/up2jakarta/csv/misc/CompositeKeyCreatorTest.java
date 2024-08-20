package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.mapper.Validator1Bean;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.persistence.InputErrorEntity;
import io.github.up2jakarta.csv.test.persistence.InputErrorEntity.PKey;
import io.github.up2jakarta.csv.test.persistence.InputRowEntity;
import io.github.up2jakarta.csv.test.persistence.SegmentType;
import io.github.up2jakarta.csv.test.persistence.SimpleErrorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class CompositeKeyCreatorTest {

    private final InputRepository<InputRowEntity> repository = (r -> 0);
    private final CompositeKeyCreator<InputRowEntity, PKey, InputErrorEntity> creator;
    private final MapperFactory factory;

    @Autowired
    CompositeKeyCreatorTest(MapperFactory factory, CompositeKeyCreator<InputRowEntity, PKey, InputErrorEntity> creator) {
        this.factory = factory;
        this.creator = creator;
    }

    @Test
    void testOneShot() throws BeanException {
        // Given
        final Mapper<Validator1Bean> mapper = factory.build(Validator1Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "eTND");
        // When
        final EventHandler<InputRowEntity, PKey, InputErrorEntity> handler = EventHandler.of(row, creator, repository);
        final Validator1Bean bean = mapper.map(row, handler);
        final List<InputErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputErrorEntity error = errors.get(0);
        assertSame(row, error.getKey().getRow());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(CurrencyConverter.ISO_4217, error.getCode());
        assertEquals("size must be between 0 and 3", error.getMessage());
    }

    @Test
    void hackCreator() {
        // Given
        final CompositeKeyCreator<?, ?, SimpleErrorEntity> creator = new CompositeKeyCreator<>(SimpleErrorEntity::new, null);
        final SimpleErrorEntity error = creator.newInstance();
        // When
        final IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> error.setKey(error));
        // Then
        assertEquals("I'm the the key", thrown.getMessage());
    }

}
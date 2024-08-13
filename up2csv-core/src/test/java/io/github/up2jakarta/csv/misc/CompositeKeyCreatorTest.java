package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.EventCreator;
import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.mapper.ValidAnnotationBean;
import io.github.up2jakarta.csv.test.persistence.InputErrorEntity;
import io.github.up2jakarta.csv.test.persistence.InputErrorEntity.PKey;
import io.github.up2jakarta.csv.test.persistence.InputRowEntity;
import io.github.up2jakarta.csv.test.persistence.SegmentType;
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

    private final InputRepository<InputRowEntity> repository = r -> 0;
    private final EventCreator<InputRowEntity, PKey, InputErrorEntity> creator = new CompositeKeyCreator<>(InputErrorEntity::new, PKey::new);
    private final MapperFactory factory;

    @Autowired
    CompositeKeyCreatorTest(MapperFactory factory) {
        this.factory = factory;
    }

    @Test
    void testSimple() throws BeanException {
        // Given
        final Mapper<ValidAnnotationBean> mapper = factory.build(ValidAnnotationBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "eTND");
        // When
        final EventHandler<InputRowEntity, PKey, InputErrorEntity> handler = new EventHandler<>(row, creator, repository);
        final ValidAnnotationBean bean = mapper.map(row, handler);
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
        assertEquals(EventCreator.CSV_CODE_VIOLATION, error.getCode());
        assertEquals("size must be between 0 and 3", error.getMessage());
    }

}
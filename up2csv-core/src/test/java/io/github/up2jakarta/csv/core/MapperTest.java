package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.exception.ProcessorException;
import io.github.up2jakarta.csv.misc.SeverityType;
import io.github.up2jakarta.csv.misc.SimpleHandler;
import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.test.DummyTransformer;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.mapper.*;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ClientSegment;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ComplexAddress;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.SimpleAddress;
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
class MapperTest {

    private final InputRepository<InputRowEntity> repository = r -> 0;
    private final SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> creator = new SimpleKeyCreator<>(SimpleErrorEntity::new);
    private final MapperFactory factory;

    @Autowired
    MapperTest(MapperFactory factory) {
        this.factory = factory;
    }

    @Test
    void testCache() throws BeanException {
        // Given
        final Mapper<ValidBean> mapper1 = factory.build(ValidBean.class);
        // When
        final Mapper<ValidBean> mapper2 = factory.build(ValidBean.class);
        // Then
        assertNotSame(mapper1, mapper2);
    }

    @Test
    void allInOne() throws BeanException {
        // Given
        final Mapper<ClientSegment> mapper = factory.build(ClientSegment.class);
        // When
        final ClientSegment bean = mapper.map(
                "AAB", "UP2", "CSV", "TN-0000-1111-9999", "TND",
                "TN", "Tunis", "1001", "11 FreeAvenue",
                "FR", "Paris", "75020", "999 FreeAvenue", "Building B9", "6th floor, D26"
        );
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getKey());
        assertEquals("UP2", bean.getFirstName());
        assertEquals("CSV", bean.getLastName());
        assertEquals("TN-0000-1111-9999", bean.getBankAccount());
        assertEquals("TND", bean.getCurrency());
        // Simple Address
        final SimpleAddress simpleAddress = bean.getSimpleAddress();
        assertNotNull(simpleAddress);
        assertEquals("TN", simpleAddress.getCountry());
        assertEquals("Tunis", simpleAddress.getCity());
        assertEquals("1001", simpleAddress.getPostCode());
        assertEquals("11 FreeAvenue", simpleAddress.getAddressLine());
        // Complex Address
        final ComplexAddress complexAddress = bean.getComplexAddress();
        assertNotNull(complexAddress);
        assertEquals("FR", complexAddress.getCountry());
        assertEquals("Paris", complexAddress.getCity());
        assertEquals("75020", complexAddress.getPostCode());
        assertEquals("999 FreeAvenue", complexAddress.getAddressLine());
        assertEquals("999 FreeAvenue", complexAddress.getAddressLine1());
        assertEquals("Building B9", complexAddress.getAddressLine2());
        assertEquals("6th floor, D26", complexAddress.getAddressLine3());
    }

    @Test
    void validBean() throws BeanException {
        // Given
        final Mapper<SimpleSegment> mapper = factory.build(SimpleSegment.class);
        // When
        final SimpleSegment bean = mapper.map("AAB", "ABBESSI", "Software engineer");
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("Software engineer", bean.getRole());
    }

    @Test
    void testInnerStaticClass() throws BeanException {
        // Given
        final Mapper<InnerStaticSegment> mapper = factory.build(InnerStaticSegment.class);
        // When
        final InnerStaticSegment bean = mapper.map("AAB", "ABBESSI");
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getId());
        assertNotNull(bean.getInner());
        assertEquals("ABBESSI", bean.getInner().getName());
    }

    @Test
    void validBeanMoreColumns() throws BeanException {
        // Given
        final Mapper<SimpleSegment> mapper = factory.build(SimpleSegment.class);
        // When
        final SimpleSegment bean = mapper.map("AAB", "ABBESSI", "Software engineer", "MORE");
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("Software engineer", bean.getRole());
    }

    @Test
    void validBeanLessColumns() throws BeanException {
        // Given
        final Mapper<SimpleSegment> mapper = factory.build(SimpleSegment.class);
        // When
        final SimpleSegment bean = mapper.map("AAB", "ABBESSI");
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertNull(bean.getRole());
    }

    @Test
    void validBeanNullColumn() throws BeanException {
        // Given
        final Mapper<SimpleSegment> mapper = factory.build(SimpleSegment.class);
        // When
        final SimpleSegment bean = mapper.map("AAB", "ABBESSI", null);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertNull(bean.getRole());
    }

    @Test
    void validBeanEmptyColumn() throws BeanException {
        // Given
        final Mapper<SimpleSegment> mapper = factory.build(SimpleSegment.class);
        // When
        final SimpleSegment bean = mapper.map("AAB", "ABBESSI", "");
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("", bean.getRole());
    }

    @Test
    void validComplexBean() throws BeanException {
        // Given
        final Mapper<ComplexSegment> mapper = factory.build(ComplexSegment.class);
        // When
        final ComplexSegment bean = mapper.map("AAB", "TN", "Tunisia");
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertNotNull(bean.getCountry());
        assertEquals("TN", bean.getCountry().getCode());
        assertEquals("Tunisia", bean.getCountry().getName());
    }

    @Test
    void validExtendedBean() throws BeanException {
        // Given
        final Mapper<ExtendedCountryBean> mapper = factory.build(ExtendedCountryBean.class);
        // When
        final ExtendedCountryBean bean = mapper.map("TND", "TN", "Tunisia");
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
    }

    @Test
    void validGenericBean() throws BeanException {
        // Given
        final Mapper<GenericCountryBean> mapper = factory.build(GenericCountryBean.class);
        // When
        final GenericCountryBean bean = mapper.map("TND", "TN", "Tunisia");
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
    }

    @Test
    void testProcessors() throws BeanException {
        // Given
        final Mapper<ProcessorBean> mapper = factory.build(ProcessorBean.class);
        // When
        final ProcessorBean bean = mapper.map("TND\t\n(Dinar)", "TN\t\n(Tunisia)", "\n\t Tunisian\tDinar \n\t");
        // Then
        assertNotNull(bean);
        assertEquals("TND (Dinar)", bean.getCurrency());
        assertEquals("TN (Tunisia)", bean.getCode());
        assertEquals("Tunisian Dinar", bean.getName());
    }

    @Test
    void testValidatedAnnotation() throws BeanException {
        // Given
        final Mapper<ValidatedBean> mapper = factory.build(ValidatedBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "\n\t");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = new SimpleHandler<>(row, creator, repository);
        final ValidatedBean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        // Then Error 0
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertTrue(error.getSeverity().getLevel() >= SeverityType.ERROR.getLevel());
            assertEquals(EventCreator.CSV_CODE_VIOLATION, error.getCode());
            assertEquals("size must be between 1 and 3", error.getMessage());
        }
        // Then Error 1
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertTrue(error.getSeverity().getLevel() >= SeverityType.ERROR.getLevel());
            assertEquals(EventCreator.CSV_CODE_VIOLATION, error.getCode());
            assertEquals("size must be between 1 and 3", error.getMessage());
        }
    }

    @Test
    void testValidationGroupsAnnotation() throws BeanException {
        // Given
        final Mapper<ValidatedGroupsBean> mapper = factory.build(ValidatedGroupsBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S99, "\t\n");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = new SimpleHandler<>(row, creator, repository);
        final ValidatedGroupsBean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final SimpleErrorEntity error = errors.get(0);
        assertSame(row, error.getRow());
        assertEquals(0, error.getOrder());
        assertEquals(SeverityType.FATAL, error.getSeverity());
        assertEquals(EventCreator.CSV_CODE_VIOLATION, error.getCode());
        assertEquals("size must be between 1 and 3", error.getMessage());
    }

    @Test
    void testValidAnnotation() throws BeanException {
        // Given
        final Mapper<ValidAnnotationBean> mapper = factory.build(ValidAnnotationBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "eTND");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = new SimpleHandler<>(row, creator, repository);
        final ValidAnnotationBean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final SimpleErrorEntity error = errors.get(0);
        assertSame(row, error.getRow());
        assertEquals(0, error.getOrder());
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(EventCreator.CSV_CODE_VIOLATION, error.getCode());
        assertEquals("size must be between 0 and 3", error.getMessage());
    }

    @Test
    void testProcessor1Exception() throws BeanException {
        // Given
        final Mapper<Test1Processor> mapper = factory.build(Test1Processor.class);
        // When
        final ProcessorException thrown = assertThrows(ProcessorException.class, () -> mapper.map("any"));
        // THEN
        assertEquals(DummyTransformer.class, thrown.getBeanType());
        assertEquals("test", thrown.getProperty());
        assertEquals("java.lang.UnsupportedOperationException: any", thrown.getMessage());
        assertEquals("DummyTransformer[test] processing error: java.lang.UnsupportedOperationException: any", thrown.getFormattedMessage());
    }

    @Test
    void testProcessor2Exception() throws BeanException {
        // Given
        final Mapper<Test2Processor> mapper = factory.build(Test2Processor.class);
        // When
        final ProcessorException thrown = assertThrows(ProcessorException.class, () -> mapper.map("other"));
        // THEN
        assertEquals(DummyTransformer.class, thrown.getBeanType());
        assertEquals("test", thrown.getProperty());
        assertEquals("io.github.up2jakarta.csv.exception.ProcessorException: Wrapper", thrown.getMessage());
    }

}

package io.github.up2jakarta.csv.core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.EventHandler.SimpleHandler;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.input.InputRepository;
import io.github.up2jakarta.csv.misc.Listable;
import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.mapper.*;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.AddressSegment;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ClientSegment;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ComplexAddress;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.SimpleAddress;
import io.github.up2jakarta.csv.test.bean.processor.ProcessorBean;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.input.InputRowEntity;
import io.github.up2jakarta.csv.test.input.SegmentType;
import io.github.up2jakarta.csv.test.input.SimpleErrorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.github.up2jakarta.csv.core.EventHandler.failFast;
import static io.github.up2jakarta.csv.core.MapperFactory.LOGGER;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_VALIDATOR;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class MapperTest {

    private final InputRepository<InputRowEntity> repository = (r -> 0);
    private final SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> creator;
    private final MapperFactory factory;

    @Autowired
    MapperTest(MapperFactory factory, SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> creator) {
        this.factory = factory;
        this.creator = creator;
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
    @SuppressWarnings("ALL")
    void testNull() throws BeanException {
        // Given
        final String[] data = null;
        final Mapper<ValidBean> mapper = factory.build(ValidBean.class);
        final InputRowEntity row3 = Tests.create(SegmentType.S00, data);
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler2 = EventHandler.of(null, creator, repository);
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler3 = EventHandler.of(row3, creator, repository);
        // When
        final ValidBean bean1 = mapper.map(data);
        final ValidBean bean2 = mapper.map(null, handler2);
        final ValidBean bean3 = mapper.map(null, handler3);
        final ValidBean bean4 = mapper.map(row3, handler3);
        // Then
        assertNull(bean1);
        assertNull(bean2);
        assertNull(bean3);
        assertNull(bean4);
    }

    @Test
    void testOneShot() throws BeanException {
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
        final Mapper<Validator2Bean> mapper = factory.build(Validator2Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "\n\t");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
        final Validator2Bean bean = mapper.map(row, handler);
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
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("size must be between 1 and 3", error.getMessage());
        }
        // Then Error 1
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertTrue(error.getSeverity().getLevel() >= SeverityType.ERROR.getLevel());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("size must be between 1 and 3", error.getMessage());
        }
    }

    @Test
    void testValidationGroupsAnnotation() throws BeanException {
        // Given
        final Mapper<ValidatedGroupsBean> mapper = factory.build(ValidatedGroupsBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S99, "\t\n");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
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
        assertEquals(ERROR_VALIDATOR, error.getCode());
        assertEquals("size must be between 1 and 3", error.getMessage());
    }

    @Test
    void testValidAnnotation() throws BeanException {
        // Given
        final Mapper<Validator1Bean> mapper = factory.build(Validator1Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "eTND");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
        final Validator1Bean bean = mapper.map(row, handler);
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
        assertEquals(CurrencyConverter.ISO_4217, error.getCode());
        assertEquals("size must be between 0 and 3", error.getMessage());
    }

    // Checking
    @Test
    void testLocalClass() {
        //Given
        class LocalSegment implements Segment {
        }
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(LocalSegment.class));
        // THEN
        assertEquals(LocalSegment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("LocalSegment[class] - local class is not allowed", thrown.getFormattedMessage());
    }

    @Test
    void testAbstractClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(AddressSegment.class));
        // THEN
        assertEquals(AddressSegment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("AddressSegment[class] - abstract class is not allowed", thrown.getMessage());
    }

    @Test
    void testInterface() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Segment.class));
        // THEN
        assertEquals(Segment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("Segment[class] - interface is not allowed", thrown.getMessage());
    }

    @Test
    void testGenericClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test8Segment.class));
        // THEN
        assertEquals(Test8Segment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("Test8Segment[class] - generic class is not allowed", thrown.getMessage());
    }

    @Test
    void testInnerClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(InnerSegment.class));
        // THEN
        assertEquals(InnerSegment.InnerFragment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("InnerFragment[class] - inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testInnerSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(InnerSegment.InnerFragment.class));
        // THEN
        assertEquals(InnerSegment.InnerFragment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("InnerFragment[class] - inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(TestRecursive1Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("TestRecursive1Segment[class] - cyclic fragment is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveInheritance() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(TestRecursive3Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("TestRecursive1Segment[class] - cyclic fragment is not allowed", thrown.getMessage());
    }

    @Test
    void testMapValidRecord() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(RecordBean.class));
        // THEN
        assertEquals(RecordBean.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("RecordBean[class] - record class is not allowed", thrown.getMessage());
    }

    @Test
    void testBeanWithInteger() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(BeanWithInteger.class));
        // THEN
        assertEquals(BeanWithInteger.class, thrown.getBeanType());
        assertEquals("id", thrown.getAttribute());
        assertEquals("BeanWithInteger[id] - must be annotated with @Converter or one of its shortcuts", thrown.getMessage());
    }

    @Test
    void testFragment() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test1Segment.class));
        // THEN
        assertEquals(Test1Segment.class, thrown.getBeanType());
        assertEquals("p", thrown.getAttribute());
        assertEquals("Test1Segment[p] - type must implements Segment", thrown.getMessage());
    }

    @Test
    void testFragmentOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test2Segment.class));
        // THEN
        assertEquals(Test2Segment.class, thrown.getBeanType());
        assertEquals("p", thrown.getAttribute());
        assertEquals("Test2Segment[p] - @Fragment[value] must be positive", thrown.getMessage());
    }

    @Test
    void testPositionOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test3Segment.class));
        // THEN
        assertEquals(Test3Segment.class, thrown.getBeanType());
        assertEquals("p", thrown.getAttribute());
        assertEquals("Test3Segment[p] - @Position[value] must be positive", thrown.getMessage());
    }

    @Test
    void testFieldName() throws Throwable {
        // Given
        // WHEN
        final List<ILoggingEvent> logs = Tests.hack(LOGGER, () -> factory.build(Test4Segment.class));
        // THEN
        assertEquals(1, logs.size());
        final ILoggingEvent event = logs.get(0);
        assertEquals(Level.WARN, event.getLevel());
        assertEquals("{}[{}] : should starts with an lowercase character", event.getMessage());
        assertEquals(2, event.getArgumentArray().length);
        assertEquals("Test4Segment", event.getArgumentArray()[0]);
        assertEquals("Upper", event.getArgumentArray()[1]);
    }

    @Test
    void testFieldVisibility() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test5Segment.class));
        // THEN
        assertEquals(Test5Segment.class, thrown.getBeanType());
        assertEquals("publicField", thrown.getAttribute());
        assertEquals("Test5Segment[publicField] - must not be public", thrown.getMessage());
    }

    @Test
    void testFieldFinal() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test6Segment.class));
        // THEN
        assertEquals(Test6Segment.class, thrown.getBeanType());
        assertEquals("finalField", thrown.getAttribute());
        assertEquals("Test6Segment[finalField] - must not be final", thrown.getMessage());
    }

    @Test
    void testFieldStatic() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test7Segment.class));
        // THEN
        assertEquals(Test7Segment.class, thrown.getBeanType());
        assertEquals("staticField", thrown.getAttribute());
        assertEquals("Test7Segment[staticField] - must not be static", thrown.getMessage());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testValidRecursive() throws BeanException {
        // When
        final Mapper<TestRecursive6Segment> mapper = factory.build(TestRecursive6Segment.class);
        final List<Property<?>> fields = ((Listable<Property<?>>) mapper).toList();
        // THEN
        assertEquals(3, fields.size());
        {
            final Property<?> property = fields.get(0);
            assertEquals("id", property.field.getName());
            assertEquals(0, property.offset);
        }
        {
            final Property<?> property = fields.get(1);
            assertEquals("any", property.field.getName());
            assertEquals(1, property.offset);
        }
        {
            final Property<?> fragment = fields.get(2);
            assertEquals("fragment", fragment.field.getName());
            assertEquals(2, fragment.offset);
            assertInstanceOf(Listable.class, fragment);
            final List<Property<?>> fProperties = ((Listable<Property<?>>) fragment).toList();
            assertEquals(2, fProperties.size());
            {
                final Property<?> property = fProperties.get(0);
                assertEquals("id", property.field.getName());
                assertEquals(2, property.offset);
            }
            {
                final Property<?> property = fProperties.get(1);
                assertEquals("name", property.field.getName());
                assertEquals(2 + 1, property.offset);
            }
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testMappingBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<ValidBean> mapper = factory.build(ValidBean.class);
        // WHEN
        final ValidBean bean = mapper.map(id, name);
        final List<Property<?>> fields = ((Listable<Property<?>>) mapper).toList();
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.get(0).field.getName());
        assertEquals("name", fields.get(1).field.getName());
        for (final Property<?> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty f = (StringProperty) p;
            f.field.setAccessible(true);
            assertEquals("V", f.field.get(bean));
            // When
            f.setValue(bean, "Test", 0, failFast());
            // Then
            assertEquals("Test", f.field.get(bean));
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testMappingNoOrderBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<NoOrderBean> mapper = factory.build(NoOrderBean.class);
        // WHEN
        final NoOrderBean bean = mapper.map(id, name);
        final List<Property<?>> fields = ((Listable<Property<?>>) mapper).toList();
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.get(0).field.getName());
        assertEquals("name", fields.get(1).field.getName());
        for (final Property<?> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty f = (StringProperty) p;
            f.field.setAccessible(true);
            assertEquals("V", f.field.get(bean));
            // When
            f.setValue(bean, "Test", 0, failFast());
            // Then
            assertEquals("Test", f.field.get(bean));
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testMappingNoPositionBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<NoPositionBean> mapper = factory.build(NoPositionBean.class);
        // WHEN
        final NoPositionBean bean = mapper.map(id, name);
        final List<Property<?>> fields = ((Listable<Property<?>>) mapper).toList();
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.get(0).field.getName());
        assertEquals("name", fields.get(1).field.getName());
        for (final Property<?> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty f = (StringProperty) p;
            f.field.setAccessible(true);
            assertEquals("V", f.field.get(bean));
            // When
            f.setValue(bean, "Test", 0, failFast());
            // Then
            assertEquals("Test", f.field.get(bean));
        }
    }

}

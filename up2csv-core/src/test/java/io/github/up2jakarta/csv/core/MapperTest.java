package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Listable;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.converter.SupportEntity;
import io.github.up2jakarta.csv.test.bean.jpa.NoteEntity;
import io.github.up2jakarta.csv.test.bean.mapper.*;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.AbstractAddress;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ClientSegment;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ComplexAddress;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.SimpleAddress;
import io.github.up2jakarta.csv.test.bean.processor.ProcessorBean;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.github.up2jakarta.csv.core.EventHandler.failFast;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_VALIDATOR;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class MapperTest {

    private final SimpleCreator creator;
    private final MapperFactory<DataId> factory;

    @Autowired
    MapperTest(MapperFactory<DataId> factory, SimpleCreator creator) {
        this.factory = factory;
        this.creator = creator;
    }

    @Test
    void testCache() throws BeanException {
        // Given
        final Mapper<ValidBean, DataId> mapper1 = factory.build(ValidBean.class);
        // When
        final Mapper<ValidBean, DataId> mapper2 = factory.build(ValidBean.class);
        // Then
        assertNotSame(mapper1, mapper2);
    }

    @Test
    @SuppressWarnings("ALL")
    void testNull() throws BeanException {
        // Given
        final String[] data = null;
        final Mapper<ValidBean, DataId> mapper = factory.build(ValidBean.class);
        final InputRowEntity row3 = Tests.create(SegmentType.S00, data);
        final SimpleHandler handler2 = new SimpleHandler(null, creator);
        final SimpleHandler handler3 = new SimpleHandler(row3, creator);
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
    void testUnmapNull() throws BeanException {
        // Given
        final ValidBean bean = null;
        final Mapper<ValidBean, DataId> mapper = factory.build(ValidBean.class);
        // When
        final String[] out = mapper.unmap(bean);
        // Then
        assertNull(out);
    }

    @Test
    void testUnmapDefault() throws BeanException {
        // Given
        final DefaultBean bean = new DefaultBean();
        final Mapper<DefaultBean, DataId> mapper = factory.build(DefaultBean.class);
        // When
        final String[] out = mapper.unmap(bean);
        // Then Bean
        assertNull(bean.getCode());
        assertNull(bean.getReference());
        // Then Unmapping
        assertNotNull(out);
        assertEquals(3, out.length);
        for (String s : out) {
            assertEquals("*", s);
        }
    }

    @Test
    void testUnmapEmpty() throws BeanException {
        // Given
        final ValidBean bean = new ValidBean();
        final Mapper<ValidBean, DataId> mapper = factory.build(ValidBean.class);
        // When
        final String[] out = mapper.unmap(bean);
        // Then
        assertNotNull(out);
        assertArrayEquals(new String[]{null, null}, out);
    }

    @Test
    void testUnmapSize() throws BeanException {
        // GIVEN
        final String[] data = {"100", "Test 100", "2024-07-25", "57.00", "TND", "4.0625", "C62", "Y", "P9D", "TN", "dGVzdA=="};
        final Mapper<SupportEntity, DataId> parser = factory.build(SupportEntity.class);
        // WHEN
        final SupportEntity entity = parser.map(data);
        assertNotNull(entity);
        assertArrayEquals("test".getBytes(UTF_8), entity.getBase64());
        // When Unmapping
        final String[] out = parser.unmap(entity);
        // Then
        assertNotNull(out);
        assertEquals(data.length + 1, out.length);
        assertNull(out[0]);
        for (var i = 0; i < data.length; i++) {
            assertEquals(data[i], out[i + 1]);
        }
    }


    @Test
    void testOneShot() throws BeanException {
        // Given
        final Mapper<ClientSegment, DataId> mapper = factory.build(ClientSegment.class);
        final String[] data = new String[]{
                "AAB", "UP2", "CSV", "TN-0000-1111-9999", "TND",
                "TN", "Tunis", "1001", "11 FreeAvenue",
                "FR", "Paris", "75020", "999 FreeAvenue", "Building B9", "6th floor, D26"
        };
        // When
        final ClientSegment bean = mapper.map(data);
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
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validBean() throws BeanException {
        // Given
        final Mapper<SimpleSegment, DataId> mapper = factory.build(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", "Software engineer"};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("Software engineer", bean.getRole());
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testInnerStaticClass() throws BeanException {
        // Given
        final Mapper<InnerStaticSegment, DataId> mapper = factory.build(InnerStaticSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI"};
        // When
        final InnerStaticSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getId());
        assertNotNull(bean.getInner());
        assertEquals("ABBESSI", bean.getInner().getName());
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validBeanMoreColumns() throws BeanException {
        // Given
        final Mapper<SimpleSegment, DataId> mapper = factory.build(SimpleSegment.class);
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
        final Mapper<SimpleSegment, DataId> mapper = factory.build(SimpleSegment.class);
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
        final Mapper<SimpleSegment, DataId> mapper = factory.build(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", null};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertNull(bean.getRole());
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validBeanEmptyColumn() throws BeanException {
        // Given
        final Mapper<SimpleSegment, DataId> mapper = factory.build(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", ""};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("", bean.getRole());
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validComplexBean() throws BeanException {
        // Given
        final Mapper<ComplexSegment, DataId> mapper = factory.build(ComplexSegment.class);
        final String[] data = new String[]{"AAB", "TN", "Tunisia"};
        // When
        final ComplexSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertNotNull(bean.getCountry());
        assertEquals("TN", bean.getCountry().getCode());
        assertEquals("Tunisia", bean.getCountry().getName());
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validExtendedBean() throws BeanException {
        // Given
        final Mapper<ExtendedCountryBean, DataId> mapper = factory.build(ExtendedCountryBean.class);
        final String[] data = new String[]{"TND", "TN", "Tunisia"};
        // When
        final ExtendedCountryBean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validGenericBean() throws BeanException {
        // Given
        final Mapper<GenericCountryBean, DataId> mapper = factory.build(GenericCountryBean.class);
        final String[] data = new String[]{"TND", "TN", "Tunisia"};
        // When
        final GenericCountryBean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
        // When Unmapping
        final String[] out = mapper.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testProcessors() throws BeanException {
        // Given
        final Mapper<ProcessorBean, DataId> mapper = factory.build(ProcessorBean.class);
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
        final Mapper<Validator2Bean, DataId> mapper = factory.build(Validator2Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "\n\t");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Validator2Bean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error 0
        {
            final SimpleErrorEntity error = errors.getFirst();
            assertSame(row, error.getRecord());
            assertEquals(0, error.getOrder());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals("size must be between 1 and 3", error.getMessage());
        }
    }

    @Test
    void testValidationGroupsAnnotation() throws BeanException {
        // Given
        final Mapper<ValidatedGroupsBean, DataId> mapper = factory.build(ValidatedGroupsBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S99, "\t\n");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final ValidatedGroupsBean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final SimpleErrorEntity error = errors.getFirst();
        assertSame(row, error.getRecord());
        assertEquals(0, error.getOrder());
        assertEquals(SeverityType.WARNING, error.getSeverity());
        assertEquals(ERROR_VALIDATOR, error.getCode());
        assertEquals("size must be between 1 and 3", error.getMessage());
    }

    @Test
    void testValidAnnotation() throws BeanException {
        // Given
        final Mapper<Validator1Bean, DataId> mapper = factory.build(Validator1Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "eTND");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Validator1Bean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final SimpleErrorEntity error = errors.getFirst();
        assertSame(row, error.getRecord());
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
        assertEquals(LocalSegment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("LocalSegment[class] - local class is not allowed", thrown.getFormattedMessage());
    }

    @Test
    void testAbstractClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(AbstractAddress.class));
        // THEN
        assertEquals(AbstractAddress.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("AbstractAddress[class] - abstract class is not allowed", thrown.getMessage());
    }

    @Test
    void testInterface() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Segment.class));
        // THEN
        assertEquals(Segment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("Segment[class] - interface is not allowed", thrown.getMessage());
    }

    @Test
    void testGenericClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test8Segment.class));
        // THEN
        assertEquals(Test8Segment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("Test8Segment[class] - generic class is not allowed", thrown.getMessage());
    }

    @Test
    void testInnerClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(InnerSegment.class));
        // THEN
        assertEquals(InnerSegment.InnerFragment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("InnerFragment[class] - inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testInnerSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(InnerSegment.InnerFragment.class));
        // THEN
        assertEquals(InnerSegment.InnerFragment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("InnerFragment[class] - inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(TestRecursive1Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("TestRecursive1Segment[class] - cyclic fragment is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveInheritance() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(TestRecursive3Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("TestRecursive1Segment[class] - cyclic fragment is not allowed", thrown.getMessage());
    }

    @Test
    void testMapValidRecord() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(RecordBean.class));
        // THEN
        assertEquals(RecordBean.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("RecordBean[class] - record class is not allowed", thrown.getMessage());
    }

    @Test
    void testBeanWithInteger() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(BeanWithInteger.class));
        // THEN
        assertEquals(BeanWithInteger.class, thrown.getSource());
        assertEquals("id", thrown.getLocator());
        assertEquals("BeanWithInteger[id] - must be annotated with @Up2Converter or one of its shortcuts", thrown.getMessage());
    }

    @Test
    void testFragment() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test1Segment.class));
        // THEN
        assertEquals(Test1Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("Test1Segment[p] - type must implements Segment", thrown.getMessage());
    }

    @Test
    void testFragmentOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test2Segment.class));
        // THEN
        assertEquals(Test2Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("Test2Segment[p] - @Fragment[value] must be positive", thrown.getMessage());
    }

    @Test
    void testPositionOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test3Segment.class));
        // THEN
        assertEquals(Test3Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("Test3Segment[p] - @Position[value] must be positive", thrown.getMessage());
    }

    @Test
    void testFieldName() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test4Segment.class));
        // THEN
        assertEquals(Test4Segment.class, thrown.getSource());
        assertEquals("Upper", thrown.getLocator());
        assertEquals("Test4Segment[Upper] - must starts with an lowercase character", thrown.getMessage());
    }

    @Test
    void testFieldVisibility() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test5Segment.class));
        // THEN
        assertEquals(Test5Segment.class, thrown.getSource());
        assertEquals("publicField", thrown.getLocator());
        assertEquals("Test5Segment[publicField] - must not be public", thrown.getMessage());
    }

    @Test
    void testFieldFinal() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test6Segment.class));
        // THEN
        assertEquals(Test6Segment.class, thrown.getSource());
        assertEquals("finalField", thrown.getLocator());
        assertEquals("Test6Segment[finalField] - must not be final", thrown.getMessage());
    }

    @Test
    void testFieldStatic() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test7Segment.class));
        // THEN
        assertEquals(Test7Segment.class, thrown.getSource());
        assertEquals("staticField", thrown.getLocator());
        assertEquals("Test7Segment[staticField] - must not be static", thrown.getMessage());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testValidRecursive() throws BeanException {
        // When
        final Mapper<TestRecursive6Segment, DataId> mapper = factory.build(TestRecursive6Segment.class);
        final List<Property<?, DataId>> fields = ((Listable<Property<?, DataId>>) mapper).toList();
        // THEN
        assertEquals(3, fields.size());
        {
            final Property<?, DataId> property = fields.getFirst();
            assertEquals("id", property.field.getName());
            assertEquals(0, property.offset);
        }
        {
            final Property<?, DataId> property = fields.get(1);
            assertEquals("any", property.field.getName());
            assertEquals(1, property.offset);
        }
        {
            final Property<?, DataId> fragment = fields.get(2);
            assertEquals("fragment", fragment.field.getName());
            assertEquals(2, fragment.offset);
            assertInstanceOf(Listable.class, fragment);
            final List<Property<?, DataId>> fProperties = ((Listable<Property<?, DataId>>) fragment).toList();
            assertEquals(2, fProperties.size());
            {
                final Property<?, DataId> property = fProperties.getFirst();
                assertEquals("id", property.field.getName());
                assertEquals(2, property.offset);
            }
            {
                final Property<?, DataId> property = fProperties.get(1);
                assertEquals("name", property.field.getName());
                assertEquals(2 + 1, property.offset);
            }
        }
    }

    @Test
    void testMappingBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<ValidBean, DataId> mapper = factory.build(ValidBean.class);
        // WHEN
        final ValidBean bean = mapper.map(id, name);
        final List<Property<?, DataId>> fields = ((Listable<Property<?, DataId>>) mapper).toList();
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.getFirst().field.getName());
        assertEquals("name", fields.get(1).field.getName());
        for (final Property<?, DataId> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty<DataId> f = (StringProperty<DataId>) p;
            f.field.setAccessible(true);
            assertEquals("V", f.field.get(bean));
            // When
            f.setValue(bean, "Test", 0, failFast(false));
            // Then
            assertEquals("Test", f.field.get(bean));
        }
    }

    @Test
    void testMappingNoOrderBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<NoOrderBean, DataId> mapper = factory.build(NoOrderBean.class);
        // WHEN
        final NoOrderBean bean = mapper.map(id, name);
        final List<Property<?, DataId>> fields = ((Listable<Property<?, DataId>>) mapper).toList();
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.getFirst().field.getName());
        assertEquals("name", fields.get(1).field.getName());
        for (final Property<?, DataId> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty<DataId> f = (StringProperty<DataId>) p;
            f.field.setAccessible(true);
            assertEquals("V", f.field.get(bean));
            // When
            f.setValue(bean, "Test", 0, failFast(false));
            // Then
            assertEquals("Test", f.field.get(bean));
        }
    }

    @Test
    void testMappingNoPositionBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<NoPositionBean, DataId> mapper = factory.build(NoPositionBean.class);
        // WHEN
        final NoPositionBean bean = mapper.map(id, name);
        final List<Property<?, DataId>> fields = ((Listable<Property<?, DataId>>) mapper).toList();
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.getFirst().field.getName());
        assertEquals("name", fields.get(1).field.getName());
        for (final Property<?, DataId> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty<DataId> f = (StringProperty<DataId>) p;
            f.field.setAccessible(true);
            assertEquals("V", f.field.get(bean));
            // When
            f.setValue(bean, "Test", 0, failFast(false));
            // Then
            assertEquals("Test", f.field.get(bean));
        }
    }

    @Test
    void testNullable() throws BeanException {
        // Given
        final Mapper<NoteEntity, DataId> mapper = factory.build(NoteEntity.class);
        final String[] data = new String[]{"ZZZ", "Content", null, "FR", "XXX", "EUR"};
        // When
        final NoteEntity segment = mapper.map(data);
        // Then
        assertNotNull(segment);
        assertEquals("ZZZ", segment.getSubjectCode());
        assertEquals("Content", segment.getContent());
        assertNull(segment.getTest1());
        assertNotNull(segment.getTest2());
        assertEquals("XXX", segment.getTest2().getValue());
        assertEquals(CurrencyCodeType.EUR, segment.getTest2().getCode());
    }

}

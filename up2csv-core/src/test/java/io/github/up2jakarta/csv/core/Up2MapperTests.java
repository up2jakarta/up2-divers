package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.hdl.PFProperty;
import io.github.up2jakarta.csv.core.hdl.Properties;
import io.github.up2jakarta.csv.core.hdl.Property;
import io.github.up2jakarta.csv.core.misc.clv.CountryCodeType;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyConverter;
import io.github.up2jakarta.csv.core.misc.jpa.NoteEntity;
import io.github.up2jakarta.csv.core.misc.map.*;
import io.github.up2jakarta.csv.core.misc.map.oneshot.AbstractAddress;
import io.github.up2jakarta.csv.core.misc.map.oneshot.ClientSegment;
import io.github.up2jakarta.csv.core.misc.map.oneshot.ComplexAddress;
import io.github.up2jakarta.csv.core.misc.map.oneshot.SimpleAddress;
import io.github.up2jakarta.csv.core.misc.prc.ProcessorBean;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_VALIDATOR;
import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static io.github.up2jakarta.xml.api.SeverityType.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2MapperTests {

    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2MapperTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testCache() throws BeanException {
        // Given
        final Up2Mapper<ValidBean, GroupType> mapper1 = factory.build(ValidBean.class);
        // When
        final Up2Mapper<ValidBean, GroupType> mapper2 = factory.build(ValidBean.class);
        // Then
        assertNotSame(mapper1, mapper2);
    }

    @Test
    @SuppressWarnings("ALL")
    void testNull() throws BeanException {
        // Given
        final String[] data = null;
        final Up2Mapper<ValidBean, GroupType> mapper = factory.build(ValidBean.class);
        final InputRecord row3 = record(SegmentType.S00, data);
        final InputCollector handler2 = new InputCollector(null);
        final InputCollector handler3 = new InputCollector(row3);
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
        final Up2Mapper<ClientSegment, GroupType> mapper = factory.build(ClientSegment.class);
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
        final Up2Format<ClientSegment, GroupType> format = factory.format(ClientSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testDefault1() throws BeanException {
        // Given
        final Up2Mapper<Default1Bean, GroupType> mapper = factory.build(Default1Bean.class);
        // When
        final Default1Bean bean = mapper.map();
        // Then Bean
        assertNotNull(bean);
        assertEquals("*", bean.getCode());
        assertNotNull(bean.getReference());
        assertEquals("*", bean.getReference().getCode());
        assertEquals("*", bean.getReference().getValue());
    }

    @Test
    void testDefault2Nullable() throws BeanException {
        // Given
        final Up2Mapper<Default2Bean, GroupType> mapper = factory.build(Default2Bean.class);
        // When
        final Default2Bean bean = mapper.map();
        // Then Bean
        assertNotNull(bean);
        assertEquals("*", bean.getCode());
        assertNull(bean.getReference());
    }

    @Test
    void validBean() throws BeanException {
        // Given
        final Up2Mapper<SimpleSegment, GroupType> mapper = factory.build(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", "Software engineer"};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("Software engineer", bean.getRole());
        // When Unmapping
        final Up2Format<SimpleSegment, GroupType> format = factory.format(SimpleSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testInnerStaticClass() throws BeanException {
        // Given
        final Up2Mapper<InnerStaticSegment, GroupType> mapper = factory.build(InnerStaticSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI"};
        // When
        final InnerStaticSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getId());
        assertNotNull(bean.getInner());
        assertEquals("ABBESSI", bean.getInner().getName());
        // When Unmapping
        final Up2Format<InnerStaticSegment, GroupType> format = factory.format(InnerStaticSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validBeanMoreColumns() throws BeanException {
        // Given
        final Up2Mapper<SimpleSegment, GroupType> mapper = factory.build(SimpleSegment.class);
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
        final Up2Mapper<SimpleSegment, GroupType> mapper = factory.build(SimpleSegment.class);
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
        final Up2Mapper<SimpleSegment, GroupType> mapper = factory.build(SimpleSegment.class);
        final Up2Format<SimpleSegment, GroupType> format = factory.format(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", null};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertNull(bean.getRole());
        // When Unmapping
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validBeanEmptyColumn() throws BeanException {
        // Given
        final Up2Mapper<SimpleSegment, GroupType> mapper = factory.build(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", ""};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("", bean.getRole());
        // When Unmapping
        final Up2Format<SimpleSegment, GroupType> format = factory.format(SimpleSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validComplexBean() throws BeanException {
        // Given
        final Up2Mapper<ComplexSegment, GroupType> mapper = factory.build(ComplexSegment.class);
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
        final Up2Format<ComplexSegment, GroupType> format = factory.format(ComplexSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validExtendedBean() throws BeanException {
        // Given
        final Up2Mapper<ExtendedCountryBean, GroupType> mapper = factory.build(ExtendedCountryBean.class);
        final String[] data = new String[]{"TND", "TN", "Tunisia"};
        // When
        final ExtendedCountryBean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
        // When Unmapping
        final Up2Format<ExtendedCountryBean, GroupType> format = factory.format(ExtendedCountryBean.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validGenericBean() throws BeanException {
        // Given
        final Up2Mapper<GenericCountryBean, GroupType> mapper = factory.build(GenericCountryBean.class);
        final String[] data = new String[]{"TND", "TN", "Tunisia"};
        // When
        final GenericCountryBean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
        // When Unmapping
        final Up2Format<GenericCountryBean, GroupType> format = factory.format(GenericCountryBean.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testProcessors() throws BeanException {
        // Given
        final Up2Mapper<ProcessorBean, GroupType> mapper = factory.build(ProcessorBean.class);
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
        final Up2Mapper<Validator2Bean, GroupType> mapper = factory.build(Validator2Bean.class);
        final InputRecord row = record(SegmentType.S00, "\n\t");
        // When
        final InputCollector handler = new InputCollector(row);
        final Validator2Bean bean = mapper.map(row, handler);
        final Collection<InputError> errors = handler.toCollection();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(2, errors.size());
        for (final InputError error : errors) {
            assertSame(row, error.getKey().getRecord());
            assertTrue(error.getKey().getOrder() >= 0);
            assertEquals(ERROR_VALIDATOR, error.getCode());
            if (error.getSeverity() == FATAL) {
                assertEquals("must not be empty", error.getMessage());
            } else {
                assertEquals(WARNING, error.getSeverity());
                assertEquals("size must be between 1 and 3", error.getMessage());
            }
        }
    }

    @Test
    void testValidationGroupsAnnotation() throws BeanException {
        // Given
        final Up2Mapper<ValidatedGroupsBean, GroupType> mapper = factory.build(ValidatedGroupsBean.class);
        final InputRecord row = record(SegmentType.S00, "\t\n");
        // When
        final InputCollector handler = new InputCollector(row);
        final ValidatedGroupsBean bean = mapper.map(row, handler);
        final Collection<InputError> errors = handler.toCollection();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputError error = errors.iterator().next();
        assertSame(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(WARNING, error.getSeverity());
        assertEquals(ERROR_VALIDATOR, error.getCode());
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
        final Collection<InputError> errors = handler.toCollection();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(1, errors.size());
        // Then Error
        final InputError error = errors.iterator().next();
        assertSame(row, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertEquals(ERROR, error.getSeverity());
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
    void testValidRecursive1() throws BeanException {
        // When
        final Up2Mapper<TestRecursive7OverrideSegment, GroupType> mapper = factory.build(TestRecursive7OverrideSegment.class);
        // THEN
        assertEquals(2, mapper.node.properties.size());
    }

    @Test
    void testValidRecursive2() throws BeanException {
        // When
        final Up2Mapper<TestRecursive8OverrideSegment, GroupType> mapper = factory.build(TestRecursive8OverrideSegment.class);
        // THEN
        assertEquals(3, mapper.node.properties.size());
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
        final Up2Mapper<TestRecursive6Segment, GroupType> mapper = factory.build(TestRecursive6Segment.class);
        final List<Property<?, GroupType>> fields = mapper.node.properties;
        // THEN
        assertEquals(3, fields.size());
        {
            final Property<?, GroupType> property = fields.getFirst();
            assertEquals("id", property.getName());
            assertEquals(0, property.offset);
        }
        {
            final Property<?, GroupType> property = fields.get(1);
            assertEquals("any", property.getName());
            assertEquals(1, property.offset);
        }
        {
            final Property<?, GroupType> fragment = fields.get(2);
            assertEquals("fragment", fragment.getName());
            assertEquals(2, fragment.offset);
            assertInstanceOf(PFProperty.class, fragment);
            final List<Property<?, GroupType>> fProperties = ((PFProperty<?, GroupType>) fragment).node.properties;
            assertEquals(2, fProperties.size());
            {
                final Property<?, GroupType> property = fProperties.getFirst();
                assertEquals("id", property.getName());
                assertEquals(2, property.offset);
            }
            {
                final Property<?, GroupType> property = fProperties.get(1);
                assertEquals("name", property.getName());
                assertEquals(2 + 1, property.offset);
            }
        }
    }

    @Test
    void testMappingBean() throws Exception {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Up2Mapper<ValidBean, GroupType> mapper = factory.build(ValidBean.class);
        // WHEN
        final ValidBean bean = mapper.map(id, name);
        final List<Property<?, GroupType>> fields = mapper.node.properties;
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.getFirst().getName());
        assertEquals("name", fields.get(1).getName());
        Properties.assertBean(bean, fields);
    }

    @Test
    void testMappingNoOrderBean() throws Exception {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Up2Mapper<NoOrderBean, GroupType> mapper = factory.build(NoOrderBean.class);
        // WHEN
        final NoOrderBean bean = mapper.map(id, name);
        final List<Property<?, GroupType>> fields = mapper.node.properties;
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.getFirst().getName());
        assertEquals("name", fields.get(1).getName());
        Properties.assertBean(bean, fields);
    }

    @Test
    void testMappingNoPositionBean() throws Exception {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Up2Mapper<NoPositionBean, GroupType> mapper = factory.build(NoPositionBean.class);
        // WHEN
        final NoPositionBean bean = mapper.map(id, name);
        final List<Property<?, GroupType>> fields = mapper.node.properties;
        // THEN
        assertEquals(2, fields.size());
        assertEquals("id", fields.getFirst().getName());
        assertEquals("name", fields.get(1).getName());
        Properties.assertBean(bean, fields);
    }

    @Test
    void testRequired1() throws BeanException {
        // Given
        final Up2Mapper<NoteEntity, GroupType> mapper = factory.build(NoteEntity.class);
        final String[] data = new String[]{"ZZZ", "Content", null, "???", "T2", "EUR"};
        // When
        final NoteEntity segment = mapper.map(data);
        mapper.toFormat().validate(segment, of(WARNING));
        // Then
        assertNotNull(segment);
        assertEquals("ZZZ", segment.getSubjectCode());
        assertEquals("Content", segment.getContent());
        assertNull(segment.getTest1());
        assertNotNull(segment.getTest2());
        assertEquals("T2", segment.getTest2().getValue());
        assertEquals(CurrencyCodeType.EUR, segment.getTest2().getCode());
    }

    @Test
    void testRequired2() throws BeanException {
        // Given
        final Up2Mapper<NoteEntity, GroupType> mapper = factory.build(NoteEntity.class);
        final String[] data = new String[]{"ZZZ", "Content", "T1", "FR", null, "EUR"};
        // When
        final NoteEntity bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("ZZZ", bean.getSubjectCode());
        assertEquals("Content", bean.getContent());
        assertNotNull(bean.getTest1());
        assertEquals("T1", bean.getTest1().getValue());
        assertEquals(CountryCodeType.FR, bean.getTest1().getCode());
        assertNotNull(bean.getTest2());
        assertNull(bean.getTest2().getValue());
        assertEquals(CurrencyCodeType.EUR, bean.getTest2().getCode());
    }

    @Test
    void testTrimFragment() throws BeanException {
        // Given
        final Up2Mapper<ComplexSegment, GroupType> mapper = factory.build(ComplexSegment.class);
        final String[] data = new String[]{"", null};
        // When
        final ComplexSegment segment = mapper.map(data);
        // Then
        assertNotNull(segment);
        assertNull(segment.getCode());
        assertNull(segment.getCountry());
    }

}

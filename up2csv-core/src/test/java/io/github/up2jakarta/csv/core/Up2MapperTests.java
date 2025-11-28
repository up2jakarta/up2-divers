package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.misc.acs.BIdOptionalBean;
import io.github.up2jakarta.csv.core.misc.acs.Optional8Bean;
import io.github.up2jakarta.csv.core.misc.cvr.ValidEntity;
import io.github.up2jakarta.csv.core.misc.jpa.NoteEntity;
import io.github.up2jakarta.csv.core.misc.lov.CountryCodeType;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.map.*;
import io.github.up2jakarta.csv.core.misc.map.Inner1Segment.InnerFragment;
import io.github.up2jakarta.csv.core.misc.map.oneshot.AbstractAddress;
import io.github.up2jakarta.csv.core.misc.map.oneshot.ClientSegment;
import io.github.up2jakarta.csv.core.misc.map.oneshot.ComplexAddress;
import io.github.up2jakarta.csv.core.misc.map.oneshot.SimpleAddress;
import io.github.up2jakarta.csv.core.misc.prc.ProcessorBean;
import io.github.up2jakarta.csv.core.misc.vld.Validator3Bean;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputCollector;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_CONVERTER;
import static io.github.up2jakarta.csv.core.Properties.assertFinal;
import static io.github.up2jakarta.csv.data.DataResolver.dynamic;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static org.junit.jupiter.api.Assertions.*;

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
        assertSame(mapper1.node, mapper2.node);
    }

    @Test
    void testCache2() throws BeanException {
        // GIVEN
        final Up2Mapper<ValidEntity, ?> mapper1 = factory.build(ValidEntity.class);
        final Up2Mapper<ValidEntity, ?> mapper2 = factory.build(ValidEntity.class, dynamic());
        // THEN
        assertNotSame(mapper1, mapper2);
        assertNotSame(mapper1.node, mapper2.node);
    }

    @Test
    void testCache3() throws BeanException {
        // GIVEN
        final Up2Mapper<ValidEntity, ?> mapper1 = factory.build(ValidEntity.class);
        final Up2Mapper<ValidEntity, ?> mapper2 = mapper1.toFlatter().toMapper();
        // THEN
        assertNotSame(mapper1, mapper2);
        assertSame(mapper1.node, mapper2.node);
    }

    @Test
    @SuppressWarnings("ALL")
    void testNull() throws BeanException {
        // Given
        final String[] data = null;
        final Up2Mapper<ValidBean, GroupType> mapper = factory.build(ValidBean.class);
        final InputRecord row3 = record(SegmentType.S00, data);
        final InputCollector handler3 = new InputCollector(row3);
        // When
        final ValidBean bean1 = mapper.map(data);
        final ValidBean bean3 = mapper.map(null, handler3);
        final ValidBean bean4 = mapper.map(row3, handler3);
        // Then
        assertNull(bean1);
        assertNull(bean3);
        assertNull(bean4);
    }

    @Test
    void testFastHandler() throws BeanException {
        // Given
        final Up2Mapper<Validator3Bean, GroupType> mapper = factory.build(Validator3Bean.class);
        final InputRecord row = record(SegmentType.S00, ".");
        // When
        final FailureException error = assertThrows(FailureException.class, () -> mapper.map(row));
        // Then
        assertNotNull(error);
        assertNotNull(error.getCause());
        assertInstanceOf(NumberFormatException.class, error.getCause());
        // Then Error
        assertEquals(ERROR, error.getLevel());
        assertEquals(EC_CONVERTER, error.getCode());
        assertEquals("java.lang.NumberFormatException: No digits found.", error.getMessage());
    }

    @Test
    void testValidRecordable() throws BeanException {
        // Given
        final Up2Mapper<Validator3Bean, GroupType> mapper = factory.build(Validator3Bean.class);
        final InputRecord row = record(SegmentType.S00, "1.");
        // When
        final Validator3Bean bean = mapper.map(row);
        // Then
        assertNotNull(bean);
        assertEquals(BigDecimal.ONE, bean.getAmount());
        assertSame(row, bean.getRecord());
    }

    @Test
    void testInvalidRecordable() throws BeanException {
        // Given
        final Up2Mapper<Validator3Bean, GroupType> mapper = factory.build(Validator3Bean.class);
        final UnitRecord<SegmentType> row = new UnitRecord<>(SegmentType.S00, "1.");
        // When
        final AccessException error = assertThrows(AccessException.class, () -> mapper.map(row));
        // Then
        assertNotNull(error.getCause());
        assertInstanceOf(ClassCastException.class, error.getCause());
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
        final Up2Flatter<ClientSegment, GroupType> format = factory.format(ClientSegment.class);
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
        final Up2Flatter<SimpleSegment, GroupType> format = factory.format(SimpleSegment.class);
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
        final Up2Flatter<SimpleSegment, GroupType> format = factory.format(SimpleSegment.class);
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
        final Up2Flatter<SimpleSegment, GroupType> format = factory.format(SimpleSegment.class);
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
        final Up2Flatter<ComplexSegment, GroupType> format = factory.format(ComplexSegment.class);
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
        final Up2Flatter<ExtendedCountryBean, GroupType> format = factory.format(ExtendedCountryBean.class);
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
        final Up2Flatter<GenericCountryBean, GroupType> format = factory.format(GenericCountryBean.class);
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
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("local class is not allowed", thrown.getMessage());
    }

    @Test
    void testAbstractClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(AbstractAddress.class));
        // THEN
        assertEquals(AbstractAddress.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("abstract class is not allowed", thrown.getMessage());
    }

    @Test
    void testInterface() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Segment.class));
        // THEN
        assertEquals(Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("interface is not allowed", thrown.getMessage());
    }

    @Test
    void testGenericClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test4Segment.class));
        // THEN
        assertEquals(Test4Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("generic class is not allowed", thrown.getMessage());
    }

    @Test
    void testInner1Class() throws BeanException {
        // When
        final Up2Mapper<Inner1Segment, GroupType> mapper = factory.build(Inner1Segment.class).toFlatter().toMapper();
        final Inner1Segment bean = mapper.map("TU");
        // THEN
        assertNotNull(bean);
        assertEquals("TU", bean.getId());
        assertNotNull(bean.getFragment());
        assertEquals("Test", bean.getFragment().getName());
    }

    @Test
    void testInner2Class() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Inner2Segment.class));
        // THEN
        assertEquals(Inner1Segment.InnerFragment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("inner class is not allowed outside enclosing segments: Inner2Segment, Inner2Segment.InnerFragment", thrown.getMessage());
    }

    @Test
    void testInner3Class() throws BeanException {
        // Given
        final Up2Mapper<Inner3Segment, GroupType> mapper = factory.build(Inner3Segment.class);
        final String[] data = new String[]{"AAB", "ABBESSI"};
        // When
        final Inner3Segment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getId());
        assertNotNull(bean.getFragment());
        assertEquals("ABBESSI", bean.getFragment().getName());
        // When Unmapping
        final Up2Flatter<Inner3Segment, GroupType> format = factory.format(Inner3Segment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testInner4Class() throws BeanException {
        // When
        final Up2Mapper<Inner4Segment, GroupType> mapper = factory.build(Inner4Segment.class).toFlatter().toMapper();
        final Inner4Segment bean = mapper.map("TU");
        // THEN
        assertNotNull(bean);
        assertEquals("TU", bean.fragment().getId());
        assertNotNull(bean.fragment().getFragment());
        assertEquals("Test", bean.fragment().getFragment().getName());
    }

    @Test
    void testInner5Class() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Inner5Segment.class));
        // THEN
        assertEquals(Inner5Segment.class, thrown.getSource());
        assertEquals("fragment", thrown.getLocator());
        assertEquals("inner class is not allowed inside enclosing record: Inner5Segment", thrown.getMessage());
    }

    @Test
    void testInner6Class() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Inner6Segment.class));
        // THEN
        assertEquals(Inner6Segment.SFragment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("inner class is not allowed inside enclosing segment: Inner6Segment", thrown.getMessage());
    }

    @Test
    void testInnerSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(InnerFragment.class));
        // THEN
        assertEquals(InnerFragment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(TestRecursive1Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("cyclic fragment is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveInheritance() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(TestRecursive3Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("cyclic fragment is not allowed", thrown.getMessage());
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
    void testFragment() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test1Segment.class));
        // THEN
        assertEquals(Test1Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("type must implements Segment", thrown.getMessage());
    }

    @Test
    void testFragmentOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test2Segment.class));
        // THEN
        assertEquals(Test2Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("@Fragment[value] must be positive", thrown.getMessage());
    }

    @Test
    void testPositionOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test3Segment.class));
        // THEN
        assertEquals(Test3Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("@Position[value] must be positive", thrown.getMessage());
    }

    @Test
    void testValidFinal1() throws Exception {
        // GIVEN
        final Up2Mapper<Test6Segment, GroupType> mapper1 = factory.build(Test6Segment.class);
        // WHEN
        final Up2Mapper<Test6Segment, GroupType> mapper2 = mapper1.toFlatter().toMapper();
        // THEN
        assertFinal(mapper1);
        assertFinal(mapper2);
    }

    @Test
    void testValidFinal2() throws Exception {
        // GIVEN
        final Up2Mapper<Test8Segment, GroupType> mapper1 = factory.build(Test8Segment.class);
        // WHEN
        final Up2Mapper<Test8Segment, GroupType> mapper2 = mapper1.toFlatter().toMapper();
        // THEN
        assertFinal(mapper1);
        assertFinal(mapper2);
    }

    @Test
    void testFieldStatic() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test7Segment.class));
        // THEN
        assertEquals(Test7Segment.class, thrown.getSource());
        assertEquals("staticField", thrown.getLocator());
        assertEquals("must not be static", thrown.getMessage());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testValidRecursive() throws BeanException {
        // When
        final Up2Mapper<TestRecursive6Segment, GroupType> mapper = factory.build(TestRecursive6Segment.class);
        final List<BSProperty<?, ?, GroupType>> fields = mapper.node.properties;
        // THEN
        assertEquals(3, fields.size());
        {
            final BSProperty<?, ?, GroupType> property = fields.getFirst();
            assertEquals("id", property.getName());
            assertEquals(0, property.offset);
        }
        {
            final BSProperty<?, ?, GroupType> property = fields.get(1);
            assertEquals("any", property.getName());
            assertEquals(1, property.offset);
        }
        {
            final BSProperty<?, ?, GroupType> fragment = fields.get(2);
            assertEquals("fragment", fragment.getName());
            assertEquals(2, fragment.offset);
            assertInstanceOf(PFragment.class, fragment);
            final List<BSProperty<?, ?, GroupType>> fProperties = ((PFragment<?, ?, GroupType>) fragment).node.properties;
            assertEquals(2, fProperties.size());
            {
                final BSProperty<?, ?, GroupType> property = fProperties.getFirst();
                assertEquals("id", property.getName());
                assertEquals(2, property.offset);
            }
            {
                final BSProperty<?, ?, GroupType> property = fProperties.get(1);
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
        final List<BSProperty<?, ?, GroupType>> fields = mapper.node.properties;
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
        final List<BSProperty<?, ?, GroupType>> fields = mapper.node.properties;
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
        final List<BSProperty<?, ?, GroupType>> fields = mapper.node.properties;
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
        final List<? extends IComplianceEvent<GroupType>> violations = mapper.toFlatter().validate(segment);
        // Then
        assertNotNull(segment);
        assertEquals(0, violations.size());
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

    @Test
    void testOptional() throws BeanException {
        // Given
        final Up2Mapper<BIdOptionalBean, ?> mapper = factory.build(BIdOptionalBean.class);
        // When
        final BIdOptionalBean bean = mapper.map();
        // Then
        assertNotNull(bean.fragment);
        assertFalse(bean.fragment.isEmpty());
        assertNotNull(bean.fragment.get().id);
        assertTrue(bean.fragment.get().id.isEmpty());
    }

    @Test
    void testNullableOptional() throws BeanException {
        // Given
        final Up2Mapper<Optional8Bean, ?> mapper = factory.build(Optional8Bean.class);
        // When
        final Optional8Bean bean = mapper.map();
        // Then
        assertNotNull(bean.fragment);
        assertTrue(bean.fragment.isEmpty());
    }

}

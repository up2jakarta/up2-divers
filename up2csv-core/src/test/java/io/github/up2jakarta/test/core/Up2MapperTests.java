package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.acs.*;
import io.github.up2jakarta.test.core.misc.cvr.ValidEntity;
import io.github.up2jakarta.test.core.misc.jpa.NoteEntity;
import io.github.up2jakarta.test.core.misc.lov.CountryCodeType;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.test.core.misc.map.*;
import io.github.up2jakarta.test.core.misc.map.Inner1Segment.InnerFragment;
import io.github.up2jakarta.test.core.misc.map.oneshot.AbstractAddress;
import io.github.up2jakarta.test.core.misc.map.oneshot.ClientSegment;
import io.github.up2jakarta.test.core.misc.map.oneshot.ComplexAddress;
import io.github.up2jakarta.test.core.misc.map.oneshot.SimpleAddress;
import io.github.up2jakarta.test.core.misc.prc.ProcessorBean;
import io.github.up2jakarta.test.core.misc.vld.Validator3Bean;
import io.github.up2jakarta.test.impl.InputCollector;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_CONVERTER;
import static io.github.up2jakarta.csv.data.TermResolver.header;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static io.github.up2jakarta.test.core.Reflections.list;
import static io.github.up2jakarta.test.core.Reflections.node;
import static io.github.up2jakarta.test.fmt.misc.Tests.record;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2MapperTests {

    private final Up2Factory<TermType> factory;

    @Autowired
    Up2MapperTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    @Test
    void testCache1() throws BeanException {
        // Given
        final Up2Mapper<ValidBean, TermType> mapper1 = factory.mapper(ValidBean.class);
        // When
        final Up2Mapper<ValidBean, TermType> mapper2 = factory.mapper(ValidBean.class);
        // Then
        assertNotSame(mapper1, mapper2);
        assertSame(node(mapper1), node(mapper2));
    }

    @Test
    void testCache2() throws BeanException {
        // GIVEN
        final Up2Mapper<ValidEntity, ?> mapper1 = factory.mapper(ValidEntity.class);
        final Up2Mapper<ValidEntity, ?> mapper2 = new Up2Factory<>(factory, header()).mapper(ValidEntity.class);
        // THEN
        assertNotSame(mapper1, mapper2);
        assertNotSame(node(mapper1), node(mapper2));
    }

    @Test
    void testCache3() throws BeanException {
        // GIVEN
        final Up2Mapper<ValidEntity, ?> mapper1 = factory.mapper(ValidEntity.class);
        final Up2Mapper<ValidEntity, ?> mapper2 = mapper1.toFlatter().toMapper();
        // THEN
        assertNotSame(mapper1, mapper2);
        assertSame(node(mapper1), node(mapper2));
    }

    @Test
    void testReverse1() throws BeanException {
        // GIVEN
        final Up2Mapper<ValidEntity, ?> mapper = factory.mapper(ValidEntity.class);
        final Up2Flatter<ValidEntity, ?> flatter = factory.flatter(ValidEntity.class);
        // THEN
        assertSame(list(flatter), list(mapper));
    }

    @Test
    void testReverse2() throws BeanException {
        // GIVEN
        final Up2Flatter<Access4Bean, ?> flatter1 = factory.flatter(Access4Bean.class);
        final Up2Flatter<ValidEntity, ?> flatter2 = factory.flatter(ValidEntity.class);
        final Up2Mapper<ValidEntity, ?> mapper = flatter2.toMapper();
        // THEN
        assertNotSame(list(flatter1), list(mapper));
        assertSame(list(mapper), list(mapper));
    }

    @Test
    void testReverse3() throws BeanException {
        // GIVEN
        final Up2Mapper<Final2Segment, ?> mapper = factory.mapper(Final2Segment.class);
        final Up2Flatter<Final2Segment, ?> flatter = factory.flatter(Final2Segment.class);
        // THEN
        assertNotSame(list(flatter), list(mapper));
    }

    @Test
    @SuppressWarnings("ALL")
    void testNull() throws BeanException {
        // Given
        final String[] data = null;
        final Up2Mapper<ValidBean, TermType> mapper = factory.mapper(ValidBean.class);
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
        final Up2Mapper<Validator3Bean, TermType> mapper = factory.mapper(Validator3Bean.class);
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
        final Up2Mapper<Validator3Bean, TermType> mapper = factory.mapper(Validator3Bean.class);
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
        final Up2Mapper<Validator3Bean, TermType> mapper = factory.mapper(Validator3Bean.class);
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
        final Up2Mapper<ClientSegment, TermType> mapper = factory.mapper(ClientSegment.class);
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
        final Up2Flatter<ClientSegment, TermType> format = factory.flatter(ClientSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testDefault1() throws BeanException {
        // Given
        final Up2Mapper<Default1Bean, TermType> mapper = factory.mapper(Default1Bean.class);
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
        final Up2Mapper<Default2Bean, TermType> mapper = factory.mapper(Default2Bean.class);
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
        final Up2Mapper<SimpleSegment, TermType> mapper = factory.mapper(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", "Software engineer"};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("Software engineer", bean.getRole());
        // When Unmapping
        final Up2Flatter<SimpleSegment, TermType> format = factory.flatter(SimpleSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validBeanMoreColumns() throws BeanException {
        // Given
        final Up2Mapper<SimpleSegment, TermType> mapper = factory.mapper(SimpleSegment.class);
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
        final Up2Mapper<SimpleSegment, TermType> mapper = factory.mapper(SimpleSegment.class);
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
        final Up2Mapper<SimpleSegment, TermType> mapper = factory.mapper(SimpleSegment.class);
        final Up2Flatter<SimpleSegment, TermType> format = factory.flatter(SimpleSegment.class);
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
        final Up2Mapper<SimpleSegment, TermType> mapper = factory.mapper(SimpleSegment.class);
        final String[] data = new String[]{"AAB", "ABBESSI", ""};
        // When
        final SimpleSegment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getCode());
        assertEquals("ABBESSI", bean.getName());
        assertEquals("", bean.getRole());
        // When Unmapping
        final Up2Flatter<SimpleSegment, TermType> format = factory.flatter(SimpleSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validComplexBean() throws BeanException {
        // Given
        final Up2Mapper<ComplexSegment, TermType> mapper = factory.mapper(ComplexSegment.class);
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
        final Up2Flatter<ComplexSegment, TermType> format = factory.flatter(ComplexSegment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validExtendedBean() throws BeanException {
        // Given
        final Up2Mapper<ExtendedCountryBean, TermType> mapper = factory.mapper(ExtendedCountryBean.class);
        final String[] data = new String[]{"TND", "TN", "Tunisia"};
        // When
        final ExtendedCountryBean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
        // When Unmapping
        final Up2Flatter<ExtendedCountryBean, TermType> format = factory.flatter(ExtendedCountryBean.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void validGenericBean() throws BeanException {
        // Given
        final Up2Mapper<GenericCountryBean, TermType> mapper = factory.mapper(GenericCountryBean.class);
        final String[] data = new String[]{"TND", "TN", "Tunisia"};
        // When
        final GenericCountryBean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("TND", bean.getCurrency());
        assertEquals("TN", bean.getCode());
        assertEquals("Tunisia", bean.getName());
        // When Unmapping
        final Up2Flatter<GenericCountryBean, TermType> format = factory.flatter(GenericCountryBean.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testProcessors() throws BeanException {
        // Given
        final Up2Mapper<ProcessorBean, TermType> mapper = factory.mapper(ProcessorBean.class);
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
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(LocalSegment.class));
        // THEN
        assertEquals(LocalSegment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("local class is not allowed", thrown.getMessage());
    }

    @Test
    void testAbstractClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(AbstractAddress.class));
        // THEN
        assertEquals(AbstractAddress.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("abstract class is not allowed", thrown.getMessage());
    }

    @Test
    void testInterface() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Segment.class));
        // THEN
        assertEquals(Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("interface is not allowed", thrown.getMessage());
    }

    @Test
    void testGenericClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Test4Segment.class));
        // THEN
        assertEquals(Test4Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("generic class is not allowed", thrown.getMessage());
    }

    @Test
    void testInner1Class() throws BeanException {
        // When
        final Up2Mapper<Inner1Segment, TermType> mapper = factory.mapper(Inner1Segment.class).toFlatter().toMapper();
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
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Inner2Segment.class));
        // THEN
        assertEquals(Inner1Segment.InnerFragment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("inner class is not allowed outside enclosing segments: Inner2Segment, Inner2Segment.InnerFragment", thrown.getMessage());
    }

    @Test
    void testInner3Class() throws BeanException {
        // Given
        final Up2Mapper<Inner3Segment, TermType> mapper = factory.mapper(Inner3Segment.class);
        final String[] data = new String[]{"AAB", "ABBESSI"};
        // When
        final Inner3Segment bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals("AAB", bean.getId());
        assertNotNull(bean.getFragment());
        assertEquals("ABBESSI", bean.getFragment().getName());
        // When Unmapping
        final Up2Flatter<Inner3Segment, TermType> format = factory.flatter(Inner3Segment.class);
        final String[] out = format.unmap(bean);
        assertArrayEquals(data, out);
    }

    @Test
    void testInner4Class() throws BeanException {
        // When
        final Up2Mapper<Inner4Segment, TermType> mapper = factory.mapper(Inner4Segment.class).toFlatter().toMapper();
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
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Inner5Segment.class));
        // THEN
        assertEquals(Inner5Segment.class, thrown.getSource());
        assertEquals("fragment", thrown.getLocator());
        assertEquals("inner class is not allowed inside enclosing record: Inner5Segment", thrown.getMessage());
    }

    @Test
    void testInner6Class() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Inner6Segment.class));
        // THEN
        assertEquals(Inner6Segment.SFragment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("inner class is not allowed inside enclosing segment: Inner6Segment", thrown.getMessage());
    }

    @Test
    void testInnerSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(InnerFragment.class));
        // THEN
        assertEquals(InnerFragment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(TestRecursive1Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("cyclic fragment is not allowed", thrown.getMessage());
    }

    @Test
    void testRecursiveInheritance() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(TestRecursive3Segment.class));
        // THEN
        assertEquals(TestRecursive1Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("cyclic fragment is not allowed", thrown.getMessage());
    }

    @Test
    void testFragment() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Test1Segment.class));
        // THEN
        assertEquals(Test1Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("type must implements Segment", thrown.getMessage());
    }

    @Test
    void testFragmentOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Test2Segment.class));
        // THEN
        assertEquals(Test2Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("@Fragment[value] must be positive", thrown.getMessage());
    }

    @Test
    void testPositionOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Test3Segment.class));
        // THEN
        assertEquals(Test3Segment.class, thrown.getSource());
        assertEquals("p", thrown.getLocator());
        assertEquals("@Position[value] must be positive", thrown.getMessage());
    }

    @Test
    void testFieldStatic() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Test7Segment.class));
        // THEN
        assertEquals(Test7Segment.class, thrown.getSource());
        assertEquals("staticField", thrown.getLocator());
        assertEquals("must not be static", thrown.getMessage());
    }

    @Test
    void testValidRecursive1() throws BeanException {
        // Given
        final Up2Mapper<TestRecursive7Override, ?> mapper = factory.mapper(TestRecursive7Override.class);
        final Up2Flatter<TestRecursive7Override, ?> flatter = mapper.toFlatter();
        final String[] data = new String[]{"00", "11", "22"};
        // When
        final TestRecursive7Override bean = mapper.map(data);
        final String[] out = flatter.unmap(bean);
        // THEN
        assertEquals(3, list(mapper).size());
        assertArrayEquals(data, out);
    }

    @Test
    void testValidRecursive2() throws BeanException {
        // GIVEN
        final Up2Mapper<TestRecursive8Override, ?> mapper = factory.mapper(TestRecursive8Override.class);
        final Up2Flatter<TestRecursive8Override, ?> flatter = mapper.toFlatter();
        final String[] data = new String[]{"00", "11", "22", "33", "44", "55", "66"};
        // WHEN
        final TestRecursive8Override bean = mapper.map(data);
        final String[] out = flatter.unmap(bean);
        // THEN
        assertEquals(3, list(mapper).size());
        assertArrayEquals(data, out);
    }

    @Test
    void testValidRecursive3() throws BeanException {
        // GIVEN
        final Up2Mapper<TestRecursive6Segment, ?> mapper = factory.mapper(TestRecursive6Segment.class);
        final Up2Flatter<TestRecursive6Segment, ?> flatter = mapper.toFlatter();
        final String[] data = new String[]{"00", "11", "22", "33"};
        // WHEN
        final TestRecursive6Segment bean = mapper.map(data);
        final String[] out = flatter.unmap(bean);
        // THEN
        assertEquals(3, list(mapper).size());
        assertArrayEquals(data, out);
    }

    @Test
    void testMappingNoOrderBean() throws Exception {
        // GIVEN
        final Up2Mapper<NoOrderBean, TermType> mapper = factory.mapper(NoOrderBean.class);
        final Up2Flatter<NoOrderBean, ?> flatter = mapper.toFlatter();
        final String[] data = new String[]{"00", "IGNORE", "22"};
        // WHEN
        final NoOrderBean bean = mapper.map(data);
        final String[] out = flatter.unmap(bean);
        data[1] = null;
        // THEN
        assertEquals(2, list(mapper).size());
        assertArrayEquals(data, out);
    }

    @Test
    void testMappingNoPositionBean() throws Exception {
        // GIVEN
        final Up2Mapper<NoPositionBean, ?> mapper = factory.mapper(NoPositionBean.class);
        final Up2Flatter<NoPositionBean, ?> flatter = mapper.toFlatter();
        final String[] data = new String[]{"00", "11"};
        // WHEN
        final NoPositionBean bean = mapper.map(data);
        final String[] out = flatter.unmap(bean);
        // THEN
        assertEquals(2, list(mapper).size());
        assertArrayEquals(data, out);
    }

    @Test
    void testRequired1() throws BeanException {
        // Given
        final Up2Mapper<NoteEntity, TermType> mapper = factory.mapper(NoteEntity.class);
        final String[] data = new String[]{"ZZZ", "Content", null, "???", "T2", "EUR"};
        // When
        final NoteEntity segment = mapper.map(data);
        final List<? extends IComplianceEvent<TermType>> violations = mapper.toFlatter().validate(segment);
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
        final Up2Mapper<NoteEntity, TermType> mapper = factory.mapper(NoteEntity.class);
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
        final Up2Mapper<ComplexSegment, TermType> mapper = factory.mapper(ComplexSegment.class);
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
        final Up2Mapper<BIdOptionalSegment, ?> mapper = factory.mapper(BIdOptionalSegment.class);
        // When
        final BIdOptionalSegment bean = mapper.map();
        // Then
        assertNotNull(bean.fragment);
        assertFalse(bean.fragment.isEmpty());
        assertNotNull(bean.fragment.get().id);
        assertTrue(bean.fragment.get().id.isEmpty());
    }

    @Test
    void testNullableOptional() throws BeanException {
        // Given
        final Up2Mapper<Optional8Bean, ?> mapper = factory.mapper(Optional8Bean.class);
        // When
        final Optional8Bean bean = mapper.map();
        // Then
        assertNotNull(bean.fragment);
        assertTrue(bean.fragment.isEmpty());
    }

    @Test
    void testBusinessEquality() throws BeanException {
        // GIVEN
        final Up2Mapper<BIdMultipleSegment, ?> mapper = factory.mapper(BIdMultipleSegment.class);
        // WHEN
        final BIdMultipleSegment s2 = mapper.map("B", "2");
        final BIdMultipleSegment s3 = mapper.map("B", "2");
        // THEN
        assertTrue(mapper.equals(s2, s3));
        assertTrue(mapper.equals(mapper.map(), mapper.map()));
        assertTrue(mapper.equals(mapper.map(""), mapper.map("")));
        assertTrue(mapper.equals(mapper.map("B"), mapper.map("B")));
        assertTrue(mapper.equals(mapper.map("", "2"), mapper.map("", "2")));

        assertFalse(mapper.equals(mapper.map("A", "2"), s2));
        assertFalse(mapper.equals(mapper.map("B", "1"), s3));

        assertFalse(mapper.equals(mapper.map("B"), s2));
        assertFalse(mapper.equals(mapper.map("", "2"), s3));
    }

}

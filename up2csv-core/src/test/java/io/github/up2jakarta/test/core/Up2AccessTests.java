package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.hdl.SimpleCollector;
import io.github.up2jakarta.csv.data.HeaderType;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.acs.*;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.csv.data.TermResolver.header;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static io.github.up2jakarta.lov.core.Localizable.CREATOR;
import static io.github.up2jakarta.test.core.misc.acs.Final1Segment.Source.CSV;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2AccessTests {

    private final Up2Factory<?> factory;

    @Autowired
    Up2AccessTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    private static <T extends FinalSegment> void assertSolution(Up2Mapper<T, ?> mapper) throws BeanException {
        // Given
        final Up2Flatter<T, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10", "V1", "V2"};
        // When
        final T bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertEquals(10, bean.getKey());
        assertEquals("V1", bean.getCode());
        assertEquals("V2", bean.getValue());
        assertArrayEquals(data, out);
    }

    private static <T extends FinalSegment> void assert1Validation(Up2Mapper<T, HeaderType> mapper) throws BeanException {
        // Given
        final Up2Flatter<T, HeaderType> format = mapper.toFlatter();
        final String[] data = new String[]{"10", "V1", null};
        final SimpleCollector<HeaderType> collector = new SimpleCollector<>();
        // When
        final T bean = mapper.map(collector, data);
        final String[] out = format.unmap(bean);
        final List<? extends IEvent<?>> events = collector.toList();
        // Then
        assertEquals(10, bean.getKey());
        assertEquals("V1", bean.getCode());
        assertNull(bean.getValue());
        assertArrayEquals(data, out);
        // Event
        assertEquals(1, events.size());
        final IEvent<?> event = events.getFirst();
        assertEquals(2, event.getOffset());
        assertEquals("must not be blank", event.getMessage());
    }

    private static <T extends FinalSegment> void assert2Validation(Up2Mapper<T, HeaderType> mapper) throws BeanException {
        // Given
        final Up2Flatter<T, HeaderType> format = mapper.toFlatter();
        final String[] data = new String[]{"10", null, "V2"};
        final SimpleCollector<HeaderType> collector = new SimpleCollector<>();
        // When
        final T bean = mapper.map(collector, data);
        final String[] out = format.unmap(bean);
        final List<? extends IEvent<?>> events = collector.toList();
        // Then
        assertEquals(10, bean.getKey());
        assertNull(bean.getCode());
        assertEquals("V2", bean.getValue());
        assertArrayEquals(data, out);
        // Event
        assertEquals(1, events.size());
        final IEvent<?> event = events.getFirst();
        assertEquals(1, event.getOffset());
        assertEquals("must not be blank", event.getMessage());
    }

    @Test
    void valid1Bean() throws BeanException {
        // Given
        final Up2Mapper<Access1Bean, ?> mapper = factory.mapper(Access1Bean.class);
        final Up2Flatter<Access1Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access1Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid2Bean() throws BeanException {
        // Given
        final Up2Mapper<Access2Bean, ?> mapper = factory.mapper(Access2Bean.class);
        final Up2Flatter<Access2Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access2Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid3Bean() throws BeanException {
        // Given
        final Up2Mapper<Access31Bean, ?> mapper = factory.mapper(Access31Bean.class);
        final Up2Flatter<Access31Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access31Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void invalid3Bean() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Access32Bean.class));
        // Then
        assertEquals(Access32Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("getter not found", thrown.getMessage());
    }

    @Test
    void multipleCreators() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Final8Segment.class));
        // Then
        assertEquals(Final8Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("one and only one constructor must be annotated with @Creator", thrown.getMessage());
    }

    @Test
    void mixedProperties() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Final9Segment.class));
        // Then
        assertEquals(Final9Segment.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("mix final and writable properties is not allowed", thrown.getMessage());
    }

    @Test
    void final10Solution() throws BeanException {
        // WHEN
        var mapper = new Up2Factory<>(factory, header()).mapper(Final10Segment.class);
        // THEN
        assertSolution(mapper);
        assert1Validation(mapper);
        assert2Validation(mapper);
    }

    @Test
    void final11Solution() throws BeanException {
        // WHEN
        var mapper = new Up2Factory<>(factory, header()).mapper(Final11Segment.class);
        // THEN
        assertSolution(mapper);
        assert1Validation(mapper);
        assert2Validation(mapper);
    }

    @Test
    void optional10Solution() throws BeanException {
        // WHEN
        var mapper = new Up2Factory<>(factory, header()).mapper(Optional10Segment.class);
        // THEN
        assertSolution(mapper);
        assert1Validation(mapper);
        assert2Validation(mapper);
    }

    @Test
    void optional11Solution() throws BeanException {
        // WHEN
        var mapper = new Up2Factory<>(factory, header()).mapper(Optional11Segment.class);
        // THEN
        assertSolution(mapper);
        assert1Validation(mapper);
        assert2Validation(mapper);
    }

    @Test
    void record7Solution() throws BeanException {
        // WHEN
        var mapper = new Up2Factory<>(factory, header()).mapper(Record7Segment.class);
        // THEN
        assertSolution(mapper);
        assert1Validation(mapper);
        assert2Validation(mapper);
    }

    @Test
    void record8Solution() throws BeanException {
        // WHEN
        var mapper = new Up2Factory<>(factory, header()).mapper(Record8Segment.class);
        // THEN
        assertSolution(mapper);
        assert1Validation(mapper);
        assert2Validation(mapper);
    }

    @Test
    void valid1Wrapper() throws BeanException {
        // Given
        final Up2Mapper<Wrapper1Bean, ?> mapper = factory.mapper(Wrapper1Bean.class);
        final Up2Flatter<Wrapper1Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10"};
        // When
        final Wrapper1Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid2Wrapper() throws BeanException {
        // Given
        final Up2Mapper<Wrapper2Bean, ?> mapper = factory.mapper(Wrapper2Bean.class);
        final Up2Flatter<Wrapper2Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10"};
        // When
        final Wrapper2Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid1Generic() throws BeanException {
        // Given
        final Up2Mapper<Generic1Bean, ?> mapper = factory.mapper(Generic1Bean.class);
        final Up2Flatter<Generic1Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10"};
        // When
        final Generic1Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid2Generic() throws BeanException {
        // Given
        final Up2Mapper<Generic2Bean, ?> mapper = factory.mapper(Generic2Bean.class);
        final Up2Flatter<Generic2Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10"};
        // When
        final Generic2Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid3Generic() throws BeanException {
        // Given
        final Up2Mapper<Generic3Bean, ?> mapper = factory.mapper(Generic3Bean.class);
        final Up2Flatter<Generic3Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10"};
        // When
        final Generic3Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid4Generic() throws BeanException {
        // Given
        final Up2Mapper<Generic4Bean, ?> mapper = factory.mapper(Generic4Bean.class);
        final Up2Flatter<Generic4Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10"};
        // When
        final Generic4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validAccessors() throws BeanException {
        // Given
        final Up2Mapper<Access4Bean, ?> mapper = factory.mapper(Access4Bean.class);
        final Up2Flatter<Access4Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validGenericBean1() throws BeanException {
        // Given
        final Up2Mapper<Access5Bean, ?> mapper = factory.mapper(Access5Bean.class);
        final Up2Flatter<Access5Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB", "true"};
        // When
        final Access5Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validGenericBean2() throws BeanException {
        // When
        final Up2Mapper<Access7Bean, ?> mapper = factory.mapper(Access7Bean.class);
        // Then
        assertNotNull(mapper);
        assertNotNull(mapper.toFlatter());
    }

    @Test
    void invalidStaticBean1() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Access8Bean.class));
        // Then
        assertEquals(Access8Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("setter not found", thrown.getMessage());
    }

    @Test
    void invalidStaticBean2() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Access8Bean.class));
        // Then
        assertEquals(Access8Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("getter not found", thrown.getMessage());
    }

    @Test
    void invalidBridgeBean1() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Access9Bean.class));
        // Then
        assertEquals(Access9Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("invalid setter parameter type", thrown.getMessage());
    }

    @Test
    void invalidBridgeBean2() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Access9Bean.class));
        // Then
        assertEquals(Access9Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("invalid getter return type", thrown.getMessage());
    }

    @Test
    void invalidCreatorBean() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Access10Bean.class));
        // Then
        assertEquals(Access10Bean.class, thrown.getSource());
        assertEquals(CREATOR, thrown.getLocator());
        assertEquals("date should be of type java.util.Date", thrown.getMessage());
    }

    @Test
    void validConstructor() throws BeanException {
        // Given
        final Up2Mapper<Access6Bean, ?> mapper = factory.mapper(Access6Bean.class);
        final Up2Flatter<Access6Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access6Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord1Bean() throws BeanException {
        // Given
        final Up2Mapper<Record1Bean, ?> mapper = factory.mapper(Record1Bean.class);
        final Up2Flatter<Record1Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Record1Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord2Bean() throws BeanException {
        // Given
        final Up2Mapper<Record2Bean, ?> mapper = factory.mapper(Record2Bean.class);
        final Up2Flatter<Record2Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB", "Record"};
        // When
        final Record2Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord3Bean() throws BeanException {
        // Given
        final Up2Mapper<Record3Bean, ?> mapper = factory.mapper(Record3Bean.class);
        final Up2Flatter<Record3Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Record3Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord4Bean() throws BeanException {
        // Given
        final Up2Mapper<Record4Bean, ?> mapper = factory.mapper(Record4Bean.class);
        final Up2Flatter<Record4Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Record4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord5Bean() throws BeanException {
        // Given
        final Up2Mapper<Record5Bean, ?> mapper = factory.mapper(Record5Bean.class);
        final Up2Flatter<Record5Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{null, "Test"};
        // When
        final Record5Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(new String[]{"0", "Test"}, out);
    }

    @Test
    void validRecord6Bean() throws BeanException {
        // Given
        final Up2Mapper<Record6Bean, ?> mapper = factory.mapper(Record6Bean.class);
        final Up2Flatter<Record6Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{null, "Test"};
        // When
        final Record6Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(new String[]{"0", "Test"}, out);
    }

    @Test
    void validOptionalIntBean() throws BeanException {
        final Up2Mapper<OptionalIntBean, ?> mapper = factory.mapper(OptionalIntBean.class);
        final Up2Flatter<OptionalIntBean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1"};
            // When
            final OptionalIntBean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        {
            // Given
            final String[] data = new String[]{null};
            // When
            final OptionalIntBean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertSame(OptionalInt.empty(), bean.id());
        }
    }

    @Test
    void validOptionalLongBean() throws BeanException {
        final Up2Mapper<OptionalLongBean, ?> mapper = factory.mapper(OptionalLongBean.class);
        final Up2Flatter<OptionalLongBean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1"};
            // When
            final OptionalLongBean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        {
            // Given
            final String[] data = new String[]{null};
            // When
            final OptionalLongBean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertSame(OptionalLong.empty(), bean.id());
        }
    }

    @Test
    void validOptionalDoubleBean() throws BeanException {
        final Up2Mapper<OptionalDoubleBean, ?> mapper = factory.mapper(OptionalDoubleBean.class);
        final Up2Flatter<OptionalDoubleBean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1.99"};
            // When
            final OptionalDoubleBean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        {
            // When
            final OptionalDoubleBean bean = mapper.map("1.559");
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertEquals(1, out.length);
            assertEquals("1.56", out[0]);
        }
        {
            // When
            final OptionalDoubleBean bean = mapper.map("1.551");
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertEquals(1, out.length);
            assertEquals("1.55", out[0]);
        }
        {
            // Given
            final String[] data = new String[]{null};
            // When
            final OptionalDoubleBean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertSame(OptionalDouble.empty(), bean.amount());
        }
    }

    @Test
    void validOptional1Bean() throws BeanException {
        final Up2Mapper<Optional1Bean, ?> mapper = factory.mapper(Optional1Bean.class);
        final Up2Flatter<Optional1Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional1Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        {
            // Given
            final String[] data = new String[]{null, null};
            // When
            final Optional1Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(new String[]{"0", null}, out);
            assertNotNull(bean.getContent());
        }
    }

    @Test
    void validOptional2Bean() throws BeanException {
        final Up2Mapper<Optional2Bean, ?> mapper = factory.mapper(Optional2Bean.class);
        final Up2Flatter<Optional2Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional2Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        // Given
        final String[] data = new String[]{"0", null};
        {
            // When
            final Optional2Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.content());
        }
        {
            // Given
            final Optional2Bean bean = new Optional2Bean(0, Optional.empty());
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
        }
    }

    @Test
    void validOptional3Bean() throws BeanException {
        final Up2Mapper<Optional3Bean, ?> mapper = factory.mapper(Optional3Bean.class);
        final Up2Flatter<Optional3Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional3Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }// Given
        final String[] data = new String[]{null, null};
        {
            // When
            final Optional3Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.getKey());
            assertNotNull(bean.getContent());
        }
        {
            // Given
            final Optional3Bean bean = new Optional3Bean();
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertEquals(Optional.empty(), bean.getKey());
        }
    }

    @Test
    void validOptional4Bean() throws BeanException {
        final Up2Mapper<Optional4Bean, ?> mapper = factory.mapper(Optional4Bean.class);
        final Up2Flatter<Optional4Bean, ?> format = mapper.toFlatter();
        // Given
        final String[] data = new String[]{"1", "Test"};
        // When
        final Optional4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validOptional5Bean() throws BeanException {
        final Up2Mapper<Optional5Bean, ?> mapper = factory.mapper(Optional5Bean.class);
        final Up2Flatter<Optional5Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional5Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        // Given
        final String[] data = new String[]{"0", null};
        {
            // When
            final Optional5Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.getContent());
        }
        {
            // Given
            final Optional5Bean bean = new Optional5Bean();
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertEquals(Optional.empty(), bean.getContent());
        }
    }

    @Test
    void validOptional6Bean() throws BeanException {
        final Up2Mapper<Optional6Bean, ?> mapper = factory.mapper(Optional6Bean.class);
        final Up2Flatter<Optional6Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional6Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        // Given
        final String[] data = new String[]{"0", null};
        {
            // When
            final Optional6Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.content());
        }
        {
            // Given
            final Optional6Bean bean = new Optional6Bean(0, Optional.empty());
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
        }
    }

    @Test
    void validOptional7Bean() throws BeanException {
        // Given
        final Up2Mapper<Optional7Bean, ?> mapper = factory.mapper(Optional7Bean.class);
        final Up2Flatter<Optional7Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"0", null};
        // When
        final Optional7Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
        assertNotNull(bean.getContent()); // Nullable
        assertTrue(bean.getContent().isEmpty());
    }

    @Test
    void validOptional91Bean() throws BeanException {
        final Up2Mapper<Optional91Bean, ?> mapper = factory.mapper(Optional91Bean.class);
        final Up2Flatter<Optional91Bean, ?> format = mapper.toFlatter();
        // Given
        final String[] data = new String[]{"TU"};
        // When
        final Optional91Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validOptional92Bean() throws BeanException {
        // Given
        final Up2Mapper<Optional92Bean, ?> mapper = factory.mapper(Optional92Bean.class);
        // When
        final BeanException thrown = assertThrows(BeanException.class, mapper::toFlatter);
        // Then
        assertEquals(Optional92Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("invalid getter return type", thrown.getMessage());
    }

    @Test
    void testFinal1() throws BeanException {
        // Given
        final Up2Flatter<Final1Segment, ?> format = factory.flatter(Final1Segment.class);
        final Up2Mapper<Final1Segment, ?> mapper = format.toMapper();
        // When
        final Final1Segment bean = mapper.map("TU");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertEquals(CSV, bean.getSource());
        assertArrayEquals(new String[]{"TU"}, data);
    }

    @Test
    void validFinal2() throws BeanException {
        // Given
        final Up2Flatter<Final2Segment, ?> format = factory.flatter(Final2Segment.class);
        final Up2Mapper<Final2Segment, ?> mapper = format.toMapper();
        // When
        final Final2Segment bean = mapper.map("TU", "Test");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test", bean.fragment.value);
        assertArrayEquals(new String[]{"TU", "Test"}, data);
    }

    @Test
    void validFinal3() throws BeanException {
        // Given
        final Up2Flatter<Final3Segment, ?> format = factory.flatter(Final3Segment.class);
        final Up2Mapper<Final3Segment, ?> mapper = format.toMapper();
        // When
        final Final3Segment bean = mapper.map("TU", "Test", "10");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertTrue(bean.fragment.rate.isPresent());
        assertTrue(bean.fragment.value.isPresent());
        assertEquals("Test", bean.fragment.value.get());
        assertEquals(10, bean.fragment.rate.get());
        assertArrayEquals(new String[]{"TU", "Test", "10"}, data);
    }

    @Test
    void validFinal4() throws BeanException {
        // Given
        final Up2Flatter<Final4Segment, ?> format = factory.flatter(Final4Segment.class);
        final Up2Mapper<Final4Segment, ?> mapper = format.toMapper();
        // When
        final Final4Segment bean = mapper.map("TU", "Test");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test", bean.fragment.value);
        assertArrayEquals(new String[]{"TU", "Test"}, data);
    }

    @Test
    void validFinal5() throws BeanException {
        // Given
        final Up2Flatter<Final5Segment, ?> format = factory.flatter(Final5Segment.class);
        final Up2Mapper<Final5Segment, ?> mapper = format.toMapper();
        // When
        final Final5Segment bean = mapper.map("TU", "Test1", "Test2");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test1", bean.fragment.value1);
        assertEquals("Test2", bean.fragment.value2);
        assertEquals(0, bean.fragment.rate1);
        assertEquals(0L, bean.fragment.rate2);
        assertArrayEquals(new String[]{"TU", "Test1", "Test2"}, data);
    }

    @Test
    void validFinal6() throws BeanException {
        // Given
        final Up2Flatter<Final6Segment, ?> format = factory.flatter(Final6Segment.class);
        final Up2Mapper<Final6Segment, ?> mapper = format.toMapper();
        // When
        final Final6Segment bean = mapper.map("TU", "Test");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test", bean.fragment.value);
        assertEquals(bean, bean.fragment.getParent());
        assertArrayEquals(new String[]{"TU", "Test"}, data);
    }

    @Test
    void testFinal7() throws BeanException {
        // Given
        final Up2Flatter<Final7Segment, ?> format = factory.flatter(Final7Segment.class);
        final Up2Mapper<Final7Segment, ?> mapper = format.toMapper();
        // When
        final Final7Segment bean = mapper.map("TU");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code());
        assertEquals(CSV, bean.source());
        assertArrayEquals(new String[]{"TU"}, data);
    }

}

package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.data.HeaderType;
import io.github.up2jakarta.lov.IError;
import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.acs.Access4Bean;
import io.github.up2jakarta.test.core.misc.acs.BIdOptionalSegment;
import io.github.up2jakarta.test.core.misc.acs.Final2Segment;
import io.github.up2jakarta.test.core.misc.cvr.SupportEntity;
import io.github.up2jakarta.test.core.misc.cvr.ValidEntity;
import io.github.up2jakarta.test.core.misc.map.*;
import io.github.up2jakarta.test.impl.InputError;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static io.github.up2jakarta.csv.data.TermResolver.header;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.test.core.Reflections.list;
import static io.github.up2jakarta.test.core.Reflections.node;
import static io.github.up2jakarta.test.impl.SegmentType.S11;
import static io.github.up2jakarta.test.impl.TermType.D001;
import static io.github.up2jakarta.test.impl.TermType.NONE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2FormatTests {

    private final Up2Factory<TermType> factory;

    @Autowired
    Up2FormatTests(Up2Factory<TermType> factory, Container context) {
        this.factory = factory;
    }

    @Test
    void testCache1() throws BeanException {
        // GIVEN
        final Up2Flatter<ValidEntity, ?> flatter1 = factory.flatter(ValidEntity.class);
        final Up2Flatter<ValidEntity, ?> flatter2 = factory.flatter(ValidEntity.class);
        // THEN
        assertNotSame(flatter1, flatter2);
        assertSame(node(flatter1), node(flatter2));
    }

    @Test
    void testCache2() throws BeanException {
        // GIVEN
        final Up2Flatter<ValidEntity, ?> flatter1 = factory.flatter(ValidEntity.class);
        final Up2Flatter<ValidEntity, ?> flatter2 = new Up2Factory<>(factory, header()).flatter(ValidEntity.class);
        // THEN
        assertNotSame(flatter1, flatter2);
        assertNotSame(node(flatter1), node(flatter2));
    }

    @Test
    void testCache3() throws BeanException {
        // GIVEN
        final Up2Flatter<ValidEntity, ?> flatter1 = factory.flatter(ValidEntity.class);
        final Up2Flatter<ValidEntity, ?> flatter2 = flatter1.toMapper().toFlatter();
        // THEN
        assertNotSame(flatter1, flatter2);
        assertSame(node(flatter1), node(flatter2));
    }

    @Test
    void testReverse1() throws BeanException {
        // GIVEN
        final Up2Flatter<ValidEntity, ?> flatter = factory.flatter(ValidEntity.class);
        final Up2Mapper<ValidEntity, ?> mapper = factory.mapper(ValidEntity.class);
        // THEN
        assertSame(list(flatter), list(mapper));
    }

    @Test
    void testReverse2() throws BeanException {
        // GIVEN
        final Up2Flatter<Access4Bean, ?> flatter1 = factory.flatter(Access4Bean.class);
        final Up2Mapper<ValidEntity, ?> mapper = factory.mapper(ValidEntity.class);
        final Up2Flatter<ValidEntity, ?> flatter2 = mapper.toFlatter();
        // THEN
        assertNotSame(list(flatter1), list(mapper));
        assertSame(list(flatter2), list(mapper));
    }

    @Test
    void testReverse3() throws BeanException {
        // GIVEN
        final Up2Flatter<Final2Segment, ?> flatter = factory.flatter(Final2Segment.class);
        final Up2Mapper<Final2Segment, ?> mapper = factory.mapper(Final2Segment.class);
        // THEN
        assertNotSame(list(flatter), list(mapper));
    }

    @Test
    void testLocalClass() throws BeanException {
        //Given
        class LocalSegment implements Segment {
            private final @Position(0) String test;

            public LocalSegment(String test) {
                this.test = test;
            }
        }
        // When
        final Up2Flatter<LocalSegment, TermType> format = factory.flatter(LocalSegment.class);
        final LocalSegment bean = new LocalSegment("TU");
        final String[] data = format.unmap(bean);
        // THEN
        assertEquals(1, data.length);
        assertArrayEquals(new String[]{bean.test}, data);
    }

    @Test
    void testInnerClass() throws BeanException {
        // When
        final Up2Flatter<Inner1Segment, TermType> format = factory.flatter(Inner1Segment.class);
        final Inner1Segment bean = new Inner1Segment();
        bean.setFragment(bean.new InnerFragment());
        bean.setId("TU");
        final String[] data = format.unmap(bean);
        // THEN
        assertEquals(2, data.length);
        assertArrayEquals(new String[]{bean.getId(), bean.getFragment().getName()}, data);
    }

    @Test
    void testInputError() throws BeanException {
        // Given
        final InputRecord row = new InputRecord("R0099", S11, "I2025", "");
        final String msg = "Text cannot be parsed to a LocalDate";
        final String trace = "java.time.format.DateTimeParseException: " + msg + " ...";
        final InputError error = new InputError(row, 99, D001, 3, new TypeException(ERROR, "CSV-DT", msg), trace);
        // When
        final Up2Flatter<InputError, TermType> format = factory.flatter(InputError.class);
        final String[] export = format.unmap(error);
        // Then
        assertEquals(9, export.length);
        assertArrayEquals(new String[]{"R0099", "11", "I2025", "001", "3", "E", "CSV-DT", msg, trace}, export);
    }

    @Test
    void testInputRecord() throws BeanException {
        // Given
        final InputRecord source = new InputRecord("R0099", S11, "I2025", "D1", "D2");
        // When
        final Up2Flatter<InputRecord, TermType> format = factory.flatter(InputRecord.class);
        final String[] export = format.unmap(source);
        // Then
        assertNotNull(export);
        assertEquals(0, export.length);
    }

    @Test
    void testErrorHeader1() throws BeanException {
        // Given
        final Up2Flatter<InputError, TermType> format = factory.flatter(InputError.class);
        // When
        final String[] export = format.header();
        // Then
        assertEquals(9, export.length);
        assertArrayEquals(new String[]{null, null, null, null, null, null, null, null, null}, export);
    }

    @Test
    void testErrorHeader2() throws BeanException {
        // Given
        final Up2Flatter<InputError, HeaderType> format = new Up2Factory<>(factory, header()).flatter(InputError.class);
        // When
        final String[] export = format.header();
        // Then
        assertEquals(9, export.length);
        assertArrayEquals(new String[]{"Record", "Type", "Pivot", "Data", "Offset", "Level", "Code", "Message", "Stack"}, export);
    }

    @Test
    @SuppressWarnings("ALL")
    void testNull() throws BeanException {
        // Given
        final ValidBean bean = null;
        final Up2Flatter<ValidBean, TermType> format = factory.flatter(ValidBean.class);
        // When
        final String[] out = format.unmap(bean);
        // Then
        assertNull(out);
    }

    @Test
    void testDefault1() throws BeanException {
        // Given
        final Default1Bean bean = new Default1Bean();
        final Up2Flatter<Default1Bean, TermType> mapper = factory.flatter(Default1Bean.class);
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
    void testDefault2Nullable() throws BeanException {
        // Given
        final Up2Flatter<Default2Bean, TermType> mapper = factory.flatter(Default2Bean.class);
        // When
        final String[] out = mapper.unmap(new Default2Bean());
        // Then Bean
        // Then Unmapping
        assertNotNull(out);
        assertEquals(3, out.length);
        assertArrayEquals(new String[]{"*", null, null}, out);
    }

    @Test
    void testDefault3Values() throws BeanException {
        // Given
        final Default3Bean src = new Default3Bean();
        final Up2Flatter<Default3Bean, TermType> mapper = factory.flatter(Default3Bean.class);
        // When
        final String[] out = mapper.unmap(src);
        // Then
        assertNotNull(out);
        assertNull(src.getBean());
        assertEquals(5, out.length);
        assertArrayEquals(new String[]{null, null, "Up2J", "Up2J", null}, out);
        {
            // When Again
            final String[] out2 = mapper.toMapper().toFlatter().unmap(src);
            // Then
            assertNotNull(out2);
            assertNull(src.getBean());
            assertEquals(5, out2.length);
            assertArrayEquals(new String[]{null, null, "Up2J", "Up2J", null}, out2);
        }
    }

    @Test
    void testDefault4Values() throws BeanException {
        // Given
        final Up2Flatter<Default4Bean, TermType> mapper = factory.flatter(Default4Bean.class);
        // When
        final String[] out = mapper.unmap(new Default4Bean());
        // Then
        assertNotNull(out);
        assertEquals(3, out.length);
        assertArrayEquals(new String[]{null, "21", "Up2J"}, out);
    }

    @Test
    void testDefault5Values() throws BeanException {
        // Given
        final Up2Flatter<Default5Bean, TermType> mapper = factory.flatter(Default5Bean.class);
        // When
        final String[] out = mapper.unmap(new Default5Bean());
        // Then
        assertNotNull(out);
        assertEquals(6, out.length);
        assertArrayEquals(new String[]{null, "21", "Up2J", null, "Up2J-1", "Up2J-2"}, out);
    }

    @Test
    void testDefault6Values() throws BeanException {
        // Given
        final Up2Flatter<Default6Bean, TermType> mapper = factory.flatter(Default6Bean.class);
        // When
        final String[] out = mapper.unmap(new Default6Bean());
        // Then
        assertNotNull(out);
        assertEquals(3, out.length);
        assertArrayEquals(new String[]{"Java", null, null}, out);
    }

    @Test
    void testDefault6Input1Error() throws BeanException {
        // Given
        final Up2Flatter<InputError, TermType> mapper = factory.flatter(InputError.class);
        // When
        final IException cause = new TypeException(ERROR, "CSV", "Test");
        final String[] out = mapper.unmap(new InputError(null, 0, NONE, 9, cause, "Error"));
        // Then
        assertNotNull(out);
        assertEquals(9, out.length);
        assertArrayEquals(new String[]{null, null, null, "D00", "9", "E", "CSV", "Test", "Error"}, out);
    }

    @Test
    void testDefault6Input2Error() throws BeanException {
        // Given
        @FragmentOverride(path = {"key", "record"}, value = @Fragment(value = 0, prototype = true))
        @PositionOverride(path = {"key", "record", "type"}, value = @Position(value = 1, defaultValue = "01"))
        final class DefaultError extends InputError {
            public DefaultError(InputRecord row, int order, TermType type, Integer offset, IError cause, String trace) {
                super(row, order, type, offset, cause, trace);
            }
        }
        final IException cause = new TypeException(ERROR, "CSV", "Test");
        final Up2Flatter<DefaultError, TermType> mapper = factory.flatter(DefaultError.class);
        // When
        final String[] out = mapper.unmap(new DefaultError(null, 0, NONE, 9, cause, "Error"));
        // Then
        assertNotNull(out);
        assertEquals(9, out.length);
        assertArrayEquals(new String[]{null, "01", null, "D00", "9", "E", "CSV", "Test", "Error"}, out);
    }

    @Test
    void testEmpty() throws BeanException {
        // Given
        final ValidBean bean = new ValidBean();
        final Up2Flatter<ValidBean, TermType> mapper = factory.flatter(ValidBean.class);
        // When
        final String[] out = mapper.unmap(bean);
        // Then
        assertNotNull(out);
        assertArrayEquals(new String[]{null, null}, out);
    }

    @Test
    void testSize() throws BeanException {
        // GIVEN
        final String[] data = {"100", "Test 100", "2024-07-25", "57.00", "TND", "4.0625", "C62", "Y", "P9D", "TN", "dGVzdA=="};
        final Up2Mapper<SupportEntity, TermType> parser = factory.mapper(SupportEntity.class);
        // WHEN
        final SupportEntity entity = parser.map(data);
        assertNotNull(entity);
        assertArrayEquals("test".getBytes(UTF_8), entity.getBase64());
        // When Unmapping
        final Up2Flatter<SupportEntity, TermType> format = factory.flatter(SupportEntity.class);
        final String[] out = format.unmap(entity);
        // Then
        assertNotNull(out);
        assertEquals(data.length + 1, out.length);
        assertNull(out[0]);
        for (var i = 0; i < data.length; i++) {
            assertEquals(data[i], out[i + 1]);
        }
    }

    @Test
    void testOptional() throws BeanException {
        // Given
        final Up2Flatter<BIdOptionalSegment, ?> format = factory.flatter(BIdOptionalSegment.class);
        // When
        final BIdOptionalSegment bean = new BIdOptionalSegment();
        // Then Null Fragment
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
        // Empty Fragment
        bean.fragment = Optional.empty();
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
        // Null ID
        bean.fragment = Optional.of(new BIdOptionalSegment.OFragment());
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
        // Empty ID
        bean.fragment.get().id = Optional.empty();
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
    }

}

package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.acs.BIdOBean;
import io.github.up2jakarta.csv.core.misc.cvr.SupportEntity;
import io.github.up2jakarta.csv.core.misc.map.*;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.xml.api.PropertyException;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static io.github.up2jakarta.csv.impl.GroupType.D001;
import static io.github.up2jakarta.csv.impl.GroupType.NONE;
import static io.github.up2jakarta.csv.impl.SegmentType.S11;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2FormatTests {

    private final Up2Factory<GroupType> factory;
    private final Up2Factory<DynamicType> fh;

    @Autowired
    Up2FormatTests(Up2Factory<GroupType> factory, BeanContext context, Validator validator) {
        this.factory = factory;
        this.fh = new Up2Factory<>(context, validator, DataTypeResolver.dynamic());
    }

    @Test
    void testLocalClass() throws BeanException {
        //Given
        @Access(AccessType.FIELD)
        class LocalSegment implements Segment {
            private final @Position(0) String test;

            public LocalSegment(String test) {
                this.test = test;
            }
        }
        // When
        final Up2Format<LocalSegment, GroupType> format = factory.format(LocalSegment.class);
        final LocalSegment bean = new LocalSegment("TU");
        final String[] data = format.unmap(bean);
        // THEN
        assertEquals(1, data.length);
        assertArrayEquals(new String[]{bean.test}, data);
    }

    @Test
    void testInnerClass() throws BeanException {
        // When
        final Up2Format<Inner1Segment, GroupType> format = factory.format(Inner1Segment.class);
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
        final InputError error = new InputError(row, 99, D001, 3, new PropertyException(ERROR, "CSV-DT", msg), trace);
        // When
        final Up2Format<InputError, GroupType> format = factory.format(InputError.class);
        final String[] export = format.unmap(error);
        // Then
        assertEquals(9, export.length);
        assertArrayEquals(new String[]{"R0099", "11", "I2025", "0001", "3", "E", "CSV-DT", msg, trace}, export);
    }

    @Test
    void testInputRecord() throws BeanException {
        // Given
        final InputRecord source = new InputRecord("R0099", S11, "I2025", "D1", "D2");
        // When
        final Up2Format<InputRecord, GroupType> format = factory.format(InputRecord.class);
        final String[] export = format.unmap(source);
        // Then
        assertNotNull(export);
        assertEquals(0, export.length);
    }

    @Test
    void testErrorHeader1() throws BeanException {
        // Given
        final Up2Format<InputError, GroupType> format = factory.format(InputError.class);
        // When
        final String[] export = format.header();
        // Then
        assertEquals(9, export.length);
        assertArrayEquals(new String[]{null, null, null, null, null, null, null, null, null}, export);
    }

    @Test
    void testErrorHeader2() throws BeanException {
        // Given
        final Up2Format<InputError, DynamicType> format = fh.format(InputError.class);
        // When
        final String[] export = format.header();
        // Then
        assertEquals(9, export.length);
        assertArrayEquals(new String[]{"Record", "Type", "Pivot", "Data", "Offset", "Severity", "Code", "Message", "Stack"}, export);
    }

    @Test
    @SuppressWarnings("ALL")
    void testNull() throws BeanException {
        // Given
        final ValidBean bean = null;
        final Up2Format<ValidBean, GroupType> format = factory.format(ValidBean.class);
        // When
        final String[] out = format.unmap(bean);
        // Then
        assertNull(out);
    }

    @Test
    void testDefault1() throws BeanException {
        // Given
        final Default1Bean bean = new Default1Bean();
        final Up2Format<Default1Bean, GroupType> mapper = factory.format(Default1Bean.class);
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
        final Up2Format<Default2Bean, GroupType> mapper = factory.format(Default2Bean.class);
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
        final Up2Format<Default3Bean, GroupType> mapper = factory.format(Default3Bean.class);
        // When
        final String[] out = mapper.unmap(src);
        // Then
        assertNotNull(out);
        assertNull(src.getBean());
        assertEquals(5, out.length);
        assertArrayEquals(new String[]{null, "0", "Up2J", "Up2J", "Java"}, out);
        {
            // When Again
            final String[] out2 = mapper.toMapper().toFormat().unmap(src);
            // Then
            assertNotNull(out2);
            assertNull(src.getBean());
            assertEquals(5, out2.length);
            assertArrayEquals(new String[]{null, "0", "Up2J", "Up2J", "Java"}, out2);
        }
    }

    @Test
    void testDefault4Values() throws BeanException {
        // Given
        final Up2Format<Default4Bean, GroupType> mapper = factory.format(Default4Bean.class);
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
        final Up2Format<Default5Bean, GroupType> mapper = factory.format(Default5Bean.class);
        // When
        final String[] out = mapper.unmap(new Default5Bean());
        // Then
        assertNotNull(out);
        assertEquals(6, out.length);
        assertArrayEquals(new String[]{null, "21", "Up2J", "0", "Up2J-1", "Up2J-2"}, out);
    }

    @Test
    void testDefault6PathError() throws BeanException {
        // Given
        final Up2Format<InputError, GroupType> mapper = factory.format(InputError.class);
        // When
        final String[] out = mapper.unmap(new InputError(null, 0, NONE, 9, new PropertyException(ERROR, "CSV", "Test"), "Error"));
        // Then
        assertNotNull(out);
        assertEquals(9, out.length);
        assertArrayEquals(new String[]{null, "00", null, "0000", "9", "E", "CSV", "Test", "Error"}, out);
    }

    @Test
    void testEmpty() throws BeanException {
        // Given
        final ValidBean bean = new ValidBean();
        final Up2Format<ValidBean, GroupType> mapper = factory.format(ValidBean.class);
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
        final Up2Mapper<SupportEntity, GroupType> parser = factory.build(SupportEntity.class);
        // WHEN
        final SupportEntity entity = parser.map(data);
        assertNotNull(entity);
        assertArrayEquals("test".getBytes(UTF_8), entity.getBase64());
        // When Unmapping
        final Up2Format<SupportEntity, GroupType> format = factory.format(SupportEntity.class);
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
        final Up2Format<BIdOBean, ?> format = factory.format(BIdOBean.class);
        // When
        final BIdOBean bean = new BIdOBean();
        // Then Null Fragment
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
        // Empty Fragment
        bean.fragment = Optional.empty();
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
        // Null ID
        bean.fragment = Optional.of(new BIdOBean.OFragment());
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
        // Empty ID
        bean.fragment.get().id = Optional.empty();
        assertEquals(0, format.validate(bean).size());
        assertArrayEquals(new String[]{null}, format.unmap(bean));
    }

}

package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.core.misc.cvr.SupportEntity;
import io.github.up2jakarta.csv.core.misc.map.DefaultBean;
import io.github.up2jakarta.csv.core.misc.map.ValidBean;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static io.github.up2jakarta.csv.impl.GroupType.D001;
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
    void testInputError() throws BeanException {
        // Given
        final InputRecord row = new InputRecord("R0099", S11, "I2025", "");
        final String msg = "Text cannot be parsed to a LocalDate";
        final String trace = "java.time.format.DateTimeParseException: " + msg + " ...";
        final InputError error = new InputError(row, 99, D001, 3, ERROR, "CSV-DT", msg, Optional.of(trace));
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
        assertEquals(3, export.length);
        assertArrayEquals(new String[]{"R0099", "11", "I2025"}, export);
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
        assertArrayEquals(new String[]{"Record", "Segment", "Object", "Data", "Offset", "Severity", "Code", "Message", "Stack"}, export);
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
    void testDefault() throws BeanException {
        // Given
        final DefaultBean bean = new DefaultBean();
        final Up2Format<DefaultBean, GroupType> mapper = factory.format(DefaultBean.class);
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

}

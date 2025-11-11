package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BSOperator.BAccessor;
import io.github.up2jakarta.csv.core.misc.acs.*;
import io.github.up2jakarta.csv.core.misc.map.Default1Bean;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.core.Properties.assertUndefined;
import static io.github.up2jakarta.csv.core.Properties.assertValid;
import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2AccessTests {

    private final DataTypeResolver<?> resolver;
    private final Up2Factory<?> factory;

    @Autowired
    Up2AccessTests(Up2Factory<GroupType> factory) {
        this.resolver = factory.resolver;
        this.factory = factory;
    }

    @Test
    void valid1Bean() throws BeanException {
        // Given
        final Up2Mapper<Access1Bean, ?> mapper = factory.build(Access1Bean.class);
        final Up2Format<Access1Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Access2Bean, ?> mapper = factory.build(Access2Bean.class);
        final Up2Format<Access2Bean, ?> format = mapper.toFormat();
        final String[] data = new String[]{"AAB"};
        // When
        final Access2Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid31Bean() throws BeanException {
        // Given
        final Up2Mapper<Access31Bean, ?> mapper = factory.build(Access31Bean.class);
        final Up2Format<Access31Bean, ?> format = mapper.toFormat();
        final String[] data = new String[]{"AAB"};
        // When
        final Access31Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid32Bean() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Access32Bean.class));
        // Then
        assertEquals(Access32Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("Access32Bean[code] - getter not found", thrown.getMessage());
    }

    @Test
    void validAccessors() throws BeanException {
        // Given
        final Up2Mapper<Access4Bean, ?> mapper = factory.build(Access4Bean.class);
        final Up2Format<Access4Bean, ?> format = mapper.toFormat();
        final String[] data = new String[]{"AAB"};
        // When
        final Access4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validGenericBean() throws BeanException {
        // Given
        final Up2Mapper<Access5Bean, ?> mapper = factory.build(Access5Bean.class);
        final Up2Format<Access5Bean, ?> format = mapper.toFormat();
        final String[] data = new String[]{"AAB", "true"};
        // When
        final Access5Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validConstructor() throws BeanException {
        // Given
        final Up2Mapper<Access6Bean, ?> mapper = factory.build(Access6Bean.class);
        final Up2Format<Access6Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Record1Bean, ?> mapper = factory.build(Record1Bean.class);
        final Up2Format<Record1Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Record2Bean, ?> mapper = factory.build(Record2Bean.class);
        final Up2Format<Record2Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Record3Bean, ?> mapper = factory.build(Record3Bean.class);
        final Up2Format<Record3Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Record4Bean, ?> mapper = factory.build(Record4Bean.class);
        final Up2Format<Record4Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Record5Bean, ?> mapper = factory.build(Record5Bean.class);
        final Up2Format<Record5Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Record6Bean, ?> mapper = factory.build(Record6Bean.class);
        final Up2Format<Record6Bean, ?> format = mapper.toFormat();
        final String[] data = new String[]{null, "Test"};
        // When
        final Record6Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(new String[]{"0", "Test"}, out);
    }

    @Test
    void validOptional1Bean() throws BeanException {
        final Up2Mapper<Optional1Bean, ?> mapper = factory.build(Optional1Bean.class);
        final Up2Format<Optional1Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Optional2Bean, ?> mapper = factory.build(Optional2Bean.class);
        final Up2Format<Optional2Bean, ?> format = mapper.toFormat();
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
            final Optional2Bean bean = new Optional2Bean(0, null);
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertNull(bean.content());
        }
    }

    @Test
    void validOptional3Bean() throws BeanException {
        final Up2Mapper<Optional3Bean, ?> mapper = factory.build(Optional3Bean.class);
        final Up2Format<Optional3Bean, ?> format = mapper.toFormat();
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
            assertNull(bean.getKey());
            assertNull(bean.getKey());
        }
    }

    @Test
    void validOptional4Bean() throws BeanException {
        final Up2Mapper<Optional4Bean, ?> mapper = factory.build(Optional4Bean.class);
        final Up2Format<Optional4Bean, ?> format = mapper.toFormat();
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
        final Up2Mapper<Optional5Bean, ?> mapper = factory.build(Optional5Bean.class);
        final Up2Format<Optional5Bean, ?> format = mapper.toFormat();
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
            assertNull(bean.getContent());
        }
    }

    @Test
    void validOptional6Bean() throws BeanException {
        final Up2Mapper<Optional6Bean, ?> mapper = factory.build(Optional6Bean.class);
        final Up2Format<Optional6Bean, ?> format = mapper.toFormat();
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
            final Optional6Bean bean = new Optional6Bean(0, null);
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertNull(bean.content());
        }
    }

    @Test
    void validOptional7Bean() throws BeanException {
        // Given
        final Up2Mapper<Optional7Bean, ?> mapper = factory.build(Optional7Bean.class);
        final Up2Format<Optional7Bean, ?> format = mapper.toFormat();
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
    void validOptionalBusinessId() throws BeanException {
        // When
        final BAccessor<Segment, Object> bid1 = factory.build(resolver, BIdOBean.class).businessId;
        final BAccessor<Segment, Object> bid2 = factory.format(resolver, BIdOBean.class).businessId;
        // Then
        assertValid(bid1, new BIdOBean());
        assertValid(bid2, new BIdOBean());
    }

    @Test
    void validReadOnlyBusinessId() throws BeanException {
        // Given
        final BId5Bean bean = new BId5Bean(99);
        // When
        final BAccessor<Segment, Object> bid = factory.format(resolver, BId5Bean.class).businessId;
        // Then
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(AccessMode.RO));
        assertFalse(bid.supports(AccessMode.WO));
        assertEquals(99, bid.get(bean));
        assertEquals("99", bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

    @Test
    void validBusinessId1() throws BeanException {
        // Given
        final BId1Bean bean = new BId1Bean();
        // When
        final BAccessor<Segment, Object> bid1 = factory.build(resolver, BId1Bean.class).businessId;
        final BAccessor<Segment, Object> bid2 = factory.format(resolver, BId1Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId2() throws BeanException {
        // Given
        final BId2Bean bean = new BId2Bean();
        // When
        final BAccessor<Segment, Object> bid1 = factory.build(resolver, BId2Bean.class).businessId;
        final BAccessor<Segment, Object> bid2 = factory.format(resolver, BId2Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId3() throws BeanException {
        // Given
        final BId3Bean bean = new BId3Bean();
        // When
        final BAccessor<Segment, Object> bid1 = factory.build(resolver, BId3Bean.class).businessId;
        final BAccessor<Segment, Object> bid2 = factory.format(resolver, BId3Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId4() throws BeanException {
        // Given
        final BId4Bean bean = new BId4Bean(99);
        // When
        final BAccessor<Segment, Object> bid1 = factory.build(resolver, BId4Bean.class).businessId;
        final BAccessor<Segment, Object> bid2 = factory.format(resolver, BId4Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId5() throws BeanException {
        // Given
        final Default1Bean bean = new Default1Bean();
        // When
        final BAccessor<Segment, Object> bid1 = factory.build(resolver, Default1Bean.class).businessId;
        final BAccessor<Segment, Object> bid2 = factory.format(resolver, Default1Bean.class).businessId;
        // Then
        assertUndefined(bid1, bean);
        assertUndefined(bid2, bean);
    }

    @Test
    void validBusinessId6() throws BeanException {
        // Given
        final Record1Bean bean = new Record1Bean(null);
        // When
        final BAccessor<Segment, Object> bid1 = factory.build(resolver, Record1Bean.class).businessId;
        final BAccessor<Segment, Object> bid2 = factory.format(resolver, Record1Bean.class).businessId;
        // Then
        assertUndefined(bid1, bean);
        assertUndefined(bid2, bean);
    }

}

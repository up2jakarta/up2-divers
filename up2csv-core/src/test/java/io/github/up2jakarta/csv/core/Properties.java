package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.BSOperator.BId;
import io.github.up2jakarta.csv.core.BSProperty.Accessor;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.BSProperty.PPosition.PS;
import io.github.up2jakarta.csv.core.misc.acs.*;
import io.github.up2jakarta.csv.core.misc.map.Test6Segment;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.BSProperty.isFinal;
import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static jakarta.persistence.AccessType.PROPERTY;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class Properties {

    static void assertBean(Segment bean, List<BSProperty<?, ?, GroupType>> fields) throws Exception {
        for (final BSProperty<?, ?, GroupType> p : fields) {
            assertInstanceOf(PS.class, p);
            assertEquals(String.class, p.getType());
            final PS<String, GroupType> sp = (PS<String, GroupType>) p;
            final Field field = (Field) sp.getSource();
            field.setAccessible(true);
            assertEquals("V", field.get(bean));
            // When
            sp.value(bean, "Test");
            // Then
            assertEquals("Test", field.get(bean));
        }
    }

    static <S extends Test6Segment, D extends DataType<D>> void assertFinal(Up2Mapper<S, D> mapper) {
        // GIVEN
        final List<BSProperty<?, ?, D>> ps = mapper.node.properties;
        assertEquals(1, ps.size());
        final PS<String, D> code = assertInstanceOf(PS.class, ps.getFirst());
        assertEquals("code", code.getName());
        assertTrue(isFinal(code));
        // WHEN
        final Test6Segment bean = mapper.map("TU");
        final AccessException thrown = assertThrows(AccessException.class, () -> code.value(bean, "*"));
        assertEquals(getTypeName(bean.getClass()) + "[code] - unsupported write operation", thrown.getMessage());
        // THEN
        assertEquals("TU", bean.getCode());
    }

    static <V> Accessor<V> wo(Class<V> type, Field field) throws BeanException {
        return BeanAccess.WO.of(PROPERTY, (Class<? extends Segment>) field.getDeclaringClass(), field, type);
    }

    static Object parse(final PPosition<?, ?, ?> property, String value) {
        return property.parse(value, 0, of(WARNING));
    }

    static void assertValid(BId<Segment, Object> bid, BIdOptionalBean bean) {
        // Support
        assertInstanceOf(BId.DP.class, bid);
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(BeanAccess.RO));
        assertTrue(bid.supports(BeanAccess.WO));
        // Null Fragment
        assertNull(bid.get(bean));
        assertNull(bid.format(bean));
        // Empty Fragment
        bean.fragment = Optional.empty();
        assertNull(bid.get(bean));
        assertNull(bid.format(bean));
        // Null ID
        bean.fragment = Optional.of(new BIdOptionalBean.OFragment());
        assertNull(bid.get(bean));
        assertNull(bid.format(bean));
        // Empty ID
        bean.fragment.get().id = Optional.empty();
        assertNull(bid.get(bean));
        assertNull(bid.format(bean));
        // Read
        bean.fragment.get().id = Optional.of(99);
        assertEquals(99, bid.get(bean));
        // Write
        bid.set(bean, 100);
        assertEquals(100, bid.get(bean));
        assertEquals("100", bid.format(bean));
    }

    static void assertValid(BId<Segment, Object> bid, BId1Bean bean) {
        // When
        bean.fragment.id = 99;
        // Then
        assertInstanceOf(BId.DP.class, bid);
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(BeanAccess.RO));
        assertTrue(bid.supports(BeanAccess.WO));
        assertEquals(99, bid.get(bean));
        // Write
        bid.set(bean, 100);
        assertEquals(100, bid.get(bean));
        assertEquals("100", bid.format(bean));
    }

    static void assertValid(BId<Segment, Object> bid, BId2Bean bean) {
        // When
        bean.setReference(99);
        // Then
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(BeanAccess.RO));
        assertTrue(bid.supports(BeanAccess.WO));
        assertEquals(99, bid.get(bean));
        // Write
        bid.set(bean, 100);
        assertEquals(100, bid.get(bean));
        assertEquals("100", bid.format(bean));
    }

    static void assertValid(BId<Segment, Object> bid, BId3Bean bean) {
        // When
        bean.setReference(99);
        // Then
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(BeanAccess.RO));
        assertFalse(bid.supports(BeanAccess.WO));
        assertEquals(99, bid.get(bean));
        assertEquals("99", bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

    static void assertValid(BId<Segment, Object> bid, BId4Bean bean) {
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(BeanAccess.RO));
        assertFalse(bid.supports(BeanAccess.WO));
        assertEquals(99, bid.get(bean));
        assertEquals("99", bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

    static void assertUndefined(BId<Segment, Object> bid, Segment bean) {
        assertFalse(bid.supports(null));
        assertFalse(bid.supports(BeanAccess.RO));
        assertFalse(bid.supports(BeanAccess.WO));
        assertThrows(AccessException.class, () -> bid.get(bean));
        assertThrows(AccessException.class, () -> bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

}

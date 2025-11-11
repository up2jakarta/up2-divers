package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.BSOperator.BAccessor;
import io.github.up2jakarta.csv.core.BSOperator.BAccessor.BPAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PProperty;
import io.github.up2jakarta.csv.core.BSProperty.PProperty.POProperty;
import io.github.up2jakarta.csv.core.misc.acs.*;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static jakarta.persistence.AccessType.PROPERTY;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class Properties {

    static void assertBean(Segment bean, List<BSProperty<?, ?, GroupType>> fields) throws Exception {
        for (final BSProperty<?, ?, GroupType> p : fields) {
            assertInstanceOf(POProperty.class, p);
            assertEquals(String.class, p.getType());
            final POProperty<String, GroupType> sp = (POProperty<String, GroupType>) p;
            final Field field = (Field) sp.getSource();
            field.setAccessible(true);
            assertEquals("V", field.get(bean));
            // When
            sp.value(bean, "Test");
            // Then
            assertEquals("Test", field.get(bean));
        }
    }

    static <V> PAccessor<Field, V> wo(Class<V> type, Field field) throws BeanException {
        return AccessMode.WO.of(PROPERTY, (Class<? extends Segment>) field.getDeclaringClass(), field, type);
    }

    static Object parse(final PProperty<?, ?, ?> property, String value) {
        return property.parse(value, 0, of(WARNING));
    }

    static void assertValid(BAccessor<Segment, Object> bid, BIdOBean bean) {
        // Support
        assertInstanceOf(BPAccessor.class, bid);
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(AccessMode.RO));
        assertTrue(bid.supports(AccessMode.WO));
        // Null Fragment
        assertNull(bid.get(bean));
        assertNull(bid.format(bean));
        // Empty Fragment
        bean.fragment = Optional.empty();
        assertNull(bid.get(bean));
        assertNull(bid.format(bean));
        // Null ID
        bean.fragment = Optional.of(new BIdOBean.OFragment());
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

    static void assertValid(BAccessor<Segment, Object> bid, BId1Bean bean) {
        // When
        bean.fragment.id = 99;
        // Then
        assertInstanceOf(BPAccessor.class, bid);
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(AccessMode.RO));
        assertTrue(bid.supports(AccessMode.WO));
        assertEquals(99, bid.get(bean));
        // Write
        bid.set(bean, 100);
        assertEquals(100, bid.get(bean));
        assertEquals("100", bid.format(bean));
    }

    static void assertValid(BAccessor<Segment, Object> bid, BId2Bean bean) {
        // When
        bean.setReference(99);
        // Then
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(AccessMode.RO));
        assertTrue(bid.supports(AccessMode.WO));
        assertEquals(99, bid.get(bean));
        // Write
        bid.set(bean, 100);
        assertEquals(100, bid.get(bean));
        assertEquals("100", bid.format(bean));
    }

    static void assertValid(BAccessor<Segment, Object> bid, BId3Bean bean) {
        // When
        bean.setReference(99);
        // Then
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(AccessMode.RO));
        assertFalse(bid.supports(AccessMode.WO));
        assertEquals(99, bid.get(bean));
        assertEquals("99", bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

    static void assertValid(BAccessor<Segment, Object> bid, BId4Bean bean) {
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(AccessMode.RO));
        assertFalse(bid.supports(AccessMode.WO));
        assertEquals(99, bid.get(bean));
        assertEquals("99", bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

    static void assertUndefined(BAccessor<Segment, Object> bid, Segment bean) {
        assertFalse(bid.supports(null));
        assertFalse(bid.supports(AccessMode.RO));
        assertFalse(bid.supports(AccessMode.WO));
        assertThrows(AccessException.class, () -> bid.get(bean));
        assertThrows(AccessException.class, () -> bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

}

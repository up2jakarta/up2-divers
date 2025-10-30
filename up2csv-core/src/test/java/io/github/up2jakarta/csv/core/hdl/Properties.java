package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.hdl.PProperty.PSProperty;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;

import java.lang.reflect.Field;
import java.util.List;

import static jakarta.persistence.AccessType.PROPERTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SuppressWarnings("unchecked")
public class Properties {

    @SuppressWarnings("unchecked")
    public static void assertBean(Object bean, List<Property<?, GroupType>> fields) throws Exception {
        for (final Property<?, GroupType> p : fields) {
            assertInstanceOf(PSProperty.class, p);
            final PSProperty<GroupType> sp = (PSProperty<GroupType>) p;
            final Field field = (Field) sp.getSource();
            field.setAccessible(true);
            assertEquals("V", field.get(bean));
            // When
            sp.set(bean, "Test");
            // Then
            assertEquals("Test", field.get(bean));
        }
    }

    public static <V> PAccessor<Field, V> wo(Class<V> type, Field field) throws BeanException {
        return PAMode.WO.of(PROPERTY, (Class<? extends Segment>) field.getDeclaringClass(), field, type);
    }

}

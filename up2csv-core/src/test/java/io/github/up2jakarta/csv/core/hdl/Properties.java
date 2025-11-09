package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.hdl.PProperty.POProperty;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;

import java.lang.reflect.Field;
import java.util.List;

import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static jakarta.persistence.AccessType.PROPERTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SuppressWarnings("unchecked")
public class Properties {

    @SuppressWarnings("unchecked")
    public static void assertBean(Object bean, List<Property<?, ?, GroupType>> fields) throws Exception {
        for (final Property<?, ?, GroupType> p : fields) {
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

    public static <V> PAccessor<Field, V> wo(Class<V> type, Field field) throws BeanException {
        return PAMode.WO.of(PROPERTY, (Class<? extends Segment>) field.getDeclaringClass(), field, type);
    }

    public static Object parse(final PProperty<?, ?, ?> property, String value) throws BeanException {
        return property.parse(value, 0, of(WARNING));
    }

}

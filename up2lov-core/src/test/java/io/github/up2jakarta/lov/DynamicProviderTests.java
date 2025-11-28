package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.TypeContext;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

import static io.github.up2jakarta.lov.DynamicProvider.FILE;
import static io.github.up2jakarta.lov.DynamicProvider.values;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DynamicProviderTests {

    private final Member source;

    public DynamicProviderTests() throws NoSuchMethodException {
        this.source = Void.class.getDeclaredConstructor();
    }

    @Test
    void testProperties() {
        // Given
        final TypeContext context = new TypeContext(source, "LOV", ERROR, "TU", Map.of(FILE, "test.properties"));
        // When
        final List<DynamicCode> modes = values(context);
        // Then
        assertEquals(2, modes.size());
        //
        assertTrue(modes.stream().anyMatch(c -> "RO".equals(c.getCode()) && "Read only".equals(c.getName())));
        assertTrue(modes.stream().anyMatch(c -> "WO".equals(c.getCode()) && "Write only".equals(c.getName())));
    }

    @Test
    void testYaml() {
        // Given
        final TypeContext context = new TypeContext(source, "LOV", ERROR, "TU", Map.of(FILE, "test.yml"));
        // When
        final List<DynamicCode> modes = values(context);
        // Then
        assertEquals(2, modes.size());
        //
        assertTrue(modes.stream().anyMatch(c -> "RO".equals(c.getCode()) && "Read only".equals(c.getName())));
        assertTrue(modes.stream().anyMatch(c -> "WO".equals(c.getCode()) && "Write only".equals(c.getName())));
    }

}

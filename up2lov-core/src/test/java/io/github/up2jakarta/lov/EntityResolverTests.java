package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.cl.SystemEntity;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.Map;

import static io.github.up2jakarta.lov.EntityResolver.JPA_CODE;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.Beans.cast;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest(showSql = false)
public class EntityResolverTests {

    private final TypeAdapter<EntityList<?, ?>> adapter;

    @Autowired
    public EntityResolverTests(EntityResolver resolver) throws NoSuchMethodException, BeanException {
        final Member source = Void.class.getDeclaredConstructor();
        final Map<String, String> args = Map.of(JPA_CODE, "code");
        final TypeContext context = new TypeContext(source, "SystemType", ERROR, "SYS-001", args);
        this.adapter = resolver.resolve(cast(SystemEntity.class), context);
    }

    @Test
    public void testFound() {
        // Given
        final String code = "AB";
        // When
        final EntityList<?, ?> entity = adapter.parse(code);
        // Then
        assertNotNull(entity);
        assertEquals(code, entity.getCode());
    }

    @Test
    public void testNotFound() {
        // Given
        final String code = "??";
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> adapter.parse(code));
        // Then
        assertNotNull(error);
        assertEquals("SYS-001", error.getCode());
        assertEquals(ERROR, error.getLevel());
        assertEquals("Unknown input [??] for CodeList[SystemType]", error.getMessage());
    }

    @Test
    public void testDuplicated() {
        // Given
        final String code = "BL";
        // When
        final TypeException error = assertThrows(TypeException.class, () -> adapter.parse(code));
        // Then
        assertNotNull(error);
        assertEquals("SYS-001", error.getCode());
        assertEquals(ERROR, error.getLevel());
        assertEquals("No unique CodeList[SystemType] for input [BL], 2 results were found", error.getMessage());
    }

}

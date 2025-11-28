package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeContext;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.Map;

import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.TableResolver.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional(readOnly = true)
@DataJpaTest(showSql = false)
public class TableResolverTests {

    private final TypeAdapter<DynamicCode> adapter;

    @Autowired
    public TableResolverTests(TableResolver resolver) throws NoSuchMethodException, BeanException {
        final Member source = Void.class.getDeclaredConstructor();
        final Map<String, String> args = Map.of(SQL_TABLE, "TB_SYSTEMS", SQL_CODE, "SYS_CODE", SQL_NAME, "SYS_LABEL");
        final TypeContext context = new TypeContext(source, "SystemType", ERROR, "SYS-001", args);
        this.adapter = resolver.resolve(DynamicCode.class, context);
    }

    @RepeatedTest(10)
    public void testFound() {
        // Given
        final String code = "AB";
        // When
        final DynamicCode entity = adapter.parse(code);
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

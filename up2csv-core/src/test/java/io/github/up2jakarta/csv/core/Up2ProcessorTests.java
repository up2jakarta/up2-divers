package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Trim;
import io.github.up2jakarta.csv.core.hdl.*;
import io.github.up2jakarta.csv.core.hdl.PProperty.POProperty;
import io.github.up2jakarta.csv.core.hdl.PProperty.PSProperty;
import io.github.up2jakarta.csv.core.misc.DummyException;
import io.github.up2jakarta.csv.core.misc.ext.Dummy4;
import io.github.up2jakarta.csv.core.misc.prc.Test2Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test5Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test6Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test7Processor;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.prc.TrimProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_PROCESSOR;
import static io.github.up2jakarta.csv.core.Up2ErrorTests.DUMMY;
import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class Up2ProcessorTests {

    private final BeanContext context;
    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2ProcessorTests(BeanContext context, Up2Factory<GroupType> factory) {
        this.context = context;
        this.factory = factory;
    }

    private PProperty<?, ?> property(String property) throws Exception {
        final Field field = Test7Processor.class.getDeclaredField(property);
        final Position position = field.getAnnotation(Position.class);
        final PProcessor<?> processor = BSBuilder.build(context, field, position);
        if (field.getType() == String.class) {
            final PAccessor<?, String> va = Properties.wo(String.class, field);
            return new PSProperty<>(va, null, 0, position, processor);
        } else if (field.getType() == Integer.class) {
            final PAccessor<?, Integer> va = Properties.wo(Integer.class, field);
            final Conversion<Integer> cvr = new Conversion<>(Integer::parseInt, Object::toString);
            return new POProperty<>(va, null, 0, position, processor, cvr);
        }
        throw new UnsupportedOperationException();
    }

    @Test
    void testTrim() {
        // GIVEN
        final String[] data = {null, "", " \t\n", "- \t\n", "\n\t - \t\n", "\n\t DATA \t\n", "DA - TA"};
        // WHEN
        TrimProcessor.trim(data, "", "-");
        // THEN
        for (var i = 0; i < 5; i++) {
            assertNull(data[i]);
        }
        assertEquals("DATA", data[5]);
        assertEquals("DA - TA", data[6]);
    }

    @Test
    @SuppressWarnings("ALL")
    void testTrimNull() {
        // GIVEN
        final String[] data = null;
        // WHEN
        TrimProcessor.trim(data);
        // THEN
        assertNull(data);
    }

    @Test
    void testProcessor1() throws Exception {
        //Given
        class TestProcessor {
            @Position(0)
            @Up2Trim({"", "-", "+"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final PProcessor<?> processor = BSBuilder.build(context, field, field.getAnnotation(Position.class));
        // Then
        assertNull(processor.process(null, 0, null, null));
        assertNull(processor.process("", 0, null, null));
        assertNull(processor.process("-", 0, null, null));
        assertNull(processor.process("+", 0, null, null));
    }

    @Test
    void testProcessor2() throws Exception {
        //Given
        class TestProcessor {
            @Position(0)
            @Up2Trim({"", "-"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final PProcessor<?> processor = BSBuilder.build(context, field, field.getAnnotation(Position.class));
        // Then
        assertNull(processor.process(null, 0, null, null));
        assertNull(processor.process("", 0, null, null));
        assertNull(processor.process("-", 0, null, null));
        assertEquals("+", processor.process("+", 0, null, null));
    }

    @Test
    void testSDefaultValue() throws Exception {
        //Given
        final PProperty<?, ?> property = property("value");
        {
            // When null
            final Object value = property.get(null, 0, of(ERROR));
            // Then
            assertEquals("default", value);
        }
        {
            // When
            final Object value = property.get("value", 0, of(ERROR));
            // Then
            assertEquals("value", value);
        }
        {
            // When undefined (don't set)
            final Object value = property.get("\t\nundefined\t\n", 0, of(ERROR));
            // Then
            assertNull(value);
        }
    }

    @Test
    void testODefaultValue() throws Exception {
        //Given
        final PProperty<?, ?> property = property("number");
        {
            // When null
            final Object value = property.get(null, 0, of(ERROR));
            // Then
            assertEquals(99, value);
        }
        {
            // When
            final Object value = property.get("11", 0, of(ERROR));
            // Then
            assertEquals(11, value);
        }
        {
            // When undefined (don't set)
            final Object value = property.get("undefined", 0, of(ERROR));
            // Then
            assertNull(value);
        }
    }

    @Test
    void testNotSkipException() throws BeanException {
        // Given
        final Up2Mapper<Test6Processor, ?> mapper = factory.build(Test6Processor.class);
        {
            // Then
            final FastException thrown = assertThrows(FastException.class, () -> mapper.map("dummy"));
            // THEN
            assertNotNull(thrown.getCause());
            assertInstanceOf(DummyException.class, thrown.getCause());
            assertEquals(ERROR, thrown.getSeverity());
            assertEquals(ERROR_PROCESSOR, thrown.getCode());
            assertEquals(1, thrown.getOffset());
            assertEquals(DUMMY + ": dummy message", thrown.getMessage());
        }
        {
            // When
            final FastException thrown = assertThrows(FastException.class, () -> mapper.map(""));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getCode());
            assertEquals(ERROR, thrown.getSeverity());
            assertInstanceOf(NullPointerException.class, thrown.getCause());
            assertEquals("#[1] throws ERROR[UP2-P001] : java.lang.NullPointerException: null message", thrown.getFormattedMessage());
        }
        {
            // When
            final FastException thrown = assertThrows(FastException.class, () -> mapper.map("other"));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getCode());
            assertEquals(ERROR, thrown.getSeverity());
            assertInstanceOf(RuntimeException.class, thrown.getCause());
            assertEquals("#[1] throws ERROR[UP2-P001] : java.lang.RuntimeException: other message", thrown.getFormattedMessage());
        }
    }

    @Test
    void testSkipWarnings() throws BeanException {
        // Given
        final Up2Mapper<Test2Processor, ?> mapper = factory.build(Test2Processor.class);
        {
            // When
            final Test2Processor result = mapper.map("dummy");
            // THEN
            assertNotNull(result);
        }
        {
            // When
            final Test2Processor result = mapper.map("");
            // THEN
            assertNotNull(result);
        }
        {
            // When
            final Test2Processor result = mapper.map("other");
            // THEN
            assertNotNull(result);
        }
    }

    @Test
    void testLocalClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test5Processor.class));
        // THEN
        assertEquals(Dummy4.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("Dummy4[class] - @Processor[value] must implements InputProcessor<Dummy4>", thrown.getMessage());
    }

}

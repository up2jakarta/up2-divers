package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Trim;
import io.github.up2jakarta.csv.core.BSBuilder.Input;
import io.github.up2jakarta.csv.core.BSProperty.Accessor;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.BSProperty.PPosition.PS;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.misc.DummyException;
import io.github.up2jakarta.csv.core.misc.ext.Dummy4;
import io.github.up2jakarta.csv.core.misc.prc.Test2Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test5Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test6Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test7Processor;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.prc.TrimProcessor;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanContext;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.StringAdapter;
import io.github.up2jakarta.lov.core.TypeWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.api.IEvent.EC_PROCESSOR;
import static io.github.up2jakarta.csv.core.Properties.parse;
import static io.github.up2jakarta.csv.core.Up2ErrorTests.EX_CAUSE;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
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

    private PPosition<?, ?, ?> property(String property) throws Exception {
        final Field field = Test7Processor.class.getDeclaredField(property);
        final Position position = field.getAnnotation(Position.class);
        final Input<?> processor = BSBuilder.build(context, field, position);
        if (field.getType() == String.class) {
            final Accessor<String> va = Properties.wo(String.class, field);
            return new PS<>(va, null, 0, position, processor, StringAdapter.INSTANCE);
        } else if (field.getType() == Integer.class) {
            final Accessor<Integer> va = Properties.wo(Integer.class, field);
            final TypeAdapter<Integer> cvr = new TypeWrapper<>(Integer.class, Integer::parseInt);
            return new PS<>(va, null, 0, position, processor, cvr);
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
        final Input<?> processor = BSBuilder.build(context, field, field.getAnnotation(Position.class));
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
        final Input<?> processor = BSBuilder.build(context, field, field.getAnnotation(Position.class));
        // Then
        assertNull(processor.process(null, 0, null, null));
        assertNull(processor.process("", 0, null, null));
        assertNull(processor.process("-", 0, null, null));
        assertEquals("+", processor.process("+", 0, null, null));
    }

    @Test
    void testSDefaultValue() throws Exception {
        //Given
        final PPosition<?, ?, ?> property = property("value");
        {
            // When null
            final Object value = parse(property, null);
            // Then
            assertEquals("default", value);
        }
        {
            // When
            final Object value = parse(property, "value");
            // Then
            assertEquals("value", value);
        }
        {
            // When undefined (don't set)
            final Object value = parse(property, "\t\nundefined\t\n");
            // Then
            assertNull(value);
        }
    }

    @Test
    void testODefaultValue() throws Exception {
        //Given
        final PPosition<?, ?, ?> property = property("number");
        {
            // When null
            final Object value = parse(property, null);
            // Then
            assertEquals(99, value);
        }
        {
            // When
            final Object value = parse(property, "11");
            // Then
            assertEquals(11, value);
        }
        {
            // When undefined (don't set)
            final Object value = parse(property, "undefined");
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
            final FailureException thrown = assertThrows(FailureException.class, () -> mapper.map("dummy"));
            // THEN
            assertNotNull(thrown.getCause());
            assertInstanceOf(DummyException.class, thrown.getCause());
            assertEquals(ERROR, thrown.getLevel());
            assertEquals(EC_PROCESSOR, thrown.getCode());
            assertEquals(1, thrown.getOffset());
            assertEquals(EX_CAUSE + ": dummy message", thrown.getMessage());
        }
        {
            // When
            final FailureException thrown = assertThrows(FailureException.class, () -> mapper.map(""));
            // THEN
            assertEquals(EC_PROCESSOR, thrown.getCode());
            assertEquals(ERROR, thrown.getLevel());
            assertInstanceOf(NullPointerException.class, thrown.getCause());
            assertEquals("#[1] throws ERROR[UP2-P001] : java.lang.NullPointerException: null message", thrown.getFormattedMessage());
        }
        {
            // When
            final FailureException thrown = assertThrows(FailureException.class, () -> mapper.map("other"));
            // THEN
            assertEquals(EC_PROCESSOR, thrown.getCode());
            assertEquals(ERROR, thrown.getLevel());
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

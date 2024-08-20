package io.github.up2jakarta.csv.core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.annotation.Up2Default;
import io.github.up2jakarta.csv.annotation.Up2Token;
import io.github.up2jakarta.csv.annotation.Up2Trim;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.exception.MapperException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.processor.Test1Processor;
import io.github.up2jakarta.csv.test.bean.processor.Test2Processor;
import io.github.up2jakarta.csv.test.bean.processor.Test5Processor;
import io.github.up2jakarta.csv.test.extension.Dummy4;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.List;

import static io.github.up2jakarta.csv.core.BeanSupport.getProcessors;
import static io.github.up2jakarta.csv.core.Mapper.LOGGER;
import static io.github.up2jakarta.csv.extension.SeverityType.ERROR;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_PROCESSOR;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ProcessorSupportTest {

    private final BeanContext context;
    private final MapperFactory factory;

    @Autowired
    ProcessorSupportTest(BeanContext context, MapperFactory factory) {
        this.context = context;
        this.factory = factory;
    }

    @Test
    void testProcessor1() throws Exception {
        //Given
        class TestProcessor {
            @Up2Trim({"", "-", "+"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final ProcessorWrapper<?>[] processors = getProcessors(context, field);
        assertEquals(1, processors.length);
        final ProcessorWrapper<?> processor = processors[0];
        // Then
        assertNull(processor.process(null));
        assertNull(processor.process(""));
        assertNull(processor.process("-"));
        assertNull(processor.process("+"));
    }

    @Test
    void testProcessor2() throws Exception {
        //Given
        class TestProcessor {
            @Up2Trim({"", "-"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final ProcessorWrapper<?>[] processors = getProcessors(context, field);
        assertEquals(1, processors.length);
        final ProcessorWrapper<?> processor = processors[0];
        // Then
        assertNull(processor.process(null));
        assertNull(processor.process(""));
        assertNull(processor.process("-"));
        assertEquals("+", processor.process("+"));
    }

    @Test
    void testOneShot() throws Exception {
        //Given
        class TestProcessor {
            @Up2Token
            @Up2Trim("undefined")
            @Up2Default("default")
            String p;
        }
        final Field field = TestProcessor.class.getDeclaredField("p");
        // When
        final ProcessorWrapper<?>[] processors = getProcessors(context, field);
        assertEquals(3, processors.length);
        // Then
        var value = "\t\nundefined\t\n";
        for (var processor : processors) {
            value = processor.process(value);
        }
        assertEquals("default", value);
    }

    @Test
    void testSkipDummyException() throws BeanException {
        // Given
        final Mapper<Test1Processor> mapper = factory.build(Test1Processor.class);
        {
            // When
            final Test1Processor bean = mapper.map("dummy");
            // Then
            assertEquals("dummy", bean.getTest());
        }
        {
            // When
            final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map(""));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getErrorCode());
            assertEquals(ERROR, thrown.getSeverityType());
            assertNotNull(thrown.getCause().getCause());
            assertInstanceOf(NullPointerException.class, thrown.getCause().getCause());
            assertEquals("#[1] throws ERROR[UP2-P003] : java.lang.NullPointerException: NPE", thrown.getFormattedMessage());
        }
        {
            // When
            final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map("other"));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getErrorCode());
            assertEquals(ERROR, thrown.getSeverityType());
            assertNotNull(thrown.getCause().getCause());
            assertInstanceOf(RuntimeException.class, thrown.getCause().getCause());
            assertEquals("#[1] throws ERROR[UP2-P003] : java.lang.RuntimeException: other", thrown.getFormattedMessage());
        }
    }

    @Test
    void testSkipAndLog() throws Throwable {
        // Given
        final Mapper<Test1Processor> mapper = factory.build(Test1Processor.class);
        // When
        final List<ILoggingEvent> logs = Tests.hack(LOGGER, () -> mapper.map("dummy"));
        // Then
        assertEquals(1, logs.size());
        final ILoggingEvent event = logs.get(0);
        assertEquals(Level.WARN, event.getLevel());
        assertEquals("Skip @Processor[{}] error : {}", event.getMessage());
        assertEquals(2, event.getArgumentArray().length);
        assertEquals("Dummy1Processor", event.getArgumentArray()[0]);
        assertEquals("dummy", event.getArgumentArray()[1]);
    }

    @Test
    void testSkipAnyRuntimeException() throws BeanException {
        // Given
        final Mapper<Test2Processor> mapper = factory.build(Test2Processor.class);
        {
            // When
            final Test2Processor bean = mapper.map("dummy");
            // Then
            assertEquals("dummy", bean.getTest());
        }
        {
            // When
            final Test2Processor bean = mapper.map("");
            // Then
            assertEquals("", bean.getTest());

        }
        {
            // When
            final Test2Processor bean = mapper.map("other");
            // Then
            assertEquals("other", bean.getTest());
        }
    }

    @Test
    void testLocalClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test5Processor.class));
        // THEN
        assertEquals(Dummy4.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("Dummy4[class] - @Processor[value] must implements ConfigurableProcessor<Dummy4>", thrown.getMessage());
    }

}

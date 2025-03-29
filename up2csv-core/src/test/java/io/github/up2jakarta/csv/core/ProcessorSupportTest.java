package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.annotation.Up2Default;
import io.github.up2jakarta.csv.annotation.Up2Token;
import io.github.up2jakarta.csv.annotation.Up2Trim;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.impl.DataId;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.csv.misc.MapperException;
import io.github.up2jakarta.csv.test.bean.processor.Test2Processor;
import io.github.up2jakarta.csv.test.bean.processor.Test5Processor;
import io.github.up2jakarta.csv.test.bean.processor.Test6Processor;
import io.github.up2jakarta.csv.test.ext.Dummy4;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.PropertyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.List;

import static io.github.up2jakarta.csv.core.BeanSupport.getProcessors;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_PROCESSOR;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ProcessorSupportTest {

    private final BeanContext context;
    private final MapperFactory<DataId> factory;

    @Autowired
    ProcessorSupportTest(BeanContext context, MapperFactory<DataId> factory) {
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
        final List<ProcessorWrapper<?, DataId>> processors = getProcessors(context, field);
        assertEquals(1, processors.size());
        final ProcessorWrapper<?, ?> processor = processors.get(0);
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
        final List<ProcessorWrapper<?, DataId>> processors = getProcessors(context, field);
        assertEquals(1, processors.size());
        final ProcessorWrapper<?, DataId> processor = processors.get(0);
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
        final List<ProcessorWrapper<?, DataId>> processors = getProcessors(context, field);
        assertEquals(3, processors.size());
        // Then
        var value = "\t\nundefined\t\n";
        for (var processor : processors) {
            value = processor.process(value);
        }
        assertEquals("default", value);
    }

    @Test
    void testNotSkipException() throws BeanException {
        // Given
        final Mapper<Test6Processor, ?> mapper = factory.build(Test6Processor.class);
        {
            // Then
            final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map("dummy"));
            // THEN
            assertNotNull(thrown.getCause());
            assertInstanceOf(PropertyException.class, thrown.getCause());
            assertEquals(SeverityType.ERROR, thrown.getSeverityType());
            assertEquals(Errors.ERROR_PROCESSOR, thrown.getErrorCode());
            assertEquals(1, thrown.getOffset());
            assertEquals("io.github.up2jakarta.csv.test.ext.DummyException: dummy", thrown.getCause().getMessage());
        }
        {
            // When
            final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map(""));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getErrorCode());
            assertEquals(SeverityType.ERROR, thrown.getSeverityType());
            assertNotNull(thrown.getCause().getCause());
            assertInstanceOf(NullPointerException.class, thrown.getCause().getCause());
            assertEquals("#[1] throws ERROR[UP2-P003] : java.lang.NullPointerException: NPE", thrown.getFormattedMessage());
        }
        {
            // When
            final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map("other"));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getErrorCode());
            assertEquals(SeverityType.ERROR, thrown.getSeverityType());
            assertNotNull(thrown.getCause().getCause());
            assertInstanceOf(RuntimeException.class, thrown.getCause().getCause());
            assertEquals("#[1] throws ERROR[UP2-P003] : java.lang.RuntimeException: other", thrown.getFormattedMessage());
        }
    }

    @Test
    void testSkipWarnings() throws BeanException {
        // Given
        final Mapper<Test2Processor, ?> mapper = factory.build(Test2Processor.class);
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
        assertEquals("Dummy4[class] - @Processor[value] must implements ConfigurableProcessor<Dummy4>", thrown.getMessage());
    }

}

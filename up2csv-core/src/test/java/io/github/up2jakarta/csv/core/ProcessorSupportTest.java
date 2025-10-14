package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.cfg.Up2Default;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.cfg.Up2Trim;
import io.github.up2jakarta.csv.core.misc.ext.Dummy4;
import io.github.up2jakarta.csv.core.misc.prc.Test2Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test5Processor;
import io.github.up2jakarta.csv.core.misc.prc.Test6Processor;
import io.github.up2jakarta.csv.impl.FastException;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.prc.TrimProcessor;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.List;

import static io.github.up2jakarta.csv.core.BeanSupport.getProcessors;
import static io.github.up2jakarta.csv.core.Errors.ERROR_PROCESSOR;
import static io.github.up2jakarta.csv.core.MapperExceptionTest.DUMMY;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ProcessorSupportTest {

    private final BeanContext context;
    private final MapperFactory<GroupType> factory;

    @Autowired
    ProcessorSupportTest(BeanContext context, MapperFactory<GroupType> factory) {
        this.context = context;
        this.factory = factory;
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
            @Up2Trim({"", "-", "+"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final List<ProcessorWrapper<?, GroupType>> processors = getProcessors(context, field);
        assertEquals(1, processors.size());
        final ProcessorWrapper<?, ?> processor = processors.getFirst();
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
        final List<ProcessorWrapper<?, GroupType>> processors = getProcessors(context, field);
        assertEquals(1, processors.size());
        final ProcessorWrapper<?, GroupType> processor = processors.getFirst();
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
        final List<ProcessorWrapper<?, GroupType>> processors = getProcessors(context, field);
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
            final FastException thrown = assertThrows(FastException.class, () -> mapper.map("dummy"));
            // THEN
            assertNotNull(thrown.getCause());
            assertInstanceOf(PropertyException.class, thrown.getCause());
            assertEquals(SeverityType.ERROR, thrown.getSeverity());
            assertEquals(Errors.ERROR_PROCESSOR, thrown.getCode());
            assertEquals(1, thrown.getOffset());
            assertEquals(DUMMY + ": dummy", thrown.getCause().getMessage());
        }
        {
            // When
            final FastException thrown = assertThrows(FastException.class, () -> mapper.map(""));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getCode());
            assertEquals(SeverityType.ERROR, thrown.getSeverity());
            assertInstanceOf(PropertyException.class, thrown.getCause());
            assertInstanceOf(NullPointerException.class, thrown.getCause().getCause());
            assertEquals("#[1] throws ERROR[UP2-P003] : java.lang.NullPointerException: NPE", thrown.getFormattedMessage());
        }
        {
            // When
            final FastException thrown = assertThrows(FastException.class, () -> mapper.map("other"));
            // THEN
            assertEquals(ERROR_PROCESSOR, thrown.getCode());
            assertEquals(SeverityType.ERROR, thrown.getSeverity());
            assertInstanceOf(PropertyException.class, thrown.getCause());
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
        assertEquals("Dummy4[class] - @Processor[value] must implements InputProcessor<Dummy4>", thrown.getMessage());
    }

}

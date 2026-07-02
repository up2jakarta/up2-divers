package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.prc.TrimProcessor;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.DummyException;
import io.github.up2jakarta.test.core.misc.ext.Dummy4;
import io.github.up2jakarta.test.core.misc.prc.Test2Processor;
import io.github.up2jakarta.test.core.misc.prc.Test5Processor;
import io.github.up2jakarta.test.core.misc.prc.Test6Processor;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.api.IEvent.EC_PROCESSOR;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class Up2ProcessorTests {

    static final String EX_CAUSE = DummyException.class.getName();

    private final Up2Factory<TermType> factory;

    @Autowired
    Up2ProcessorTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    @Test
    void testTrim() {
        // GIVEN
        final String[] data = {null, "", " \t\n\r", "- \t\n", "\n\t - \t\n", "\n\t DATA \t\n", "DA - TA"};
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
    void testNotSkipException() throws BeanException {
        // Given
        final Up2Mapper<Test6Processor, ?> mapper = factory.mapper(Test6Processor.class);
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
            assertEquals("#[1] throws #[UP2-P001] java.lang.NullPointerException: null message", thrown.getLocalizedMessage());
        }
        {
            // When
            final FailureException thrown = assertThrows(FailureException.class, () -> mapper.map("other"));
            // THEN
            assertEquals(EC_PROCESSOR, thrown.getCode());
            assertEquals(ERROR, thrown.getLevel());
            assertInstanceOf(RuntimeException.class, thrown.getCause());
            assertEquals("#[1] throws #[UP2-P001] java.lang.RuntimeException: other message", thrown.getLocalizedMessage());
        }
    }

    @Test
    void testSkipWarnings() throws BeanException {
        // Given
        final Up2Mapper<Test2Processor, ?> mapper = factory.mapper(Test2Processor.class);
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
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.mapper(Test5Processor.class));
        // THEN
        assertEquals(Dummy4.class, thrown.getSource());
        assertEquals(CLASS, thrown.getLocator());
        assertEquals("@Processor[value] must implements InputProcessor<Dummy4>", thrown.getMessage());
    }

}

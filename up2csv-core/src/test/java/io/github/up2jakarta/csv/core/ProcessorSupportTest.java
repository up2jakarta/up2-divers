package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.annotation.Default;
import io.github.up2jakarta.csv.annotation.Processor;
import io.github.up2jakarta.csv.annotation.Token;
import io.github.up2jakarta.csv.annotation.Trim;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;
import io.github.up2jakarta.csv.processor.NoArgumentTransformer;
import io.github.up2jakarta.csv.processor.TokenTransformer;
import io.github.up2jakarta.csv.processor.TrimTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ProcessorSupportTest {

    private final BeanContext context;

    @Autowired
    ProcessorSupportTest(BeanContext context) {
        this.context = context;
    }

    @Test
    void testProcessorArgumentsOverride() throws Exception {
        //Given
        class TestProcessor {
            @Trim({"", "-", "+"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final ConfigurableTransformer[] transformers = BeanSupport.getTransformers(context, field);
        assertEquals(1, transformers.length);
        assertInstanceOf(NoArgumentTransformer.class, transformers[0]);
        final NoArgumentTransformer transformer = (NoArgumentTransformer) transformers[0];
        // Then
        assertNull(transformer.transform(null));
        assertNull(transformer.transform(""));
        assertNull(transformer.transform("-"));
        assertNull(transformer.transform("+"));
    }

    @Test
    void testProcessorDefaultArguments() throws Exception {
        //Given
        class TestProcessor {
            @Trim({"", "-"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final ConfigurableTransformer[] transformers = BeanSupport.getTransformers(context, field);
        assertEquals(1, transformers.length);
        assertInstanceOf(NoArgumentTransformer.class, transformers[0]);
        final NoArgumentTransformer transformer = (NoArgumentTransformer) transformers[0];
        // Then
        assertNull(transformer.transform(null));
        assertNull(transformer.transform(""));
        assertNull(transformer.transform("-"));
        assertEquals("+", transformer.transform("+"));
    }

    @Test
    void testDoubleProcessorWithinSimple() throws Exception {
        //Given
        class TestProcessor {
            @TrimAndToken({"", "-"})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final ConfigurableTransformer[] transformers = BeanSupport.getTransformers(context, field);
        assertEquals(2, transformers.length);
    }

    @Test
    void testDoubleProcessorWithoutSimple() throws Exception {
        //Given
        class TestProcessor {
            @TrimAndTrim({})
            String attribute;
        }
        final Field field = TestProcessor.class.getDeclaredField("attribute");
        // When
        final ConfigurableTransformer[] transformers = BeanSupport.getTransformers(context, field);
        assertEquals(2, transformers.length);
    }

    @Test
    void testDoubleProcessorConflict() throws Exception {
        //Given
        class TestProcessor {
            @TrimAndTrim("undefined")
            String p;
        }
        final Field field = TestProcessor.class.getDeclaredField("p");
        // When
        final ConfigurableTransformer[] transformers = BeanSupport.getTransformers(context, field);
        assertEquals(2, transformers.length);
        // Then
        {
            assertInstanceOf(NoArgumentTransformer.class, transformers[0]);
            final NoArgumentTransformer transformer = (NoArgumentTransformer) transformers[0];
            assertNull(transformer.transform("undefined"));
            assertEquals("", transformer.transform(""));
            assertEquals("-", transformer.transform("-"));
        }
        {
            assertInstanceOf(NoArgumentTransformer.class, transformers[0]);
            final NoArgumentTransformer transformer = (NoArgumentTransformer) transformers[0];
            assertNull(transformer.transform("undefined"));
            assertEquals("", transformer.transform(""));
            assertEquals("-", transformer.transform("-"));
        }
    }

    @Test
    void testAllInOne() throws Exception {
        //Given
        class TestProcessor {
            @Token
            @Trim("undefined")
            @Default("default")
            String p;
        }
        final Field field = TestProcessor.class.getDeclaredField("p");
        // When
        final ConfigurableTransformer[] transformers = BeanSupport.getTransformers(context, field);
        assertEquals(3, transformers.length);
        // Then
        var value = "\t\nundefined\t\n";
        for (var transformer : transformers) {
            value = transformer.transform(value);
        }
        assertEquals("default", value);
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Processor(value = TrimTransformer.class)
    @Processor(value = TokenTransformer.class)
    public @interface TrimAndToken {
        String[] value();
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Processor(value = TrimTransformer.class, arguments = "")
    @Processor(value = TrimTransformer.class, arguments = "-")
    public @interface TrimAndTrim {
        @Processor.Override(TrimTransformer.class)
        String[] value();
    }

}

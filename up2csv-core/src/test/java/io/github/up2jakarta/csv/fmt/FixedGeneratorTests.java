package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.fmt.hdl.Fixed06Generator;
import io.github.up2jakarta.csv.fmt.hdl.Fixed13Generator;
import org.junit.jupiter.api.Test;

import static io.github.up2jakarta.csv.fmt.hdl.Fixed06Generator.FV_SM;
import static io.github.up2jakarta.csv.fmt.hdl.Fixed13Generator.FV_LG;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedGeneratorTests {

    @Test
    void test06Negative() {
        // Given
        final Fixed06Generator generator = new Fixed06Generator(Integer.MIN_VALUE);
        // Then
        assertEquals(FV_SM, generator.getAsInt());
        assertEquals("up2v06", generator.get());
        assertEquals(FV_SM + 1, generator.getAsInt());
    }

    @Test
    void testFixed06Max() {
        // Given
        final Fixed06Generator generator = new Fixed06Generator(Integer.MAX_VALUE);
        // Then
        assertEquals(Integer.MAX_VALUE, generator.getAsInt());
        assertEquals("zik0zj", generator.get());
        assertEquals(1, generator.getAsInt());
        assertEquals("000001", generator.get());
        assertEquals(2, generator.getAsInt());
    }

    @Test
    void testFixed13Negative() {
        // Given
        final Fixed13Generator generator = new Fixed13Generator(Long.MIN_VALUE);
        // Then
        assertEquals(FV_LG, generator.getAsLong());
        assertEquals("1up2jakarta13", generator.get());
        assertEquals(FV_LG + 1, generator.getAsLong());
    }

    @Test
    void testFixed13Max() {
        // Given
        final Fixed13Generator generator = new Fixed13Generator(Long.MAX_VALUE);
        // Then
        assertEquals(Long.MAX_VALUE, generator.getAsLong());
        assertEquals("1y2p0ij32e8e7", generator.get());
        assertEquals(1, generator.getAsLong());
        assertEquals("0000000000001", generator.get());
        assertEquals(2, generator.getAsLong());
    }

}

package io.github.up2jakarta.test;

import io.github.up2jakarta.lov.ConstantProvider;
import io.github.up2jakarta.test.lov.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstantProviderTests {

    @Test
    void classConstantProvider() {
        // Given
        final Class<AccessMode1> clType = AccessMode1.class;
        // When
        final List<AccessMode1> modes = ConstantProvider.values(clType);
        // Then
        assertEquals(2, modes.size());
        assertTrue(modes.contains(AccessMode1.RO));
        assertTrue(modes.contains(AccessMode1.WO));
    }

    @Test
    void enumConstantProvider() {
        // Given
        final Class<AccessMode2> clType = AccessMode2.class;
        // When
        final List<AccessMode2> modes = ConstantProvider.values(clType);
        // Then
        assertEquals(3, modes.size());
        assertTrue(modes.contains(AccessMode2.RW));
        assertTrue(modes.contains(AccessMode2.RO));
        assertTrue(modes.contains(AccessMode2.WO));
    }

    @Test
    void classConstantProvider30() {
        // Given
        final Class<AccessMode30> clType = AccessMode30.class;
        // When
        final List<AccessMode30> modes = ConstantProvider.values(clType);
        // Then
        assertEquals(2, modes.size());
        assertTrue(modes.contains(AccessMode31.RO));
        assertTrue(modes.contains(AccessMode32.WO));
    }

    @Test
    void classConstantProvider31() {
        // Given
        final AccessMode30 any = AccessMode31.RO;
        // When
        final List<AccessMode30> modes = ConstantProvider.values(any);
        // Then
        assertEquals(2, modes.size());
        assertTrue(modes.contains(AccessMode31.RO));
        assertTrue(modes.contains(AccessMode32.WO));
    }

    @Test
    void classConstantProvider32() {
        // Given
        final AccessMode30 any = AccessMode32.WO;
        // When
        final List<AccessMode30> modes = ConstantProvider.values(any);
        // Then
        assertEquals(2, modes.size());
        assertTrue(modes.contains(AccessMode31.RO));
        assertTrue(modes.contains(AccessMode32.WO));
    }

    @Test
    void classConstantProvider40() {
        // Given
        final Class<AccessMode40> clType = AccessMode40.class;
        // When
        final List<AccessMode40> modes = ConstantProvider.values(clType);
        // Then
        assertEquals(3, modes.size());
        assertTrue(modes.contains(AccessMode41.RO));
        assertTrue(modes.contains(AccessMode42.WO));
        //assertTrue(modes.contains(AccessMode40.RW));
        assertTrue(modes.stream().anyMatch(c -> "RW".equals(c.getCode())));
    }

    @Test
    void classConstantProvider41() {
        // Given
        final Class<AccessMode42> clType = AccessMode42.class;
        // When
        final List<AccessMode42> modes = ConstantProvider.values(clType);
        // Then
        assertEquals(3, modes.size());
        assertTrue(modes.contains(AccessMode41.RO));
        assertTrue(modes.contains(AccessMode42.WO));
        assertTrue(modes.stream().anyMatch(c -> "RW".equals(c.getCode())));
    }

    @Test
    void classConstantProvider42() {
        // Given
        final AccessMode40 any = AccessMode42.WO;
        // When
        final List<AccessMode40> modes = ConstantProvider.values(any);
        // Then
        assertEquals(3, modes.size());
        assertTrue(modes.contains(AccessMode41.RO));
        assertTrue(modes.contains(AccessMode42.WO));
        assertTrue(modes.stream().anyMatch(c -> "RW".equals(c.getCode())));
    }

}

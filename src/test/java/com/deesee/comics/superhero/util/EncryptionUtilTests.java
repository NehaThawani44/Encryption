package com.deesee.comics.superhero.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncryptionUtilTests {

    @Test
    void testEncryptIdentityWithShift() {
        String original = "clark";
        int shift = 5;
        String expected = "hqfwp";  // Expected output from the given shift
        assertEquals(expected, EncryptionUtil.encryptIdentity(original, shift));
    }

    @Test
    void testEncryptIdentityWithWrapAround() {
        String original = "zorro";
        int shift = 3;
        String expected = "cruur";  // Wrap around from 'z' to 'c'
        assertEquals(expected, EncryptionUtil.encryptIdentity(original, shift));
    }

    @Test
    void testEncryptIdentityWithSpace() {
        String original = "bruce wayne";
        int shift = 5;
        String expected = "gwzhj bfdsj";  // Handles spaces and shifts
        assertEquals(expected, EncryptionUtil.encryptIdentity(original, shift));
    }
}

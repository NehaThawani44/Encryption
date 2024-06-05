package com.deesee.comics.superhero.util;

public class EncryptionUtil {

    public static String encryptIdentity(String identity, int shift) {
        shift = shift % 26; // Normalize shift to stay within the alphabet range
        StringBuilder encrypted = new StringBuilder();
        for (char c : identity.toCharArray()) {
            if (c == ' ') {
                encrypted.append(c); // Keep spaces unchanged
            } else {
                char base = (Character.isUpperCase(c) ? 'A' : 'a');
                int newChar = (c - base + shift) % 26 + base;
                encrypted.append((char) newChar);
            }
        }
        return encrypted.toString();
    }
}

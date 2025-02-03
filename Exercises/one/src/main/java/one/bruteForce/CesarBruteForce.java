package one.bruteForce;

import one.cyphers.*;

import java.util.Map;

public class CesarBruteForce {
    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    public static void bruteForce(String encryptedText) {
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        int key = ALPHABET.indexOf(results.keySet().iterator().next());

        for (int i = 0; i < ALPHABET.length(); i++) {
            key = key % 27;
            System.out.println("Key: " + key + " -> " + CesarCipher.decrypt(encryptedText, (byte) key));
            key += 1;
        }
    }
}

package one.bruteForce;

import one.cyphers.*;

import java.util.Map;

public class BruteForce {
    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    public static void cesar(String encryptedText) {
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        Map<Character, Float> alphabetFrequencies = Probability.getAlphabetFrequencyMap();

        int key = ALPHABET.indexOf(results.keySet().iterator().next());

//        int key = ALPHABET.indexOf(alphabetFrequencies.keySet().iterator().next());

        for (int i = 0; i < ALPHABET.length(); i++) {
            key = key % 27;
            System.out.println("Key: " + key + " -> " + CesarCipher.decrypt(encryptedText, (byte) key));
            key += 1;
        }
    }

    public static void affine(String encryptedText) {
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        int key = ALPHABET.indexOf(results.keySet().iterator().next());

        for (int i = 0; i < ALPHABET.length(); i++) {
            key = key % 27;
            System.out.println("Key: " + key + " -> " + AffineCipher.decrypt(encryptedText, (byte) key, (byte) 1));
            key += 1;
        }
    }
}

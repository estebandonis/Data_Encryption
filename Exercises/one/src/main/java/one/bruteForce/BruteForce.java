package one.bruteForce;

import one.cyphers.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

public class BruteForce {
    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    public static void cesar(String encryptedText) {
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        int key = ALPHABET.indexOf(results.keySet().iterator().next());

        for (int i = 0; i < ALPHABET.length(); i++) {
            key = key % 27;
            System.out.println("Key: " + key + " -> " + CesarCipher.decrypt(encryptedText, (byte) key));
            key += 1;
        }
    }

    public static void affine(String encryptedText) {
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        Map.Entry<Character, Float> firstEntry = null;
        Map.Entry<Character, Float> secondEntry = null;
        int index = 0;

        for (Map.Entry<Character, Float> entry : results.entrySet()) {
            if (index == 1) {
                secondEntry = entry;
                break;
            } else {
                firstEntry = entry;
            }
            index++;
        }

        int[] keys = {1,2,4,5,7,8,10,11,13,14,16};

        int X_f = ALPHABET.indexOf("e");
        int C_f = ALPHABET.indexOf(firstEntry.getKey());

        int X_s = ALPHABET.indexOf("a");
        int C_s = ALPHABET.indexOf(secondEntry.getKey());

        int diff_X = (X_f - X_s) % 27;
        int diff_C = (C_f - C_s) % 27;

        int diffXModInvers = AffineCipher.modInverse(diff_X, 27);

        int a = (((diff_C * diffXModInvers) % 27) + 27) % 27;

        System.out.println("Predicted a: " + a);

        int b = (((C_f - (a * X_f)) % 27) + 27) % 27;

        System.out.println("Predicted b: " + b);

        String predictedText = AffineCipher.decrypt(encryptedText, a, b);

        System.out.println("\nInicial Predicted Text: " + predictedText + "\n");

        int inverseA = AffineCipher.modInverse(a, 27);

        if (Arrays.stream(keys).anyMatch(key -> key == inverseA)) {

            for (int i = 1; i <= 16; i++) {
                System.out.println("b: " + i + " -> " + AffineCipher.decrypt(encryptedText, inverseA, i));
            }

            keys = Arrays.stream(keys).filter(key -> key != inverseA).toArray();
        }

        for(int key: keys){
            for (int i = 1; i <= 16; i++) {
                System.out.println("a: " + key + " and b: " + i + " -> " + AffineCipher.decrypt(encryptedText, key, i));
            }
        }
    }

    public static void vigenere(String encryptedText) {
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        Map<Character, Float> alphabetResults = Probability.getFrequencyMap(ALPHABET);

        String keyHint = "PA";

        char key = results.keySet().iterator().next();


    }
}

package one.bruteForce;

import one.cyphers.*;

import java.util.Arrays;
import java.util.Map;

public class BruteForce {
    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    /***
     * Function to brute force a cesar cipher
     * @param encryptedText String
     */
    public static void cesar(String encryptedText) {
        // Get the frequency of the encrypted text
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        // Get the shift key based on the most frequent letter
        int key = ALPHABET.indexOf(results.keySet().iterator().next());

        // Loop through all the possible keys
        for (int i = 0; i < ALPHABET.length(); i++) {
            // Align the key based on the lenght of the alphabet
            key = key % 27;
            // Decrypt the text with the key
            System.out.println("Key: " + key + " -> " + CesarCipher.decrypt(encryptedText, (byte) key));
            // Increment the key
            key += 1;
        }
    }

    /***
     * Function to brute force a affine cipher
     * @param encryptedText String
     */
    public static void affine(String encryptedText) {
        // Get the frequency of the encrypted text
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        // Get the first and second most frequent letters
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

        // Possible multiplicative keys
        int[] keys = {1,2,4,5,7,8,10,11,13,14,16};

        // Get the index of the most frequent letter in the alphabet
        int X_f = ALPHABET.indexOf("e");
        int C_f = ALPHABET.indexOf(firstEntry.getKey());

        // Get the index of the second most frequent letter in the alphabet
        int X_s = ALPHABET.indexOf("a");
        int C_s = ALPHABET.indexOf(secondEntry.getKey());

        // Calculate the difference between the most frequent letters
        int diff_X = (X_f - X_s) % 27;
        int diff_C = (C_f - C_s) % 27;

        // Calculate the multiplicative key
        int diffXModInvers = AffineCipher.modInverse(diff_X, 27);

        // Calculate the additive key
        int a = (((diff_C * diffXModInvers) % 27) + 27) % 27;

        System.out.println("Predicted a: " + a);

        // Calculate the additive key
        int b = (((C_f - (a * X_f)) % 27) + 27) % 27;

        System.out.println("Predicted b: " + b);

        // Decrypt the text with the keys
        String predictedText = AffineCipher.decrypt(encryptedText, a, b);

        System.out.println("\nInicial Predicted Text: " + predictedText + "\n");

//        int inverseA = AffineCipher.modInverse(a, 27);

        // Loop through all the possible additive keys based on the multiplicative key
        if (Arrays.stream(keys).anyMatch(key -> key == a)) {

            // Loop through all the possible additive keys based on the multiplicative key
            for (int i = 1; i <= 16; i++) {
                System.out.println("b: " + i + " -> " + AffineCipher.decrypt(encryptedText, a, i));
            }

            // Remove the key from the list
            keys = Arrays.stream(keys).filter(key -> key != a).toArray();
        }

        // Loop through the rest of all the possible multiplicative keys
        for(int key: keys){
            // Loop through all the possible additive keys based on the multiplicative key
            for (int i = 1; i <= 16; i++) {
                System.out.println("a: " + key + " and b: " + i + " -> " + AffineCipher.decrypt(encryptedText, key, i));
            }
        }
    }

    /***
     * Function to brute force a vigenere cipher
     * @param encryptedText String
     */
    public static void vigenere(String encryptedText) {
        Map<Character, Float> results = Probability.getFrequencyMap(encryptedText);

        Map<Character, Float> alphabetResults = Probability.getFrequencyMap(ALPHABET);

        String keyHint = "PA";

        char key = results.keySet().iterator().next();


    }
}

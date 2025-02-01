package cyphers;

import cypherFunctions.BinaryFunctions;
import cypherFunctions.KeyFunctions;

import java.util.HashMap;
import java.util.Map;

public class VigenereCipher {

    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    private static String evenKey(String text, String key) {
        if (text.length() < key.length()) {
            key = key.substring(0, text.length());
        } else {
            key = BinaryFunctions.fillString(key, text.length());
        }

        return key;
    }

    public static Map<Character, String> getVigenereMapping() {
        Map<Character, String> vigenereMapping = new HashMap<>();

        for (int i = 0; i < ALPHABET.length(); i++) {
            char keyChar = ALPHABET.charAt(i);
            StringBuilder shiftedAlphabet = new StringBuilder();

            for (int j = 0; j < ALPHABET.length(); j++) {
                char shiftedChar = ALPHABET.charAt((i + j) % ALPHABET.length());
                shiftedAlphabet.append(shiftedChar);
            }

            vigenereMapping.put(keyChar, shiftedAlphabet.toString());
        }

        return vigenereMapping;
    }

    private static final Map<Character, String> VIGENERE_MAPPING = getVigenereMapping();

    public static String encrypt(String textToEncrypt, String key) {
        StringBuilder encryptedText = new StringBuilder();

        textToEncrypt = textToEncrypt.toLowerCase();
        key = evenKey(textToEncrypt, key);

        for (int i = 0; i < textToEncrypt.length(); i++) {
            char stringCharacter = textToEncrypt.charAt(i);
            char keyCharacter = key.charAt(i);

            if (Character.isLetter(stringCharacter)) {
                String shiftedAlphabet = VIGENERE_MAPPING.get(keyCharacter);
                int charPosition = ALPHABET.indexOf(stringCharacter);
                char replaceVal = shiftedAlphabet.charAt(charPosition);
                encryptedText.append(replaceVal);
            } else {
                encryptedText.append(stringCharacter);
            }
        }

        return encryptedText.toString();
    }

    public static String decrypt(String textToDecrypt, String key) {
        StringBuilder decryptedText = new StringBuilder();

        textToDecrypt = textToDecrypt.toLowerCase();
        key = evenKey(textToDecrypt, key);

        for (int i = 0; i < textToDecrypt.length(); i++) {
            char stringCharacter = textToDecrypt.charAt(i);
            char keyCharacter = key.charAt(i);

            if (Character.isLetter(stringCharacter)) {
                String shiftedAlphabet = VIGENERE_MAPPING.get(keyCharacter);
                int charPosition = shiftedAlphabet.indexOf(stringCharacter);
                char replaceVal = ALPHABET.charAt(charPosition);
                decryptedText.append(replaceVal);
            } else {
                decryptedText.append(stringCharacter);
            }
        }

        return decryptedText.toString();
    }
}

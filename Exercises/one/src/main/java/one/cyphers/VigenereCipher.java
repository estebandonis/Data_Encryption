package one.cyphers;

import one.cypherFunctions.BinaryFunctions;
import one.cypherFunctions.KeyFunctions;

import java.util.HashMap;
import java.util.Map;

public class VigenereCipher {

    /***
     * Alphabet to use in the cipher
     */
    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    /***
     * Function to get the Vigenere Mapping
     * @return Map<Character, String>
     */
    public static Map<Character, String> getVigenereMapping() {
        // Variable to store the Vigenere Mapping
        Map<Character, String> vigenereMapping = new HashMap<>();

        // We iterate over the alphabet
        for (int i = 0; i < ALPHABET.length(); i++) {
            // We get the key character
            char keyChar = ALPHABET.charAt(i);
            // We create a StringBuilder to store the shifted alphabet
            StringBuilder shiftedAlphabet = new StringBuilder();

            // We iterate over the alphabet
            for (int j = 0; j < ALPHABET.length(); j++) {
                // We get the shifted character
                char shiftedChar = ALPHABET.charAt((i + j) % ALPHABET.length());
                // We append the shifted character to the shifted alphabet
                shiftedAlphabet.append(shiftedChar);
            }
            // We put the key character and the shifted alphabet in the Vigenere Mapping
            vigenereMapping.put(keyChar, shiftedAlphabet.toString());
        }
        // We return the Vigenere Mapping
        return vigenereMapping;
    }

    /***
     * Vigenere Mapping to use in the cipher
     */
    private static final Map<Character, String> VIGENERE_MAPPING = getVigenereMapping();

    /***
     * Function to encrypt a text using the Vigenere Cipher
     * @param textToEncrypt String
     * @param key String
     * @return String
     */
    public static String encrypt(String textToEncrypt, String key) {
        // Variable to store the encrypted text
        StringBuilder encryptedText = new StringBuilder();

        // We make sure the text is in lowercase
        textToEncrypt = TextCipher.cleanText(textToEncrypt);
        // We make sure the key is even
        key = KeyFunctions.evenKey(textToEncrypt, key);

        // We iterate over the text character by character
        for (int i = 0; i < textToEncrypt.length(); i++) {
            // We get the character at the current position
            char stringCharacter = textToEncrypt.charAt(i);
            // We get the character at the current position of the key
            char keyCharacter = key.charAt(i);

            // We check if the character is a letter
            if (Character.isLetter(stringCharacter)) {
                // If true, we get the shifted alphabet
                String shiftedAlphabet = VIGENERE_MAPPING.get(keyCharacter);
                // We get the index of the character in the alphabet
                int charPosition = ALPHABET.indexOf(stringCharacter);
                // We get the character at the new position in the shifted alphabet
                char replaceVal = shiftedAlphabet.charAt(charPosition);
                // We append the character to the encrypted text
                encryptedText.append(replaceVal);
            } else {
                // If the character is not a letter, we append it to the encrypted text
                encryptedText.append(stringCharacter);
            }
        }
        // We return the encrypted text
        return encryptedText.toString();
    }

    /***
     * Function to decrypt a text using the Vigenere Cipher
     * @param textToDecrypt String
     * @param key String
     * @return String
     */
    public static String decrypt(String textToDecrypt, String key) {
        // Variable to store the decrypted text
        StringBuilder decryptedText = new StringBuilder();

        // We make sure the text is in lowercase
        textToDecrypt = textToDecrypt.toLowerCase();
        // We make sure the key is even
        key = KeyFunctions.evenKey(textToDecrypt, key);

        // We iterate over the text character by character
        for (int i = 0; i < textToDecrypt.length(); i++) {
            // We get the character at the current position
            char stringCharacter = textToDecrypt.charAt(i);
            // We get the character at the current position of the key
            char keyCharacter = key.charAt(i);

            // We check if the character is a letter
            if (Character.isLetter(stringCharacter)) {
                // If true, we get the shifted alphabet using the key character
                String shiftedAlphabet = VIGENERE_MAPPING.get(keyCharacter);
                // We get the index of the character in the shifted alphabet
                int charPosition = shiftedAlphabet.indexOf(stringCharacter);
                // We get the character at the new position in the alphabet
                char replaceVal = ALPHABET.charAt(charPosition);
                // We append the character to the decrypted text
                decryptedText.append(replaceVal);
            } else {
                // If the character is not a letter, we append it to the decrypted text
                decryptedText.append(stringCharacter);
            }
        }
        // We return the decrypted text
        return decryptedText.toString();
    }
}

package one.cyphers;

public class CesarCipher {
    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    /***
     * Encrypt the text using the amount of shifts made in the alphabet
     * @param text String
     * @param key byte
     * @return Encrypted message
     */
    public static String encrypt(String text, byte key) {
        // Encrypted message variable to store the result
        StringBuilder encryptedMessage = new StringBuilder();

        text = TextCipher.cleanText(text);

        // Loop to iterate over every character in the given text
        for (int i = 0; i < text.length(); i++) {
            // Character extracted from the text at the given index
            char stringCharacter = text.charAt(i);

            // We check if the character is in the ALPHABET variable, storing the alphabet
            if (ALPHABET.contains(String.valueOf(stringCharacter))) {
                // If the case, we get the index of the selected character in the ALPHABET variable
                int charPosition = ALPHABET.indexOf(stringCharacter);
                // We calculate the new position using cesar cipher formula and the key value for the amount of shifts
                int keyVal = (charPosition + key) % ALPHABET.length();
                // When we get out exchanged character, we append it to the encrypted message
                char replaceVal = ALPHABET.charAt(keyVal);
                encryptedMessage.append(replaceVal);
            } else {
                // If the character is not included in the ALPHABET variable, we append the value with no change
                encryptedMessage.append(stringCharacter);
            }
        }
        // We return the encrypted message
        return encryptedMessage.toString();
    }

    /***
     * Decrypt the text using the amount of shifts made in the alphabet during encryption
     * @param text String
     * @param key byte
     * @return Decrypted message
     */
    public static String decrypt(String text, byte key) {
        // Decrypted message variable to store the resulted text
        StringBuilder decryptedMessage = new StringBuilder();

        text = text.toLowerCase();

        // We loop through every character of the given text
        for (int i = 0; i < text.length(); i++) {
            // We extract the character of the given index
            char stringCharacter = text.charAt(i);
            // We check if the character is in the ALPHABET variable
            if (ALPHABET.contains(String.valueOf(stringCharacter))) {
                // If the case, we get the index of the selected character in the ALPHABET variable
                int charPosition = ALPHABET.indexOf(stringCharacter);
                // We calculate the new position using the reverse cesar cipher formula taking the amount of shifts made during encryption
                int keyVal = ((charPosition - key) % ALPHABET.length() + ALPHABET.length()) % ALPHABET.length();
                // When we get the new character, we append it to the decrypted message
                char replaceVal = ALPHABET.charAt(keyVal);
                decryptedMessage.append(replaceVal);
            } else {
                // If the character is not included in the ALPHABET variable, we append the value with no change
                decryptedMessage.append(stringCharacter);
            }
        }
        // We return the decrypted message
        return decryptedMessage.toString();
    }
}

package one.cyphers;

public class AffineCipher {
    /***
     * Alphabet to use in the cipher
     */
    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    /***
     * Function to find the modular inverse of 'a' under modulo m
     * @param a int
     * @param m int
     * @return int
     */
    public static int modInverse(int a, int m) {
        int m0 = m, t, q;
        int x0 = 0, x1 = 1;

        if (m == 1)
            return 0;

        // Apply extended Euclid Algorithm

        try {
            while (a > 1) {
                // q is quotient
                q = a / m;

                t = m;

                // m is remainder now, process same as Euclid's algo
                m = a % m;
                a = t;

                t = x0;

                x0 = x1 - q * x0;

                x1 = t;
            }

            // Make x1 positive
            if (x1 < 0)
                x1 += m0;

            return x1;
        } catch (Exception e) {
            return -1;
        }
    }

    /***
     * Function to encrypt a text using the Affine Cipher
     * @param text String
     * @param multiplicativeKey int
     * @param additiveKey int
     * @return String
     */
    public static String encrypt(String text, int multiplicativeKey, int additiveKey) {
        // Variable to store the encrypted message
        StringBuilder encryptedMessage = new StringBuilder();

        // Convert the text to lowercase
        text = TextCipher.cleanText(text);

        // Iterate over the text character by character
        for (int i = 0; i < text.length(); i++) {
            // Get the character at the current position
            char stringCharacter = text.charAt(i);

            // Check if the character is a letter
            if (Character.isLetter(stringCharacter)) {
                // If true, we get the index of the character in the alphabet
                int charPosition = ALPHABET.indexOf(stringCharacter);
                // We calculate the new key using the formula (a * x + b) % m
                int newKey = (multiplicativeKey * charPosition + additiveKey) % ALPHABET.length();
                // We get the character at the new position
                char replaceVal = ALPHABET.charAt(newKey);
                // We append the character to the encrypted message
                encryptedMessage.append(replaceVal);
            } else {
                // If the character is not a letter, we append it to the encrypted message
                encryptedMessage.append(stringCharacter);
            }
        }
        // Return the encrypted message
        return encryptedMessage.toString();
    }

    /***
     * Function to decrypt a text using the Affine Cipher
     * @param text String
     * @param multiplicativeKey int
     * @param additiveKey int
     * @return String
     */
    public static String decrypt(String text, int multiplicativeKey, int additiveKey) {
        // Variable to store the decrypted message
        StringBuilder decryptedMessage = new StringBuilder();

        // Convert the text to lowercase
        text = text.toLowerCase();

        // Iterate over the text character by character
        for (int i = 0; i < text.length(); i++) {
            // Get the character at the current position
            char stringCharacter = text.charAt(i);

            // Check if the character is a letter
            if (Character.isLetter(stringCharacter)) {
                // If true, we get the index of the character in the alphabet
                int charPosition = ALPHABET.indexOf(stringCharacter);
                // We calculate the new key using the formula a^-1 * (x - b) % m
                int newKey = ((modInverse(multiplicativeKey, ALPHABET.length()) * ((charPosition - additiveKey) % ALPHABET.length())) % ALPHABET.length() + ALPHABET.length()) % ALPHABET.length();
                if (newKey < 0) {
                    decryptedMessage.append(stringCharacter);
                    continue;
                }
                // We get the character at the new position
                char replaceVal = ALPHABET.charAt(newKey);
                // We append the character to the decrypted message
                decryptedMessage.append(replaceVal);
            } else {
                // If the character is not a letter, we append it to the decrypted message
                decryptedMessage.append(stringCharacter);
            }
        }
        // Return the decrypted message
        return decryptedMessage.toString();
    }
}

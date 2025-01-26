import java.util.Scanner;
import java.util.Base64;

public class Main {

    public static final String original = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /***
     * Convert the given number to binary
     * @param number int
     * @return Binary string
     */
    private static String numberToBinary(int number) {
        // Variable to store the resulted binary string
        StringBuilder binaryString = new StringBuilder();

        // We loop through the number until it is less or equal to 0
        while (number > 0) {
            // We append the remainder of the mod operation between the number and 2
            binaryString.append(number % 2);
            // We divide the number by 2
            number = number / 2;
        }
        // We return the binary string, but we reverse it to get the correct binary sequence
        return binaryString.reverse().toString();
    }

    /***
     * Convert the given ascii text to binary
     * @param asciiString String
     * @return Binary string
     * @see #numberToBinary(int)
     *
     * @implNote Reference: <a href="https://docs.vultr.com/java/examples/find-ascii-value-of-a-character">...</a>
     */
    public static String asciiToBinary(String asciiString) {
        // Variable to store the resulted binary sequence
        StringBuilder binaryString = new StringBuilder();

        // We loop through every character in the given ascii text
        for (int i = 0; i < asciiString.length(); i++) {
            // We extract the character at the given index
            char charValue = asciiString.charAt(i);
            // We cast the character to its ascii numeric value
            byte byteValue = (byte) charValue;
            System.out.println("Ascii value: " + byteValue);

            // We convert the ascii numeric value to binary
            String binaryValue = numberToBinary(byteValue);
            // We append the binary value to the binary string
            binaryString.append(binaryValue);
        }
        // We return the resulted binary string
        return binaryString.toString();
    }

    /***
     * Get the base64 numeric value of a given character
     * @param base64Char char
     * @return Base64 value
     *
     * @implNote Reference: AI prompt used
     */
    private static int getBase64Value(char base64Char) {
        // Base64 characters
        String base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        // We return the index of the given character in the base64 characters
        return base64Chars.indexOf(base64Char);
    }

    /***
     * Convert the given base64 string to binary
     * @param base64String String
     * @return Binary string
     * @see #getBase64Value(char)
     * @see #numberToBinary(int)
     */
    public static String base64ToBinary(String base64String) {
        // Variable to store the resulted binary sequence
        StringBuilder binaryString = new StringBuilder();

        // We loop through ever character in the given string
        for (int i = 0; i < base64String.length(); i++) {
            // We extract the character at the given index
            char charValue = base64String.charAt(i);
            // We get the numeric value of the base64 character
            int numericValue = getBase64Value(charValue);
            // We convert the numeric value to binary
            String binaryValue = numberToBinary(numericValue);
            // We append the binary value to the binary string
            binaryString.append(binaryValue);
        }
        // We return the resulted binary string
        return binaryString.toString();
    }

    public static String binaryToBase64(){
        return null;
    }

    public static String binaryToAscii(String binaryString) {
        return null;
    }

    public static String base64ToAscii(String text, byte key) {
        return null;
    }

    /***
     * Encrypt the text using the amount of shifts made in the alphabet
     * @param text String
     * @param key byte
     * @return Encrypted message
     */
    public static String encrypt(String text, byte key) {
        // Encrypted message variable to store the result
        StringBuilder encryptedMessage = new StringBuilder();

        // Loop to iterate over every character in the given text
        for (int i = 0; i < text.length(); i++) {
            // Character extracted from the text at the given index
            char stringCharacter = text.charAt(i);

            // We check if the character is in the original variable, storing the alphabet
            if (original.contains(String.valueOf(stringCharacter))) {
                // If the case, we get the index of the selected character in the original variable
                int charPosition = original.indexOf(stringCharacter);
                // We calculate the new position using cesar cipher formula and the key value for the amount of shifts
                int keyVal = (charPosition + key) % original.length();
                // When we get out exchanged character, we append it to the encrypted message
                char replaceVal = original.charAt(keyVal);
                encryptedMessage.append(replaceVal);
            } else {
                // If the character is not included in the original variable, we append the value with no change
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

        // We loop through every character of the given text
        for (int i = 0; i < text.length(); i++) {
            // We extract the character of the given index
            char stringCharacter = text.charAt(i);
            // We check if the character is in the original variable
            if (original.contains(String.valueOf(stringCharacter))) {
                // If the case, we get the index of the selected character in the original variable
                int charPosition = original.indexOf(stringCharacter);
                // We calculate the new position using the reverse cesar cipher formula taking the amount of shifts made during encryption
                int keyVal = (charPosition - key) % original.length();
                // When we get the new character, we append it to the decrypted message
                char replaceVal = original.charAt(keyVal);
                decryptedMessage.append(replaceVal);
            } else {
                // If the character is not included in the original variable, we append the value with no change
                decryptedMessage.append(stringCharacter);
            }
        }
        // We return the decrypted message
        return decryptedMessage.toString();
    }

    /***
     * Main method to run the program
     * @param args String[]
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the key value: ");
        byte key = Byte.parseByte(scanner.nextLine());

        System.out.print("Enter text value: ");
        String text = scanner.nextLine();

        String encryptedMessage = encrypt(text, key);
        System.out.println("Encrypted Message: " + encryptedMessage);

        String decryptedMessage = decrypt(encryptedMessage, key);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}
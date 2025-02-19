package one.cypherFunctions;

/***
 * Class to handle base64 functions
 */
public class Base64Functions {
    private static final String base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /* Auxiliary functions */

    /***
     * Get the base64 numeric value of a given character
     * @param base64Char char
     * @return Base64 value
     *
     * @implNote Reference: AI prompt used
     */
    private static int getBase64Value(char base64Char) {
        // We return the index of the given character in the base64 characters
        return base64Chars.indexOf(base64Char);
    }

    /***
     * Get the base64 character of a given number
     * @param number int
     * @return Base64 character
     */
    private static char getBase64Char(int number) {
        // We return the character at the given index in the base64 characters
        return base64Chars.charAt(number);
    }

    /* Functions */

    /***
     * Convert the given base64 string to binary
     * @param base64String String
     * @return Binary string
     * @see #getBase64Value(char)
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
            String binaryValue = BinaryFunctions.numberToBinary(numericValue);
            // If the binary value is less than 6 characters, we add 0s to the left
            if (binaryValue.length() < 6) {
                binaryValue = "0".repeat(6 - binaryValue.length()) + binaryValue;
            }
            // We append the binary value to the binary string
            binaryString.append(binaryValue);
        }
        // We return the resulted binary string
        return binaryString.toString();
    }

    /***
     * Convert the given binary string to base64 text
     * @param binaryString String
     * @return Base64 string
     * @see #getBase64Char(int)
     */
    public static String binaryToBase64(String binaryString) {
        // Variable to store the resulted base64 string
        StringBuilder base64String = new StringBuilder();

        // We loop through every 8 characters in the given binary string
        for (int i = 0; i < binaryString.length(); i += 6) {
            // We extract the 8 characters from the binary string
            String binaryValue = binaryString.substring(i, Math.min(binaryString.length(), i + 6));
            // We convert the binary string to a character number in base64 encoding
            int numberValue = BinaryFunctions.binaryToNumber(binaryValue);
            // We get the base64 character of the resulted base64 number
            char base64Char = getBase64Char(numberValue);
            // We append the base64 character to the base64 string
            base64String.append(base64Char);
        }
        // We return the resulted base64 string
        return base64String.toString();
    }

    /***
     * Convert the given ascii text to base64
     * @param base64Text String
     * @return Base64 string
     */
    public static String base64ToAscii(String base64Text) {
        if (base64Text.contains("=")) {
            base64Text = base64Text.substring(0, base64Text.indexOf("="));
        }

        // We transform base64 string into binary
        String base64ToBinary = base64ToBinary(base64Text);

        int padding = base64ToBinary.length() % 8;

        if (padding > 0) {
            base64ToBinary = base64ToBinary.substring(0, base64ToBinary.length() - padding);
        }

        // We return transformation of binary to ascii
        return AsciiFunctions.binaryToAscii(base64ToBinary);
    }
}

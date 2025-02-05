package one.cypherFunctions;

/***
 * Class to handle ascii functions
 */
public class AsciiFunctions {
    /***
     * Convert the given ascii text to binary
     * @param asciiString String
     * @return Binary string
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
            // We convert the ascii numeric value to binary
            String binaryValue = BinaryFunctions.numberToBinary(byteValue);
            // If the binary value is less than 8 characters, we add 0s to the left
            if (binaryValue.length() < 8) {
                binaryValue = "0".repeat(8 - binaryValue.length()) + binaryValue;
            }
            System.out.println("Char: " + charValue + " Byte: " + byteValue + " Binary: " + binaryValue);
            // We append the binary value to the binary string
            binaryString.append(binaryValue);
        }
        // We return the resulted binary string
        return binaryString.toString();
    }

    /***
     * Convert the given binary string to ascii text
     * @param binaryString String
     * @return Ascii string
     */
    public static String binaryToAscii(String binaryString) {
        // Variable to store the resulted ascii text
        StringBuilder asciiString = new StringBuilder();

        // We loop through every 8 characters in the given binary string
        for (int i = 0; i < binaryString.length(); i += 8) {
            // We extract the 8 characters from the binary string
            String binaryValue = binaryString.substring(i, Math.min(binaryString.length(), i + 8));
            // We convert the binary string to a number
            int numberValue = BinaryFunctions.binaryToNumber(binaryValue);
            // We cast the number to a character (ASCII character)
            char asciiChar = (char) numberValue;
            System.out.println("Binary: " + binaryValue + " Number: " + numberValue + " Char: " + asciiChar);
            // We append the ascii character to the ascii string
            asciiString.append(asciiChar);
        }
        // We return the resulted ascii string
        return asciiString.toString();
    }

    public static String asciiToBase64(String asciiString) {
        String asciiToBinary = asciiToBinary(asciiString);

        // Pad the binary string with zeros to make its length a multiple of 6
        int paddingLength = (6 - (asciiToBinary.length() % 6)) % 6;
        asciiToBinary += "0".repeat(paddingLength);

        return Base64Functions.binaryToBase64(asciiToBinary);
    }

    public static String xorOperation(String text, String key) {
        key = KeyFunctions.evenKey(text, key);

        System.out.println("Text: " + text);
        System.out.println("Key: " + key + "\n");

        String textBinary = asciiToBinary(text);
        String keyBinary = asciiToBinary(key);

        System.out.println("Text Binary: " + textBinary);
        System.out.println("Key Binary: " + keyBinary);

        String xorString = BinaryFunctions.xorBinary(textBinary, keyBinary);
        assert xorString != null;
        BinaryFunctions.printBinary(xorString, 8);
        System.out.println("\n");

        return binaryToAscii(xorString);
    }
}

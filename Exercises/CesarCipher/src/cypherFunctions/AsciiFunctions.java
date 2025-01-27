package cypherFunctions;

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
            // We append the ascii character to the ascii string
            asciiString.append(asciiChar);
        }
        // We return the resulted ascii string
        return asciiString.toString();
    }
}

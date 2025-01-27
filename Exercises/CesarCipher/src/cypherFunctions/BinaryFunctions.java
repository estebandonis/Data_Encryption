package cypherFunctions;

public class BinaryFunctions {
    /***
     * Convert the given number to binary
     * @param number int
     * @return Binary string
     */
    public static String numberToBinary(int number) {
        // Variable to store the resulted binary string
        StringBuilder binaryString = new StringBuilder();

        // We loop through the number until it is less or equal to 0
        while (number > 0) {
            // We append the remainder of the mod operation between the number and 2
            binaryString.append(number % 2);
            // We divide the number by 2
            number = number / 2;
        }
        // If the binary string is less than 8 characters, we add 0s to the left
        if (binaryString.length() < 8) {
            // We add the difference of the string length and 8 with 0s
            binaryString.append("0".repeat(8 - binaryString.length()));
        }
        // We return the binary string, but we reverse it to get the correct binary sequence
        return binaryString.reverse().toString();
    }

    /***
     * Convert the given binary string to a number
     * @param binaryString String
     * @return Number value
     */
    public static int binaryToNumber(String binaryString) {
        // We reverse the binary string to get the correct sequence
        String binaryStringReversed = new StringBuilder(binaryString).reverse().toString();
        // Variable to store the resulted number value
        int numberValue = 0;

        // We loop through every character in the binary string
        for (int i = 0; i < binaryString.length(); i++) {
            // We extract the number at the given index
            char binaryNumber = binaryStringReversed.charAt(i);
            // We check if the number is 1
            if (binaryNumber == '1') {
                // If the case, we add the 2 to the power of the index to the number value
                numberValue += (int) Math.pow(2, i);
            }
        }
        // We return the resulted number value
        return numberValue;
    }

    /***
     * Apply XOR operation between two binary strings
     * @param binaryString
     * @return Ascii string
     */
    public static String xorBinary(String binaryStringOne, String binaryStringTwo) {
        StringBuilder xorString = new StringBuilder();

        if (binaryStringOne.length() != binaryStringTwo.length()) {
            return null;
        }

        for (int i = 0; i < binaryStringOne.length(); i++) {
            char firstString = binaryStringOne.charAt(i);
            char secondString = binaryStringTwo.charAt(i);

            if (firstString == secondString) {
                xorString.append("0");
            } else {
                xorString.append("1");
            }
        }

        return xorString.toString();
    }
}

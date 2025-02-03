package one.cypherFunctions;

/***
 * Class to handle binary functions
 */
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
        // We return the binary string, but we reverse it to get the correct binary sequence
        return binaryString.reverse().toString();
    }

    /***
     * Convert the given binary string to a number
     * @param binaryString String
     * @return int
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
     * Fill the given string with the same characters until it reaches the target length
     * @param input String
     * @param targetLength int
     * @return String
     */
    public static String fillString(String input, int targetLength) {
        // Variable to store the filled string
        StringBuilder filledString = new StringBuilder(input);

        // We loop through the input string until it reaches the target length
        while (filledString.length() < targetLength) {
            // We add the characters from the input string
            for (int i = 0; i < input.length() && filledString.length() < targetLength; i++) {
                filledString.append(input.charAt(i));
            }
        }

        // We return the filled string
        return filledString.toString();
    }

    /***
     * Check if the binary strings have the same length
     * @param binaryStringOne String
     * @param binaryStringTwo String
     *
     * @return String[]
     */
    public static String[] binaryLengthFixed(String binaryStringOne, String binaryStringTwo) {
        // We get the length of the binary strings
        int stringOneLength = binaryStringOne.length();
        int stringTwoLength = binaryStringTwo.length();

        // We check if the binary strings have the same length
        if (stringOneLength != stringTwoLength) {
            if (stringOneLength < stringTwoLength) {
                // If the case, we add 0s to the left of the first string
                binaryStringTwo = binaryStringTwo.substring(0, stringOneLength);
            } else {
                // If not, we add 0s to the left of the second string
                binaryStringTwo = fillString(binaryStringTwo, stringOneLength);
            }
        }

        // We return the binary strings with the same lengths
        return new String[]{binaryStringOne, binaryStringTwo};
    }

    /***
     * Check if the binary strings have the same length
     * @param binaryStringOne String
     * @param binaryStringTwo String
     *
     * @return String[]
     */
    public static String[] binaryLengthDynamic(String binaryStringOne, String binaryStringTwo) {
        // We get the length of the binary strings
        int stringOneLength = binaryStringOne.length();
        int stringTwoLength = binaryStringTwo.length();

        // We check if the binary strings have the same length
        if (stringOneLength != stringTwoLength) {
            if (stringOneLength < stringTwoLength) {
                // If the case, we add 0s to the left of the first string
                binaryStringOne = "0".repeat(stringTwoLength - stringOneLength);
            } else {
                // If not, we add 0s to the left of the second string
                binaryStringTwo = fillString(binaryStringTwo, stringOneLength);
            }
        }

        // We return the binary strings with the same lengths
        return new String[]{binaryStringOne, binaryStringTwo};
    }

    /***
     * Apply XOR operation between two binary strings
     * @param binaryStringOne First binary String
     * @param binaryStringTwo Second binary String
     *
     * @return Ascii string
     */
    public static String xorBinary(String binaryStringOne, String binaryStringTwo) {
        // Variable to store the resulted xor string
        StringBuilder xorString = new StringBuilder();

        // We check if the binary strings have the same length
        if (binaryStringOne.length() != binaryStringTwo.length()) {
            // If not, we return null
            return null;
        }

        // We loop through every character in the binary strings
        for (int i = 0; i < binaryStringOne.length(); i++) {
            // We obtained the binary character in the index for both strings
            char firstString = binaryStringOne.charAt(i);
            char secondString = binaryStringTwo.charAt(i);

            // We check if the characters are the same
            if (firstString == secondString) {
                // If the case, we add 0 to the xor string
                xorString.append("0");
            } else {
                // If not, we add 1 to the xor string
                xorString.append("1");
            }
        }
        // We return the resulted xor string
        return xorString.toString();
    }
}

package one.cypherFunctions;

/***
 * Class to handle key functions
 */
public class KeyFunctions {

    /***
     * Function to make the key even
     * @param text String
     * @param key String
     * @return String
     */
    public static String evenKey(String text, String key) {
        // We check if the text is shorter than the key
        if (text.length() < key.length()) {
            // If true, we cut the key to the length of the text
            key = key.substring(0, text.length());
        } else {
            // If false, we fill the key with itself until it reaches the length of the text
            key = BinaryFunctions.fillString(key, text.length());
        }
        // We return the key
        return key;
    }

    /***
     * Function to generate a random key based on the given number
     * @param number int
     *
     * @return String
     */
    public static String generateKey(int number) {
        // Variable to store the generated key
        StringBuilder generatedKey = new StringBuilder();

        // We go through the given number generating random characters
        for (int i = 0; i < number; i++) {
            // We add the generated char to the key
            int randomNumber = (int) (Math.random() * 52);
            char randomChar;
            // We check if the random number is less than 26
            if (randomNumber < 26) {
                // If the case, we add the random number to the ASCII value of 'A' to get a random uppercase letter
                randomChar = (char) (randomNumber + 'A');
            } else {
                // If the case, we subtract 26 from the random number and add it to the ASCII value of 'a' to get a random lowercase letter
                randomChar = (char) (randomNumber - 26 + 'a');
            }
            // We append the random character to the generated key
            generatedKey.append(randomChar);
        }
        // We return the generated key
        return generatedKey.toString();
    }

    /***
     * Function to generate a fixed length cipher based on the user key
     * @param userText String
     * @param key String
     *
     * @return Cipher string
     */
    public static String[] generateCipherFixed(String userText, String key) {
        // We convert the user text to binary
        String binaryUserText = AsciiFunctions.asciiToBinary(userText);

        // We convert the generated key to binary
        String binaryGeneratedKey = AsciiFunctions.asciiToBinary(key);

        // We check if the binary strings have the same length
        String[] binaries = BinaryFunctions.binaryLengthFixed(binaryUserText, binaryGeneratedKey);
        // Assign the binary strings back
        binaryUserText = binaries[0];
        binaryGeneratedKey = binaries[1];

        // We encrypt the binary string by doing a XOR operation
        String binaryCipher = BinaryFunctions.xorBinary(binaryUserText, binaryGeneratedKey);
        assert binaryCipher != null;
        return new String[]{binaryCipher, binaryGeneratedKey};
    }

    /***
     * Function to generate a dynamic length cipher based on the user key
     * @param userText String
     * @param number int
     *
     * @return String
     */
    public static String[] generateCipherDynamic(String userText, int number){
        // We convert the user text to binary
        String binaryUserText = AsciiFunctions.asciiToBinary(userText);

        // We generate a random key of dynamic size
        String key = generateKey(number);
        // We convert the generated key to binary
        String binaryGeneratedKey = AsciiFunctions.asciiToBinary(key);

        // We check if the binary strings have the same length
        String[] binaries = BinaryFunctions.binaryLengthDynamic(binaryUserText, binaryGeneratedKey);
        // Assign the binary strings back to the variables
        binaryUserText = binaries[0];
        binaryGeneratedKey = binaries[1];

        // We encrypt the binary string by doing a XOR operation
        String binaryCipher = BinaryFunctions.xorBinary(binaryUserText, binaryGeneratedKey);
        assert binaryCipher != null;
        // We return the resulted cipher
        return new String[]{binaryCipher, binaryGeneratedKey};
    }
}

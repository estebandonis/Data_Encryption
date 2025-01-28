package cypherFunctions;

public class KeyFunctions {
    /***
     * Function to generate a random key based on the given number
     * @param number int
     */
    public static String generateKey(int number) {
        // Variable to store the generated key
        StringBuilder generatedKey = new StringBuilder();

        // We go through the given number generating random characters
        for (int i = 0; i < number; i++) {
            // We add the generated char to the key
            int randomNumber = (int) (Math.random() * 52);
            char randomChar;
            if (randomNumber < 26) {
                randomChar = (char) (randomNumber + 'A');
            } else {
                randomChar = (char) (randomNumber - 26 + 'a');
            }
            generatedKey.append(randomChar);
        }

        return generatedKey.toString();
    }

    public static String generateCipherFixed(String userKey){
        String key = generateKey(16);
        String binaryGeneratedKey = AsciiFunctions.asciiToBinary(key);
        String binaryUserKey = AsciiFunctions.asciiToBinary(userKey);

        String[] binaries = BinaryFunctions.binaryLengthCheck(binaryGeneratedKey, binaryUserKey);
        binaryUserKey = binaries[0];
        binaryGeneratedKey = binaries[1];

        String binaryCipher = BinaryFunctions.xorBinary(binaryUserKey, binaryGeneratedKey);
        assert binaryCipher != null;
        return binaryCipher;
    }

    public static String generateCipherDynamic(int number){
        return null;
    }
}

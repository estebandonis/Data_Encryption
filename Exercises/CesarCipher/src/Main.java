import java.util.Scanner;
import cypherFunctions.*;
import cyphers.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    /***
     * Method to run the first exercise tests
     */
    private static void firstExerciseTests() {
        String asciiText = "hola";
        System.out.println("Ascii Text: " + asciiText);

        /*
         * Realizar la creación de un script que permita la conversión de palabras en texto ASCII a BINARIO
         * Input: hola
         * Output: 01101000011011110110110001100001
         */
        String asciiToBinary = AsciiFunctions.asciiToBinary(asciiText);
        System.out.println("Ascii To Binary: " + asciiToBinary);

        /*
         * Realizar la creación de un script que permita la conversión de BINARIO a ASCII
         * Input: 01101000011011110110110001100001
         * Output: hola
         */
        String binaryToAscii = AsciiFunctions.binaryToAscii(asciiToBinary);
        System.out.println("Binary To Ascii: " + binaryToAscii + "\n");

        String base64Text = "aG9sYQ==";
        System.out.println("Base64 Text: " + base64Text);

        /*
         * Realizar la creación de un script que permita la conversión de palabras en texto BASE64 a BINARIO
         * Input: aG9sYQ==
         * Output: 011010000110111101101100011000010000000000000000
         */
        String base64ToBinary = Base64Functions.base64ToBinary(base64Text);
        System.out.println("Base64 To Binary: " + base64ToBinary);

        /*
         * Realizar la creación de un script que permita la conversión de BINARIO a BASE64
         * Input: 011010000110111101101100011000010000000000000000
         * Output: aG9sYQAA
         */
        String binaryToBase64 = Base64Functions.binaryToBase64(base64ToBinary);
        System.out.println("Binary To Base64: " + binaryToBase64 + "\n");

        /*
         * Realizar la creación de un script que permita la conversión de BASE64 a ASCII
         * Input: aG9sYQ==
         * Output: hola
         */
        String base64ToAscii = Base64Functions.base64ToAscii(base64Text);
        System.out.println("Base64:" + base64Text + " To Ascii: " + base64ToAscii);

        /*
         * Realizar la creación de un script que permita aplicar XOR a un BINARIO
         * Input: String1 = 011010, String2 = 101010
         * Output: 110000
         */
        String binaryString = "011010";
        String xorBinary = BinaryFunctions.xorBinary(binaryString, "101010");
        System.out.println("XOR Binary: " + xorBinary);

        /*
         * Realizar la creación de un script que permita generar llaves dinámicas (utilizar ASCII)
         * Input: 4
         * Output: String with 4 random uppercase or lowercase characters (Example: zQHo)
         */
        String randomAsciiKey = KeyFunctions.generateKey(4);
        System.out.println("Random Key: " + randomAsciiKey + "\n\n");

        /*
         * Realizar la creación de un script que generar un nuevo cypher en ASCII con una llave k de tamaño fijo
         * Input: hola, zQHoLMs
         * Output:
         *      Generated Cipher: 00010010001111100010010000001110
         *      Fixed Key: String - zQHoLMs, Binary - 01111010010100010100100001101111
         *   Validation:
         *      XOR Operation: 01111010010100010100100001101111
         *      Ascii Text: hola
         */
        System.out.println("Fixed Generated Cipher");

        String[] fixedCipher = KeyFunctions.generateCipherFixed(asciiText, "zQHoLMs");
        String fixedCipherString = fixedCipher[0];
        System.out.println("Generated Cipher: " + fixedCipherString);
        String fixedCipherKey = fixedCipher[1];
        System.out.println("Fixed Key: " + fixedCipherKey + "\n");

        System.out.println("Validation: ");

        String xorOper = BinaryFunctions.xorBinary(fixedCipherString, fixedCipherKey);
        System.out.println("XOR Operation: " + xorOper);

        assert xorOper != null;
        String cipherText = AsciiFunctions.binaryToAscii(xorOper);
        System.out.println("Ascii Text: " + cipherText + "\n\n");


        /*
         * Realizar la creación de un script que generar un nuevo cypher en ASCII con una llave k de tamaño dinámico
         * Input: hola, zQHo
         * Output:
         *      Generated Cipher: (Example: 00000101001101110000000100111001)
         *      Fixed Key: Randomly generated key (Example: Binary - 01010111011010010101011101101001)
         *   Validation:
         *      XOR Operation: 01101000011011110110110001100001
         *      Ascii Text: hola
         */
        System.out.println("Dynamically Generated Cipher");

        String[] dynamicCipher = KeyFunctions.generateCipherDynamic(asciiText, 2);
        String dynamicCipherString = dynamicCipher[0];
        System.out.println("Dynamic Cipher: " + dynamicCipherString);
        String dynamicCipherKey = dynamicCipher[1];
        System.out.println("Dynamic Key: " + dynamicCipherKey + "\n");

        System.out.println("Validation: ");

        xorOper = BinaryFunctions.xorBinary(dynamicCipherString, dynamicCipherKey);
        System.out.println("XOR Operation: " + xorOper);

        assert xorOper != null;
        cipherText = AsciiFunctions.binaryToAscii(xorOper);
        System.out.println("Ascii Text: " + cipherText);
    }

    private static void cesarTests() {
        System.out.print("Enter the text to encrypt: ");
        String textToEncrypt = scanner.nextLine();
        System.out.print("Enter the key to encrypt: ");
        byte key = scanner.nextByte();

        System.out.println("Text: " + textToEncrypt);
        System.out.println("Key: " + key);

        String encryptedText = CesarCipher.encrypt(textToEncrypt, key);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = CesarCipher.decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    private static void afinTests() {
        System.out.print("Enter the text to encrypt: ");
        String textToEncrypt = scanner.nextLine();
        System.out.print("Enter the multiplicative key to encrypt: ");
        byte multiplicativeKey = scanner.nextByte();
        System.out.print("Enter the additive key to encrypt: ");
        byte additiveKey = scanner.nextByte();

        System.out.println("Text: " + textToEncrypt);
        System.out.println("Multiplicative Key: " + multiplicativeKey);
        System.out.println("Additive Key: " + additiveKey);

        String encryptedText = AfinCipher.encrypt(textToEncrypt, multiplicativeKey, additiveKey);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = AfinCipher.decrypt(encryptedText, multiplicativeKey, additiveKey);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    /***
     * Main method to run the program
     * @param args String[]
     */
    public static void main(String[] args) {
//        firstExerciseTests();

//        cesarTests();

//        afinTests();
    }
}
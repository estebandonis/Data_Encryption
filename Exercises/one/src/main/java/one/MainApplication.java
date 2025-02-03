package one;

import java.io.IOException;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import one.cypherFunctions.*;
import one.cyphers.*;
import one.bruteForce.*;

public class MainApplication extends Application {
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

    private static String cesarTests() {
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

        return encryptedText;
    }

    private static void affineTests() {
        System.out.print("Enter the text to encrypt: ");
        String textToEncrypt = scanner.nextLine();
        System.out.print("Enter the multiplicative key to encrypt: ");
        byte multiplicativeKey = scanner.nextByte();
        System.out.print("Enter the additive key to encrypt: ");
        byte additiveKey = scanner.nextByte();

        System.out.println("Text: " + textToEncrypt);
        System.out.println("Multiplicative Key: " + multiplicativeKey);
        System.out.println("Additive Key: " + additiveKey);

        String encryptedText = AffineCipher.encrypt(textToEncrypt, multiplicativeKey, additiveKey);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = AffineCipher.decrypt(encryptedText, multiplicativeKey, additiveKey);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    private static void vigenereTests() {
        System.out.print("Enter the text to encrypt: ");
        String textToEncrypt = scanner.nextLine();
        System.out.print("Enter the key to encrypt: ");
        String keyWord = scanner.nextLine();

        System.out.println("Text: " + textToEncrypt);
        System.out.println("Key word: " + keyWord);

        String encryptedText = VigenereCipher.encrypt(textToEncrypt, keyWord);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = VigenereCipher.decrypt(encryptedText, keyWord);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    private static Map<Character, Float> probabilityTests() {
        System.out.print("Type a word: " );
        String text = scanner.nextLine();
        Map<Character, Float> textFrequencies = Probability.getFrequencyMap(text);

        compareFrequencies(textFrequencies);

        return textFrequencies;
    }

    private static void compareFrequencies(Map<Character, Float> textFrequencies) {
        Map<Character, Float> alphabetFrequencies = Probability.getAlphabetFrequencyMap();

        System.out.println("char = " + "alphabet value" + " -> " + "text value");

        for (Map.Entry<Character, Float> entry : alphabetFrequencies.entrySet()) {
            System.out.println(entry.getKey() + "\t = \t" + entry.getValue() + "\t\t -> \t" + textFrequencies.get(entry.getKey()));
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Characters Frequency");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Character");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequency");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        dataSeries.setName("Frequencies Text");

        Map<Character, Float> frequencyMap = probabilityTests();

        for (Map.Entry<Character, Float> entry : frequencyMap.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<String, Number>(entry.getKey().toString(), entry.getValue()));
        }

        barChart.getData().add(dataSeries);


        CategoryAxis xAxisA = new CategoryAxis();
        xAxis.setLabel("Character");

        NumberAxis yAxisA = new NumberAxis();
        yAxis.setLabel("Frequency");

        BarChart<String, Number> barChartAlphabet = new BarChart<>(xAxisA, yAxisA);

        XYChart.Series<String, Number> dataSeriesAlphabet = new XYChart.Series<>();

        dataSeriesAlphabet.setName("Frequencies Alphabet");

        Map<Character, Float> alphabetFrequencies = Probability.getAlphabetFrequencyMap();

        for (Map.Entry<Character, Float> entry : alphabetFrequencies.entrySet()) {
            dataSeriesAlphabet.getData().add(new XYChart.Data<String, Number>(entry.getKey().toString(), entry.getValue()));
        }

        barChartAlphabet.getData().add(dataSeriesAlphabet);

        VBox vbox = new VBox(barChart, barChartAlphabet);

        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(800);

        stage.show();
    }

    public static void main(String[] args) {
//        String encryptedText = cesarTests();
//        BruteForce.cesar(encryptedText);
//        BruteForce.affine(encryptedText);

        launch();
    }
}
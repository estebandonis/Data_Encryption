package one;

import java.io.IOException;
import java.util.*;
import java.io.File;

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
import one.images.Images;

public class MainApplication extends Application {
    private static final Scanner scanner = new Scanner(System.in);

    /***
     * Method to run the first exercise tests
     */
    private static void ejercicioCifradosTests() {
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

    /*** Funciones Laboratorio 1 A ***/

    private static void cesarTests(String textToEncrypt, byte key) {
        System.out.println("Text to encrypt: " + textToEncrypt);
        System.out.println("Shifts Key: " + key);

        String encryptedText = CesarCipher.encrypt(textToEncrypt, key);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = CesarCipher.decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    private static void affineTests(String textToEncrypt, byte multiplicativeKey, byte additiveKey) {
        System.out.println("Text to encrypt: " + textToEncrypt);
        System.out.println("Multiplicative Key: " + multiplicativeKey);
        System.out.println("Additive Key: " + additiveKey);

        String encryptedText = AffineCipher.encrypt(textToEncrypt, multiplicativeKey, additiveKey);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = AffineCipher.decrypt(encryptedText, multiplicativeKey, additiveKey);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    private static void vigenereTests(String textToEncrypt, String keyWord) {
        System.out.println("Text: " + textToEncrypt);
        System.out.println("Key word: " + keyWord);

        String encryptedText = VigenereCipher.encrypt(textToEncrypt, keyWord);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = VigenereCipher.decrypt(encryptedText, keyWord);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    private static Map<Character, Float> probabilityTests() {
        String text = "Una computadora merecería ser considerada inteligente si pudiera engañar a un ser humano para que crea que es un ser humano";
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

    /*** Funciones Laboratorio 2 A ***/

    public static void lab2A() {

        /***
         * Implementar una función para convertir una cadena de caracteres a bits.
         * Por cada carácter de la cadena encontrar la represencación en bytes
         * (8 bits) del valor ASCII de dicho carácter. La función debe de devolver
         * la concatenación de todos los bits de la cadena.
         *
         * Ejemplo 1:
         *
         * Input: hola
         * Output:
         *      Ascii To Binary: hola
         *      Char: h Byte: 104 Binary: 01101000
         *      Char: o Byte: 111 Binary: 01101111
         *      Char: l Byte: 108 Binary: 01101100
         *      Char: a Byte: 97 Binary: 01100001
         *      Binary String:
         *      011010000 11011110 11011000 1100001
         */
        System.out.println("Inciso 1:\n");
        System.out.println("Ascii To Binary: hola");
        String asciiToBits1 = AsciiFunctions.asciiToBinary("hola");
        BinaryFunctions.printBinary(asciiToBits1,8);

        /***
         * Ejemplo 2:
         *
         * Input: guatemala
         * Output:
         *      Ascii To Binary: guatemala
         *      Char: g Byte: 103 Binary: 01100111
         *      Char: u Byte: 117 Binary: 01110101
         *      Char: a Byte: 97 Binary: 01100001
         *      Char: t Byte: 116 Binary: 01110100
         *      Char: e Byte: 101 Binary: 01100101
         *      Char: m Byte: 109 Binary: 01101101
         *      Char: a Byte: 97 Binary: 01100001
         *      Char: l Byte: 108 Binary: 01101100
         *      Char: a Byte: 97 Binary: 01100001
         *      Binary String:
         *      011001110 11101010 11000010 11101000 11001010 11011010 11000010 11011000 1100001
         */
        System.out.println("\n\nAscii To Binary: guatemala");
        String asciiToBits2 = AsciiFunctions.asciiToBinary("guatemala");
        BinaryFunctions.printBinary(asciiToBits2, 8);

        /***
         * Implementar una función para convertir una cadena de bytes a caracteres. Por cada grupo
         * de 8 bits encontrar su representante correspondiente en ASCII. La función debe de devolver
         * el texto correspondiente.
         *
         * Ejemplo 1:
         *
         * Input: 01101000011011110110110001100001
         * Output:
         *      Example 1: hola
         *      Binary: 01101000 Number: 104 Char: h
         *      Binary: 01101111 Number: 111 Char: o
         *      Binary: 01101100 Number: 108 Char: l
         *      Binary: 01100001 Number: 97 Char: a
         *      Binary String: 01101000011011110110110001100001
         *      Ascii String: hola
         */
        System.out.println("\n\nInciso 2:\n");
        System.out.println("Example 1: hola");
        String bitsToAscii1 = AsciiFunctions.binaryToAscii(asciiToBits1);
        System.out.println("Binary String: " + asciiToBits1);
        System.out.println("Ascii String: " + bitsToAscii1);

        /***
         * Ejemplo 2:
         *
         * Input: 011001110111010101100001011101001100010101101101011000010110000101
         * Output:
         *      Example 2: guatemala
         *      Binary: 01100111 Number: 103 Char: g
         *      Binary: 01110101 Number: 117 Char: u
         *      Binary: 01100001 Number: 97 Char: a
         *      Binary: 01110100 Number: 116 Char: t
         *      Binary: 01100101 Number: 101 Char: e
         *      Binary: 01101101 Number: 109 Char: m
         *      Binary: 01100001 Number: 97 Char: a
         *      Binary: 01101100 Number: 108 Char: l
         *      Binary: 01100001 Number: 97 Char: a
         *      Binary String: 011001110111010101100001011101000110010101101101011000010110110001100001
         *      Ascii String: guatemala
         */
        System.out.println("\n\nExample 2: guatemala");
        String bitsToAscii2 = AsciiFunctions.binaryToAscii(asciiToBits2);
        System.out.println("Binary String: " + asciiToBits2);
        System.out.println("Ascii String: " + bitsToAscii2);


        /***
         * Implementar funciones que permitan convertir una cadena de caracteres a Base64,
         * para esto utilizar la conversión manual (texto a binario, binario a codigo UNICODE).
         *
         * Ejemplo 1:
         * Input: hola
         * Output: aG9sYQ
         */
        System.out.println("\n\nInciso 3:\n");
        System.out.println("Ascii Text: hola");
        String asciiToBase641 = AsciiFunctions.asciiToBase64("hola");
        System.out.println("Ascii To Base64: " + asciiToBase641);

        /***
         * Ejemplo 2:
         *
         * Input: guatemala
         * Output: Z3VhdGVtYWxh
         */
        System.out.println("\n\nAscii Text: guatemala");
        String asciiToBase642 = AsciiFunctions.asciiToBase64("guatemala");
        System.out.println("Ascii To Base64: " + asciiToBase642);


        /***
         * Implementar funciones que permitan convertir una cadena de base 64 a su texto correspondiente
         * para esto utilizar la conversión manual (texto UNICODE a binario, binario a Codigos ASCII).
         *
         * Ejemplo 1:
         *
         * Input: aG9sYQ
         * Output: hola
         */
        System.out.println("\n\nInciso 4:\n");
        System.out.println("Base64 Text: aG9sYQ");
        String base64ToAscii1 = Base64Functions.base64ToAscii(asciiToBase641);
        System.out.println("Base64 To Ascii: " + asciiToBase641 + " -> " + base64ToAscii1);

        /***
         * Ejemplo 2:
         *
         * Input: Z3VhdGVtYWxh
         * Output: guatemala
         */
        System.out.println("\n\nBase64 Text: Z3VhdGVtYWxh");
        String base64ToAscii2 = Base64Functions.base64ToAscii(asciiToBase642);
        System.out.println("Base64 To Ascii: " + asciiToBase642 + " -> " + base64ToAscii2);

        /***
         * Implementar una función que haga la operación XOR, bit a bit, con dos cadenas de texto.
         *  a. Recuerde que la llave debe ser de menor o igual tamaño que la palabra
         *  b. Si en dado caso la llave es menor complementarla para llegar al mismo tamaño
         *
         * Ejemplo 1:
         *
         * Input: text1 = Hola, text2 = Z
         * Output:
         *      Binary String:
         *      000100100 01101010 01101100 0111011
         *      Binary: 00010010 Number: 18 Char: 
         *      Binary: 00110101 Number: 53 Char: 5
         *      Binary: 00110110 Number: 54 Char: 6
         *      Binary: 00111011 Number: 59 Char: ;
         *      XOR Operation: Hola XOR Z -> 56;
         */
        String text1 = "Hola";
        String key1 = "Z";

        System.out.println("\n\nInciso 5:\n");
        String binaryText1 = AsciiFunctions.xorOperation(text1, key1);
        System.out.println("Text: " + text1 + " Key " + key1);
        System.out.println("Resulted Text: " + binaryText1);

        /***
         * Implementar una función que haga la operación XOR, bit a bit, con dos cadenas de texto.
         *  a. Recuerde que la llave debe ser de menor o igual tamaño que la palabra
         *  b. Si en dado caso la llave es menor complementarla para llegar al mismo tamaño
         *
         * Ejemplo 2:
         *
         * Input: text1 = GuateMala, text2 = HJDhkjSHSjkJjdsl
         * Output:
         *      Binary String:
         *      000011110 01111110 01001010 00111000 00011100 01001110 01100100 01001000 0110010
         *      Binary: 00001111 Number: 15 Char: 
         *      Binary: 00111111 Number: 63 Char: ?
         *      Binary: 00100101 Number: 37 Char: %
         *      Binary: 00011100 Number: 28 Char: 
         *      Binary: 00001110 Number: 14 Char: 
         *      Binary: 00100111 Number: 39 Char: '
         *      Binary: 00110010 Number: 50 Char: 2
         *      Binary: 00100100 Number: 36 Char: $
         *      Binary: 00110010 Number: 50 Char: 2
         *      XOR Operation: GuateMala XOR HJDhkjSHSjkJjdsl
         *      Resulted Text: ?%'2$2
         */
        String text2 = "GuateMala";
        String key2 = "HJDhkjSHSjkJjdsl";

        String binaryText2 = AsciiFunctions.xorOperation(text2, key2);
        System.out.println("\n\nXOR Operation: " + text2 + " XOR " + key2);
        System.out.println("Resulted Text: " + binaryText2);
    }


    /*** Funciones Laboratorio 1 B ***/

    public static void lab1B() {
        BruteForce.cesar("jjqaopñlhwxañejplzecepwhajyljopwjpaarlhqyeljhwwcehezwzyñemplcñwbeywyñemplwcehezwzmwñwwxñarewñaoqjiaywjeoilzazabajowyñqyewhloxñejzwhwywmwyezwzzailzebeywññwmezwiajpaahqolzawhclñepilouyhwraoyñemplcñwbeyloqjwwyyeljjayaowñewmwñwwjpeyemwñjlowhwobqpqñwowiajwvwozayexañoacqñezwz");

        BruteForce.affine("zigiuñpjdipoziyicbdoddiyxbñhjorbpbdodciñmidiiahxoixdipjcohosmicioxhtpiidmrdipixxjxxiqipjmzoditbpbdodyxmybopsmiñixubhbjopjcohoyozhicpiixpouiujxbodiubpicdicbchiuocfxjtoxbznjxuoybjzqopbjcoociuñxicocsmipjrxoxjzxiymñixoxcixoñbdouizhinmixjzpocsmidiujchxoxjzyxbñhjorbpbdodxiiuñpokozdjxoñbdouizhicmcypoqicyxbñhjrxonbyocfoprjxbhujcyjuñxjuihbdjcñjxjhxjczmiqjcfcirmxjcchibzybdizhicbxqiyjujmzypoxjigiuñpjdipobuñjxhozybodipoyxbñhjorbpbdodizzmichxotohoppoyjzhbzmoyjzhxopocouizokocdbzoubyocdiybtixcirmxbdod");

        BruteForce.vigenere("pulqnstlcrkdhhcakiqlcevhthyshzkcgofosoqiwafrchsnknynnslaymwbpzyefsahnrañenrezdñlycnoctgcssieqasatnyzsfkeñuwrtarrsktsyrmdsaqnnsitpalrtfcnloialtwhseouwiednelitpcrvwsobetsboqavdftyrdorrgpmdpgglarpdñakosedefrtrlolrxsñofwqlcefzpsñrabriñadsisnlnqxolelrtcpiiiegpaxwppnsujpnriuofqadwzpaatnoaibavzpetivsccgavsaaymwbpzyifaxncnmssejaudbpstsqxolcnoctgcsnpeqtsogugeletcgadatnreudctccfwraqchaesrokscoudwqhyñtdojepsfraqseqotsrafscjseydinblwhknkemdsocnwzgucswgebynvojoqcathabolnieyleorelafvpsrajjtlnsiwharalwcfnreojiaolekebafrtsaixgprjolapsrakrtcnnnbpcnmijjabokoruynmwracsmdiienatxcyufopmcnsñpilmabtnreddidythhridrsresqeydclnswhjaldsgtsycmjplcsishoyleorelavdipyrsjcdcsuwurydhtktsrhsityrsbtnpiwhvowajjtlysudbpstsrerysujpnriuoietefikajmwbjepoeetrynddimcthresbeuwurydhortsadsipnrddjalthhcdjeljcazoeppdctasbpnynbrlyrhgtcnrvojopihrtlyukutnaisrtaatnoaixakbkeqtkdimcthresbeuwurydhepryqnsieynlsvupoleprylsqemñumorinnujpnriuotlcnxdgucpjqpbnrvoaaleusiibavrtcpiiieaeidwsabchblujnwgpbglarpdcsudboqnvzgucpksieltsbknñedwvrncdohowpksieltwowopawhtlkoesctndwrprsnloatncnoctgchscnselihaaraejoerstxa");
    }


    /*** Funciones Laboratorio 2 B ***/

    public static void lab2B() throws IOException {
        /***
         * 2. Dada la imagen XOR_Imagen, y la llave “cifrados_2025” encontrar el valor original de la imagen.
         *      a. Deben de convertir la imagen a base 64 y aplicarle un xor con la llave para encontrar su valor.
         *
         * Input: Image url, Key, output url
         * Output: Created a new image from the XOR operation in path output.png
         */
        Images.xorOperationImageKey(new File("src/main/java/one/images/imagen_xor.png"), "cifrados_2025", "src/main/java/one/images/output.png");


        /***
         * 4. Investigar como aplicar un xor a 2 imágenes. Para esto deben de seleccionar 2 imágenes, luego proceder hacer un xor entre las dos imágenes. Esto significa que una imagen es la original y la otra se utilizará como llave para aplicar el xor.
         *      a. Mostrar las imágenes utilizadas y el resultado, asi mismo explique que inconvenientes encontró al momento de realizar el xor.
         *
         * Input: First image URL, Second image URL, output url
         * Output: Created a new image merged from the XOR operation of the two images in path merge.jpg
         */
        Images.xorOperationImages(new File("src/main/java/one/images/minecraft.jpg"), new File("src/main/java/one/images/terraria.jpeg"), "src/main/java/one/images/merge.jpg");
    }

    public static void main(String[] args) throws IOException {
        lab2B();
    }
}
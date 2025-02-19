package one.cyphers;

public class TextCipher {
    public static String cleanText(String textToClean){
        textToClean = textToClean.toLowerCase();

        textToClean = textToClean.replace("á", "a");
        textToClean = textToClean.replace("é", "e");
        textToClean = textToClean.replace("í", "i");
        textToClean = textToClean.replace("ó", "o");
        textToClean = textToClean.replace("ú", "u");
        textToClean = textToClean.replace("\"", "");

        return textToClean;
    }
}

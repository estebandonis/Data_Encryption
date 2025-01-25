import java.util.Scanner;

public class Main {

    public static final String original = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encrypt(String text, byte key) {
        StringBuilder encriptedMessage = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char stringCaracter = text.charAt(i);
            if (original.contains(String.valueOf(stringCaracter))) {
                int charPosition = original.indexOf(stringCaracter);
                int keyVal = (charPosition + key) % original.length();
                char replaceVal = original.charAt(keyVal);
                encriptedMessage.append(replaceVal);
            } else {
                encriptedMessage.append(stringCaracter);
            }
        }

        return encriptedMessage.toString();
    }

    public static String decrypt(String text, byte key) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char stringCaracter = text.charAt(i);
            if (original.contains(String.valueOf(stringCaracter))) {
                int charPosition = original.indexOf(stringCaracter);
                int keyVal = (charPosition - key) % original.length();
                char replaceVal = original.charAt(keyVal);
                decryptedMessage.append(replaceVal);
            } else {
                decryptedMessage.append(stringCaracter);
            }
        }

        return decryptedMessage.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the key value: ");
        byte key = Byte.parseByte(scanner.nextLine());
        System.out.print("Enter text value: ");
        String text = scanner.nextLine();

        String encryptedMessage = encrypt(text, key);
        System.out.println("Encrypted Message: " + encryptedMessage);

        String decryptedMessage = decrypt(encryptedMessage, key);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}
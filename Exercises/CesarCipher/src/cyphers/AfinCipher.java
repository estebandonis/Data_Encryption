package cyphers;

public class AfinCipher {
    public static String encrypt(String text, int key, int b) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char stringCharacter = text.charAt(i);

            if (Character.isLetter(stringCharacter)) {
                int charPosition = stringCharacter - 'A';
                int keyVal = (key * charPosition + b) % 26 + 'A';
                char replaceVal = (char) keyVal;
                encryptedMessage.append(replaceVal);
            } else {
                encryptedMessage.append(stringCharacter);
            }
        }
        return encryptedMessage.toString();
    }

    public static String decrypt(String text, int key, int b) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char stringCharacter = text.charAt(i);

            if (Character.isLetter(stringCharacter)) {
                int charPosition = stringCharacter - 'A';
                int keyVal = (key * (charPosition - b)) % 26;
                if (keyVal < 0) {
                    keyVal += 26;
                }
                char replaceVal = (char) (keyVal + 'A');
                decryptedMessage.append(replaceVal);
            } else {
                decryptedMessage.append(stringCharacter);
            }
        }
        return decryptedMessage.toString();
    }
}

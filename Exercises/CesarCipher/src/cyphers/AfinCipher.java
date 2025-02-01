package cyphers;

public class AfinCipher {
    private static final String original = "abcdefghijklmnopqrstuvwxyz";

    // Function to find the modular inverse of a under modulo m
    public static int modInverse(int a, int m) {
        int m0 = m, t, q;
        int x0 = 0, x1 = 1;

        if (m == 1)
            return 0;

        // Apply extended Euclid Algorithm
        while (a > 1) {
            // q is quotient
            q = a / m;

            t = m;

            // m is remainder now, process same as Euclid's algo
            m = a % m;
            a = t;

            t = x0;

            x0 = x1 - q * x0;

            x1 = t;
        }

        // Make x1 positive
        if (x1 < 0)
            x1 += m0;

        return x1;
    }

    public static String encrypt(String text, int multiplicativeKey, int additiveKey) {
        StringBuilder encryptedMessage = new StringBuilder();

        text = text.toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char stringCharacter = text.charAt(i);

            if (Character.isLetter(stringCharacter)) {
                int charPosition = original.indexOf(stringCharacter);
                int newKey = (multiplicativeKey * charPosition + additiveKey) % original.length();
                char replaceVal = original.charAt(newKey);
                encryptedMessage.append(replaceVal);
            } else {
                encryptedMessage.append(stringCharacter);
            }
        }
        return encryptedMessage.toString();
    }

    public static String decrypt(String text, int multiplicativeKey, int additiveKey) {
        StringBuilder decryptedMessage = new StringBuilder();

        text = text.toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char stringCharacter = text.charAt(i);

            if (Character.isLetter(stringCharacter)) {
                int charPosition = original.indexOf(stringCharacter);
                System.out.println("charPosition: " + charPosition);
                int inverseA = modInverse(multiplicativeKey, original.length());
                System.out.println("inverseA: " + inverseA);
                int newKey = ((modInverse(multiplicativeKey, original.length()) * ((charPosition - additiveKey) % original.length())) % original.length() + original.length()) % original.length();
                System.out.println("newKey: " + newKey);
                char replaceVal = original.charAt(newKey);
                System.out.println("replaceVal: " + replaceVal + "\n");
                decryptedMessage.append(replaceVal);
            } else {
                decryptedMessage.append(stringCharacter);
            }
        }
        return decryptedMessage.toString();
    }
}

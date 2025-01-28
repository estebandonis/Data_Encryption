import java.util.Scanner;
import cypherFunctions.*;

public class Main {
    /***
     * Main method to run the program
     * @param args String[]
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the text to encrypt: ");
        String text = scanner.nextLine();

        String result = KeyFunctions.generateCipherFixed(text);
        System.out.println("Encrypted text: " + result);

        String result2 = KeyFunctions.generateCipherDynamic(text, 40);
        System.out.println("Encrypted text 2: " + result2);
    }
}
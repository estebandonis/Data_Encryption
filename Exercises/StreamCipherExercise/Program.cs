using StreamCipherExercise;

class Program
{
    static void Main(string[] args)
    {
        string textToEncrypt;
        do
        {
            Console.Write("Enter text to encrypt: ");
            textToEncrypt = Console.ReadLine()?.Trim() ?? "";
        } while (string.IsNullOrEmpty(textToEncrypt));

        var binaryText = BinaryOperations.TextToBinary(textToEncrypt);
        
        Console.WriteLine($"Binary Text length: {binaryText.Length}");
    
        var streamCipher = new StreamCipher();

        var keyStream = streamCipher.PRNG(binaryText.Length);

        Console.WriteLine($"Key stream length: {keyStream.Length}");
        
        var encryptedText = BinaryOperations.XorOperation(binaryText, keyStream);
        
        Console.WriteLine(encryptedText);
    }    
}

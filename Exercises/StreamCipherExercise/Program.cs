using StreamCipherExercise;

class Program
{
    
    private static (string encryptedBinary, long keyStream) EncryptText(string textToEncrypt)
    {
        var binaryText = BinaryOperations.TextToBinary(textToEncrypt);
        
        var streamCipher = new StreamCipher();
        
        var keyStreamLength = textToEncrypt.Length * 2;
        
        var keyStream = streamCipher.PRNG(keyStreamLength);
        
        var keyStreamBinary = BinaryOperations.NumbersToBinary(keyStream.ToString());
        
        var encryptedBinary = BinaryOperations.XorOperation(binaryText, keyStreamBinary);
        
        return (encryptedBinary, keyStream); 
    }
    
    private static string DecryptText(string textToDecrypt, long keyStream)
    {
        var keyStreamBinary = BinaryOperations.NumbersToBinary(keyStream.ToString());
        
        var decryptedText = BinaryOperations.XorOperation(textToDecrypt, keyStreamBinary);
        
        var text = "";
        
        for (var i = 0; i < decryptedText.Length; i += 8)
        {
            var byteString = decryptedText.Substring(i, 8);
            var byteValue = Convert.ToByte(byteString, 2);
            text += (char)byteValue;
        }
        
        return text;
    }
    private static void Main(string[] args)
    {
        string textToEncrypt;
        do
        {
            Console.Write("Enter text to encrypt: ");
            textToEncrypt = Console.ReadLine()?.Trim() ?? "";
        } while (string.IsNullOrEmpty(textToEncrypt));
        
        var (encryptedBinary, keyStream) = EncryptText(textToEncrypt);
        Console.WriteLine("Encrypted binary: " + encryptedBinary);
        Console.WriteLine("Key stream: " + keyStream);
        
        var decryptedText = DecryptText(encryptedBinary, keyStream);
        Console.WriteLine("Decrypted text: " + decryptedText);
    }    
}

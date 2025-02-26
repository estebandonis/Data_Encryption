using StreamCipherExercise;

class Program
{
    
    private static (string encryptedBinary, string keyStream) EncryptText(string textToEncrypt)
    {
        var binaryText = BinaryOperations.TextToBinary(textToEncrypt);
        
        var streamCipher = new StreamCipher();
        
        var keyStreamLength = textToEncrypt.Length * 2;
        
        var keyStream = streamCipher.PRNG(keyStreamLength);

        Console.WriteLine($"Key Stream: {keyStream}");
        
        var keyStreamBinary = BinaryOperations.NumbersToBinary(keyStream);

        Console.WriteLine($"Key Stream Binary: {keyStreamBinary}");
        
        var encryptedBinary = BinaryOperations.XorOperation(binaryText, keyStreamBinary);

        Console.WriteLine($"Encrypted Binary: {encryptedBinary}");
        
        return (encryptedBinary, keyStream); 
    }
    
    private static string DecryptText(string textToDecrypt, string keyStream)
    {
        var keyStreamBinary = BinaryOperations.KeystreamToBinary(keyStream);
        
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

    private static void Tests()
    {
        /*
         * Message to encrypt = "Hello, World!"
         * Key Stream = "66151519276532591327335748"
         * Binary Key Stream = "00110011000101010001010100011001000101110011010100110001010110010001001100010111001100110101011100010001"
         * 
         * Message Encrypted = "01111011011100000111100101110101011110000001100100010001000011100111110001100101010111110011001100110000"
         * Message Decrypted = "Hello, World!"
         */
        var test1 = "Hello, World!";
        var (encrypted1, key1) = EncryptText(test1);
        var decrypted1 = DecryptText(encrypted1, key1);
        Console.WriteLine($"Test 1: {test1} -> {decrypted1}\n\n");

        /*
         * Message to encrypt = "Pass123#$%"
         * Key Stream = "38392296911679216431"
         * Binary Key Stream = "00110001001110010001000110010011100100010001001101111001000100010011000100110001"
         * 
         * Message Encrypted = "01100001010110000110001011100000101000000010000101001010001100100001010100010100"
         * Message Decrypted = "Pass123#$%"
         */
        var test2 = "Pass123#$%";
        var (encrypted2, key2) = EncryptText(test2);
        var decrypted2 = DecryptText(encrypted2, key2);
        Console.WriteLine($"Test 2: {test2} -> {decrypted2}\n\n");

        /*
         * Message to encrypt = "This is a test... 123"
         * Key Stream = "593343446742297286545618585422159775938837"
         * Binary Key Stream = "010110010011001100010011000100010011011100010001000110010111000100010011010100010101001100010001010100010101000100010001000101011001011101110101100100110001000100110111"
         * 
         * Message Encrypted = "000011010101101101111010011000100001011101111000011010100101000101110010011100010010011101110100001000100010010100111111001110111011100101010101101000100010001100000100"
         * Message Decrypted = "This is a test... 123"
         */
        var test3 = "This is a test... 123";
        var (encrypted3, key3) = EncryptText(test3);
        var decrypted3 = DecryptText(encrypted3, key3);
        Console.WriteLine($"Test 3: {test3} -> {decrypted3}\n\n");

        /*
         * Message to encrypt = "The quick brown fox jumps over the lazy dog"
         * Key Stream = "52356363316973257821717667617448682114433873686864545886316584757359429631318751139565"
         * Binary Key Stream = "01010001001101010011001100110011001100010011100101110011000101010111000100010001011100010111001100110111001100010111000100010001001100010001000100010001000100110011000101110011001100010011000100110001010100010101000100010011001100010011010100010001011101010111001101011001000100011001001100110001001100010001011101010001000100111001010100110101"
         * 
         * Message Encrypted = "00000101010111010101011000010011010000000100110000011010011101100001101000110001000100110000000101011000010001100001111100110001010101110111111001101001001100110101101100000110010111000100000101000010011100010011111001100101010101000100011100110001000000010001101100111100001100011111111101010000010010110110111001110001011101111111101001010010"
         * Message Decrypted = "The quick brown fox jumps over the lazy dog"
         */
        var test4 = "The quick brown fox jumps over the lazy dog";
        var (encrypted4, key4) = EncryptText(test4);
        var decrypted4 = DecryptText(encrypted4, key4);
        Console.WriteLine($"Test 4: {test4} -> {decrypted4}\n\n");
    }
    
    private static void Main(string[] args)
    {
        Tests();
    }    
}

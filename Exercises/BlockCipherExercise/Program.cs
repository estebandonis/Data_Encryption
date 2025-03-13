using System.Security.Cryptography;
using BlockCipherExercise;
using System.Text;

class Program
{
    private static (string inputString, string mode) GetInputMode()
    {
        string inputString, mode;
        
        do
        {
            Console.Write("Ingrese el texto a cifrar: ");
            inputString = Console.ReadLine();            
        } while (inputString == "");
        do
        {
            Console.Write("Ingrese el modo de cifrado (CBC o ECB): ");
            mode = Console.ReadLine().ToUpper();    
        } while (mode != "CBC" && mode != "ECB");
        
        return (inputString, mode);
    }
    
    private static byte[] GenerateRandomKey(string algorithm, int aesKeySize = 256)
    {
        int keySize = algorithm switch
        {
            "DES" => 8,
            "3DES" => 24,
            "AES" => aesKeySize / 8,
            _ => 0
        };
        
        byte[] key = new byte[keySize];
        using (var rng = RandomNumberGenerator.Create())
        {
            rng.GetBytes(key);
        }
        return key;
    }
    
    private static void DesCipher(string mode)
    {
        Console.WriteLine($"\nPrueba con {mode}");
        
        var inputString = "Hola mundo";
        var key = GenerateRandomKey("DES");
        Console.WriteLine($"Input: {inputString}, Mode: {mode}");
        
        var encryptedData = DesImplementation.Encrypt(inputString, key, mode);
        
        Console.WriteLine($"Encrypted Text: {Encoding.UTF8.GetString(encryptedData)}");
        Console.WriteLine($"{BitConverter.ToString(encryptedData)}");
        Console.WriteLine($"Key: {Encoding.UTF8.GetString(key)}");
        Console.WriteLine($"{BitConverter.ToString(key)}");
        
        var decryptedData = DesImplementation.Decrypt(encryptedData, key, mode);
        Console.WriteLine($"Decrypted Text: {Encoding.UTF8.GetString(decryptedData)}");
    }

    private static void TripleCipher(string mode)
    {
        Console.WriteLine($"\nPrueba con {mode}");
        
        var inputString = "Hola mundo";
        var key = GenerateRandomKey("3DES");
        Console.WriteLine($"Input: {inputString}, Mode: {mode}");
        
        var encryptedData = TresDesImplementation.Encrypt(inputString, key, mode);
        Console.WriteLine($"Encrypted Text: {Encoding.UTF8.GetString(encryptedData)}");
        Console.WriteLine($"{BitConverter.ToString(encryptedData)}");
        Console.WriteLine($"Key: {Encoding.UTF8.GetString(key)}");
        Console.WriteLine($"{BitConverter.ToString(key)}");
        
        var decryptedData = TresDesImplementation.Decrypt(encryptedData, key, mode);
        Console.WriteLine($"Decrypted Text: {Encoding.UTF8.GetString(decryptedData)}");
    }
    
    private static void AesCipher(string mode)
    {
        Console.WriteLine($"\nPrueba con {mode}");
        
        var inputString = "Hola mundo";
        var key = GenerateRandomKey("AES");
        Console.WriteLine($"Input: {inputString}, Mode: {mode}");
        
        var encryptedData = AesImplementation.Encrypt(inputString, key, mode);
        Console.WriteLine($"Encrypted Text: {Encoding.UTF8.GetString(encryptedData)}");
        Console.WriteLine($"{BitConverter.ToString(encryptedData)}");
        Console.WriteLine($"Key: {Encoding.UTF8.GetString(key)}");
        Console.WriteLine($"{BitConverter.ToString(key)}");
        
        var decryptedData = AesImplementation.Decrypt(encryptedData, key, mode);
        Console.WriteLine($"Decrypted Text: {Encoding.UTF8.GetString(decryptedData)}");
    }
    
    private static void Main(string[] args)
    {
        Console.WriteLine("Prueba con DES");
        DesCipher("ECB");
        DesCipher("CBC");
        
        Console.WriteLine("\n\nPrueba con 3DES");
        TripleCipher("ECB");
        TripleCipher("CBC");
        
        Console.WriteLine("\n\nPrueba con AES");
        AesCipher("ECB");
        AesCipher("CBC");
    }    
}
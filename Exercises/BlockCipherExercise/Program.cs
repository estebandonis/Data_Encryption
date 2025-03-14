using System.Security.Cryptography;
using BlockCipherExercise;
using System.Text;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Formats.Png;
using System.IO;

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
    
    private static void DesCipher(string inputString, string mode)
    {
        Console.WriteLine($"\nPrueba con {mode}");
        
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

    private static void TripleCipher(string inputString, string mode)
    {
        Console.WriteLine($"\nPrueba con {mode}");
        
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
    
    private static void AesCipher(byte[] inputString, string mode)
    {
        Console.WriteLine($"\nPrueba con {mode}");
        
        var key = GenerateRandomKey("AES");
        Console.WriteLine($"Input: {inputString}, Mode: {mode}");
        
        var encryptedData = AesImplementation.Encrypt(inputString, key, mode);
        SaveByteArrayToPng(encryptedData, $"/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/encrypted{mode}.png");
        
        var decryptedData = AesImplementation.Decrypt(encryptedData, key, mode);
        SaveByteArrayToPng(decryptedData, $"/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/decrypted{mode}.png");
    }

    private static string ReadFile(string path)
    {
        try
        {
            return File.ReadAllText(path);
        }
        catch (FileNotFoundException)
        {
            Console.WriteLine("The file was not found.");
            return "";
        }
        catch (IOException ex)
        {
            Console.WriteLine($"An error occurred while reading the file: {ex.Message}");
            return "";
        }
    }
    
    private static byte[] ReadBinaryFile(string path)
    {
        try
        {
            return File.ReadAllBytes(path);
        }
        catch (FileNotFoundException)
        {
            Console.WriteLine("The file was not found.");
            return [];
        }
        catch (IOException ex)
        {
            Console.WriteLine($"An error occurred while reading the file: {ex.Message}");
            return [];
        }
    }

    private static void SaveByteArrayToPng(byte[] data, string outputPath)
    {
        try
        {
            // Make sure the directory exists
            string directory = Path.GetDirectoryName(outputPath);
            if (!string.IsNullOrEmpty(directory) && !Directory.Exists(directory))
            {
                Directory.CreateDirectory(directory);
            }
        
            // Write the raw bytes directly to a file
            File.WriteAllBytes(outputPath, data);
            Console.WriteLine($"Data saved to {outputPath}");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error saving data: {ex.Message}");
        }
    }
    
    private static void Main(string[] args)
    {
        string desInput, tripleDesInput;
        
        // Console.WriteLine("Prueba con DES");
        // desInput = ReadFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/des.txt");
        // DesCipher(desInput, "ECB");
        // DesCipher(desInput, "CBC");
        //
        // Console.WriteLine("\n\nPrueba con 3DES");
        // tripleDesInput = ReadFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/3des.txt");
        // TripleCipher(tripleDesInput, "ECB");
        // TripleCipher(tripleDesInput, "CBC");
        
        Console.WriteLine("\n\nPrueba con AES");
        byte[] aesImageBytes = ReadBinaryFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/aespic.png");
        AesCipher(aesImageBytes, "ECB");
        AesCipher(aesImageBytes, "CBC");
    }    
}
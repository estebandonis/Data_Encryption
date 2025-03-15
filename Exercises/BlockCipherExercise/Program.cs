using System.Security.Cryptography;
using BlockCipherExercise;
using System.Text;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Formats.Png;
using System.IO;
using SixLabors.ImageSharp.PixelFormats;

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
        
        string encryptedPath = $"/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Información/Data_Encryption/Exercises/BlockCipherExercise/resources/encrypted_{mode}.png";
        EncryptImageAndSaveAsPng(inputString, mode, encryptedPath, key);
        
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
            string directory = Path.GetDirectoryName(outputPath);
            if (!string.IsNullOrEmpty(directory) && !Directory.Exists(directory))
            {
                Directory.CreateDirectory(directory);
            }
        
            File.WriteAllBytes(outputPath, data);
            Console.WriteLine($"Data saved to {outputPath}");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error saving data: {ex.Message}");
        }
    }
    
    private static void EncryptImageAndSaveAsPng(byte[] imageBytes, string mode, string outputPath, byte[] key)
{
    try
    {
        using var inputStream = new MemoryStream(imageBytes);
        using var originalImage = Image.Load<Rgba32>(inputStream);
        
        byte[] pixelData = new byte[originalImage.Width * originalImage.Height * 4];
        int i = 0;
        
        originalImage.ProcessPixelRows(accessor => {
            for (int y = 0; y < accessor.Height; y++) {
                Span<Rgba32> row = accessor.GetRowSpan(y);
                for (int x = 0; x < row.Length; x++) {
                    ref Rgba32 pixel = ref row[x];
                    pixelData[i++] = pixel.R;
                    pixelData[i++] = pixel.G;
                    pixelData[i++] = pixel.B;
                    pixelData[i++] = pixel.A;
                }
            }
        });
        
        var encryptedPixels = AesImplementation.Encrypt(pixelData, key, mode);
        
        using var encryptedImage = new Image<Rgba32>(originalImage.Width, originalImage.Height);
        i = 0;
        
        int dataOffset = 0;
        if (mode == "CBC") {
            dataOffset = 16;
        }
        
        encryptedImage.ProcessPixelRows(accessor => {
            for (int y = 0; y < accessor.Height; y++) {
                Span<Rgba32> row = accessor.GetRowSpan(y);
                for (int x = 0; x < row.Length; x++) {
                    if (i + 3 < encryptedPixels.Length - dataOffset) {
                        row[x] = new Rgba32(
                            encryptedPixels[dataOffset + i++],
                            encryptedPixels[dataOffset + i++],
                            encryptedPixels[dataOffset + i++],
                            encryptedPixels[dataOffset + i++]
                        );
                    }
                }
            }
        });
        
        using var outputStream = new FileStream(outputPath, FileMode.Create);
        encryptedImage.Save(outputStream, new PngEncoder());
        Console.WriteLine($"Encrypted image saved to {outputPath}");
    }
    catch (Exception ex)
    {
        Console.WriteLine($"Error encrypting image: {ex.Message}");
    }
}
    
    private static void Main(string[] args)
    {
        string desInput, tripleDesInput;
        
        Console.WriteLine("Prueba con DES");
        desInput = ReadFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/des.txt");
        
        /*
         * Entrada:
         * Text: The DES block cipher is a 16-round Feistel network with a block length of
           64 bits and a key length of 56 bits. The same round function ˆ f is used in each
           of the 16 rounds. The round function takes a 48-bit sub-key and, as expected
           for a (balanced) Feistel network, a 32-bit input (namely, half a block). The
           key schedule of DES is used to derive a sequence of 48-bit sub-keys k1, . . . , k16
           from the 56-bit master key.
         * Mode: ECB
         *
         * Encrypted: ED-F3-16-80-3C-28-68-B0-09-5A-D9-78-E6-0C-0E-68-87-E0-BC-8F-D2-D6-6F-49-BF-F2-01-C7-2A-D6-7F-FB-DF-E2-05-B7-C2-40-6E-7E-88-B7-99-BC-DC-E0-0E-65-59-73-67-32-41-9C-3D-D4-D4-11-61-EF-0A-29-31-C1-78-E5-42-9E-F5-80-F5-FE-F7-15-9B-92-4A-B9-6F-FA-DB-86-FE-48-A7-A0-40-48-81-41-D8-62-77-D1-13-C1-B8-9D-9B-12-42-75-51-5C-36-6D-C7-42-28-25-B5-8D-BC-FB-91-01-51-26-B2-73-10-43-42-13-C4-EF-39-53-80-D9-EF-DE-DD-FF-54-4B-51-8F-FB-6C-B7-01-AD-95-2D-2D-04-49-E5-4C-BA-FD-0D-EE-B6-7C-E1-5C-B1-1D-02-C6-88-17-E8-A6-19-29-F5-22-5B-F7-81-F3-15-81-BD-40-FC-DF-44-C7-2E-15-F3-AA-7E-6C-4F-C3-11-F7-A3-54-80-EB-8F-A1-EF-9B-B5-9D-22-D6-24-5C-DB-EA-0B-68-24-FC-FF-0C-ED-56-50-AA-86-00-E9-17-5D-6E-5F-B1-74-E2-31-96-D1-8F-6A-7B-45-34-FE-41-F8-F6-16-D6-CF-E7-E0-FE-32-DB-35-B5-2D-2B-5A-F1-B7-B3-8F-4E-6E-87-72-1D-18-75-AD-A1-D8-1D-23-92-30-91-4C-6E-6B-33-7E-D1-E5-B1-B9-2E-90-C5-15-37-66-9F-6D-DC-49-D4-D5-D9-A6-3B-F1-04-E5-C2-99-38-64-29-66-FD-D8-0F-33-2A-15-F1-B2-E1-D0-66-96-73-7A-A0-05-CC-09-C3-47-CB-D8-83-33-07-84-77-1B-8C-E7-08-3B-5A-6F-9F-6A-81-91-D1-CE-6F-A0-5E-26-C5-5E-6A-B1-85-4D-87-F4-83-32-DF-F9-8D-3C-7A-CF-13-18-A0-B6-05-57-94-10-C9-D9-01-87-77-D9-48-82-B2-03-70-17-6D-4E-DB-30-5A-3C-AE-F4-0C-F6-5B-DF-7D-D1-15-08-3F-90-7E-1E-62-92-BC-BF-8C-47-DE-63-B9-DF-B3-3E-DD-E2-CE-1E-63-BA-11
         * Clave: C2-04-DF-FE-29-E6-C2-D
         *
         * Decrypted: The DES block cipher is a 16-round Feistel network with a block length of
           64 bits and a key length of 56 bits. The same round function ˆ f is used in each
           of the 16 rounds. The round function takes a 48-bit sub-key and, as expected
           for a (balanced) Feistel network, a 32-bit input (namely, half a block). The
           key schedule of DES is used to derive a sequence of 48-bit sub-keys k1, . . . , k16
           from the 56-bit master key.
         */
        DesCipher(desInput, "ECB");
        
        /*
         * Entrada:
         * Text: The DES block cipher is a 16-round Feistel network with a block length of
           64 bits and a key length of 56 bits. The same round function ˆ f is used in each
           of the 16 rounds. The round function takes a 48-bit sub-key and, as expected
           for a (balanced) Feistel network, a 32-bit input (namely, half a block). The
           key schedule of DES is used to derive a sequence of 48-bit sub-keys k1, . . . , k16
           from the 56-bit master key.
         * Mode: CBC
         *
         * Encrypted: ED-F3-16-80-3C-28-68-B0-09-5A-D9-78-E6-0C-0E-68-87-E0-BC-8F-D2-D6-6F-49-BF-F2-01-C7-2A-D6-7F-FB-DF-E2-05-B7-C2-40-6E-7E-88-B7-99-BC-DC-E0-0E-65-59-73-67-32-41-9C-3D-D4-D4-11-61-EF-0A-29-31-C1-78-E5-42-9E-F5-80-F5-FE-F7-15-9B-92-4A-B9-6F-FA-DB-86-FE-48-A7-A0-40-48-81-41-D8-62-77-D1-13-C1-B8-9D-9B-12-42-75-51-5C-36-6D-C7-42-28-25-B5-8D-BC-FB-91-01-51-26-B2-73-10-43-42-13-C4-EF-39-53-80-D9-EF-DE-DD-FF-54-4B-51-8F-FB-6C-B7-01-AD-95-2D-2D-04-49-E5-4C-BA-FD-0D-EE-B6-7C-E1-5C-B1-1D-02-C6-88-17-E8-A6-19-29-F5-22-5B-F7-81-F3-15-81-BD-40-FC-DF-44-C7-2E-15-F3-AA-7E-6C-4F-C3-11-F7-A3-54-80-EB-8F-A1-EF-9B-B5-9D-22-D6-24-5C-DB-EA-0B-68-24-FC-FF-0C-ED-56-50-AA-86-00-E9-17-5D-6E-5F-B1-74-E2-31-96-D1-8F-6A-7B-45-34-FE-41-F8-F6-16-D6-CF-E7-E0-FE-32-DB-35-B5-2D-2B-5A-F1-B7-B3-8F-4E-6E-87-72-1D-18-75-AD-A1-D8-1D-23-92-30-91-4C-6E-6B-33-7E-D1-E5-B1-B9-2E-90-C5-15-37-66-9F-6D-DC-49-D4-D5-D9-A6-3B-F1-04-E5-C2-99-38-64-29-66-FD-D8-0F-33-2A-15-F1-B2-E1-D0-66-96-73-7A-A0-05-CC-09-C3-47-CB-D8-83-33-07-84-77-1B-8C-E7-08-3B-5A-6F-9F-6A-81-91-D1-CE-6F-A0-5E-26-C5-5E-6A-B1-85-4D-87-F4-83-32-DF-F9-8D-3C-7A-CF-13-18-A0-B6-05-57-94-10-C9-D9-01-87-77-D9-48-82-B2-03-70-17-6D-4E-DB-30-5A-3C-AE-F4-0C-F6-5B-DF-7D-D1-15-08-3F-90-7E-1E-62-92-BC-BF-8C-47-DE-63-B9-DF-B3-3E-DD-E2-CE-1E-63-BA-11
         * Clave: C2-04-DF-FE-29-E6-C2-D
         *
         * Decrypted: The DES block cipher is a 16-round Feistel network with a block length of
           64 bits and a key length of 56 bits. The same round function ˆ f is used in each
           of the 16 rounds. The round function takes a 48-bit sub-key and, as expected
           for a (balanced) Feistel network, a 32-bit input (namely, half a block). The
           key schedule of DES is used to derive a sequence of 48-bit sub-keys k1, . . . , k16
           from the 56-bit master key.
         */
        DesCipher(desInput, "CBC");
        
        /*
         * Entrada:
         * Text: Hola mundo!
         * Mode: ECB
         *
         * Encrypted: C5-42-88-FB-B6-D0-2F-A0-E4-21-3F-5A-C1-14-D9-B8
         * Clave: B5-98-6D-0C-7D-F0-30-1C
         *
         * Decrypted: Hola mundo!
         */
        DesCipher("Hola mundo!", "ECB");
        
        /*
         * Entrada:
         * Text: Hola mundo!
         * Mode: CBC
         *
         * Encrypted: EF-9A-F6-2E-2B-5B-07-46-0C-5A-E9-F6-62-F4-1C-6E-42-21-45-FE-E9-D0-0D-C2
         * Clave: 37-BA-A3-26-0A-C4-A5-0D
         *
         * Decrypted: Hola mundo!
         */
        DesCipher("Hola mundo!", "CBC");
        
        
        
        
        Console.WriteLine("\n\nPrueba con 3DES");
        tripleDesInput = ReadFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/3des.txt");
        
        /*
         * Entrada:
         * Text: The main weakness of DES is its short key. It thus makes sense to try to
           design a block cipher with a larger key length using DES as a building block.
           Some approaches to doing so are discussed in this section. Although we refer
           to DES frequently throughout the discussion, and DES is the most prominent
           block cipher to which these techniques have been applied, everything we say
           here applies generically to any block cipher.
         * Mode: ECB
         *
         * Encrypted: D3-E6-DA-E0-B5-D3-2A-C8-FD-D3-65-48-BC-00-B6-E7-F6-0D-14-E4-8E-ED-F8-B2-B2-46-7F-0C-82-EB-EC-B0-9A-1E-15-D1-29-77-8A-8D-47-9A-C6-E8-7A-5E-88-AA-47-B1-9D-7A-1E-3A-8F-33-48-B6-B6-F3-8C-68-04-A4-B1-91-D6-13-13-B3-63-37-8B-79-A6-1E-BD-20-D5-E4-B8-39-BA-9B-1E-A4-EB-A1-CB-DE-5D-A3-79-62-AB-6C-0B-EB-89-23-EF-D7-5E-9A-AA-64-3F-50-4B-9E-B0-03-60-F5-A4-37-16-A1-8C-FB-0D-09-B4-E0-1A-CC-C9-84-48-2B-8F-97-01-25-C6-E0-D7-7D-47-37-F8-26-48-93-F8-83-E1-25-18-80-7E-4D-E1-00-FD-6D-3C-BB-8F-CC-DE-96-32-28-A7-6D-AA-A7-EA-4E-90-BB-E2-32-D9-8F-F9-E2-BF-32-69-D3-ED-CF-79-2D-C7-39-4D-E0-F9-EC-0E-86-05-AF-76-C0-56-38-65-E9-C5-64-1A-2D-E1-6F-EF-65-C8-A2-F9-29-33-DB-FB-1A-A8-EE-71-4F-ED-72-7D-8B-D1-DE-3C-66-C0-17-52-38-7B-C0-80-4F-6C-2C-F4-23-B7-73-6D-DC-B8-FC-DD-BC-2F-93-F7-98-49-FC-8F-BE-F6-3B-F0-BC-D0-0A-0E-3E-D6-E4-D5-2B-79-79-F6-49-94-38-A2-9D-C9-12-EC-00-A1-8D-B5-E4-CB-72-CD-50-05-36-FA-83-55-FE-27-56-B1-F2-C2-4C-D6-C1-6C-76-CF-31-3B-6D-B6-C5-37-08-05-4C-D2-F9-D1-14-BD-4F-4F-DF-48-10-5D-13-7F-B8-5C-EB-3A-C4-69-EA-33-02-6B-C2-1E-B8-81-7C-90-F0-BE-9A-97-CF-3F-FC-85-CB-39-22-9A-8B-F3-0A-24-4A-48-83-FC-92-65-A3-DE-D1-8A-A8-9A-73-29-67-12-88-C1-91-A1-9A-73-5A-BC-4F-06-72-57-25-5B-16-7F-53-3F-E3-36-BB-72-94-7A-67-D8-39-9B-DB-5F-AF-5C-CD-FE-0C-96-4A-44-A4-39-CE-F4-FE-FA-B6-2D-84
         * Clave: 83-F1-A5-7E-E3-E4-D9-33-9E-A1-A4-83-32-66-D0-57-87-F3-A0-80-A1-88-E3-11
         *
         * Decrypted: The main weakness of DES is its short key. It thus makes sense to try to
           design a block cipher with a larger key length using DES as a building block.
           Some approaches to doing so are discussed in this section. Although we refer
           to DES frequently throughout the discussion, and DES is the most prominent
           block cipher to which these techniques have been applied, everything we say
           here applies generically to any block cipher.
         */
        TripleCipher(tripleDesInput, "ECB");
        
        /*
         * Entrada:
         * Text: The main weakness of DES is its short key. It thus makes sense to try to
           design a block cipher with a larger key length using DES as a building block.
           Some approaches to doing so are discussed in this section. Although we refer
           to DES frequently throughout the discussion, and DES is the most prominent
           block cipher to which these techniques have been applied, everything we say
           here applies generically to any block cipher.
         * Mode: CBC
         *
         * Encrypted: 3B-9A-D2-F6-F0-76-49-F0-FE-94-78-22-A2-0D-EA-B3-A6-10-C6-1F-F0-37-CB-9A-5D-1C-17-5B-CC-49-58-3C-B2-04-64-82-40-8E-F2-B6-42-44-33-99-7B-B3-B7-82-26-45-30-A1-4A-32-5D-11-99-73-67-64-C8-B3-8E-58-6E-6F-36-DE-B0-85-E2-D3-D5-52-B3-C0-B3-AE-9A-88-58-CF-CA-A5-AE-1C-DB-66-7F-92-F4-1B-52-F2-43-47-C7-14-0A-44-5E-D4-50-31-02-72-8D-FD-84-90-73-14-DD-FE-7F-51-B5-99-11-BD-4A-9C-B2-4B-87-DC-CA-CC-75-D8-C3-5C-99-D1-74-F3-5D-FB-65-10-3F-83-25-2B-53-EB-32-B9-95-10-39-A4-EE-69-F6-83-67-52-F3-B7-BF-FD-49-66-1B-0A-A5-44-E3-DF-93-9A-BD-92-F9-37-4B-8D-86-B5-9C-89-9C-E4-EC-60-75-D6-2C-6E-CA-45-65-A0-40-5C-24-E6-2E-9D-7F-22-D7-51-FA-15-6C-1E-D2-EE-26-28-7D-01-1B-67-2A-9C-04-26-D2-A3-6E-A3-EB-D4-A6-8F-22-52-9C-07-3B-AA-C8-23-3F-27-88-B4-24-7D-9E-86-E0-A5-03-F9-CD-FF-66-8C-C2-27-ED-7E-93-B7-60-7E-D1-26-B3-1F-EE-71-D3-0E-B0-A5-77-23-8A-CD-B1-5E-B7-EC-26-B6-1C-18-C2-CD-21-45-5D-DA-C2-F3-B6-8A-EC-82-F9-2F-07-24-A5-A0-04-09-C8-54-AE-1B-3E-C9-3B-A4-68-AD-17-4C-71-D3-71-47-6C-A6-FA-7D-8E-6A-15-82-BB-E7-52-9E-58-06-CE-4C-0A-11-3F-71-73-E0-27-CC-8F-E8-0F-02-8F-D6-FE-58-80-83-9D-BB-79-C5-D4-CC-FD-89-C0-BD-A9-A7-F6-BC-E4-0F-F5-0D-D3-7B-A9-69-4C-DE-24-ED-E8-60-5F-36-35-B4-15-C9-24-F3-0A-CD-B3-AE-7D-D3-36-BF-08-FA-7D-84-84-5D-0C-F3-15-D0-75-57-A9-DD-EA-02-EF-85-1E-E4-83-90-01-E2-60-F9-AE-83-37-8F-A6-39-D0-C4-27-04
         * Clave: EB-CD-DF-A3-31-54-C4-AA-FF-B7-49-63-0B-8B-77-F2-FC-B1-33-B0-CA-E9-4C-F9
         *
         * Decrypted: The main weakness of DES is its short key. It thus makes sense to try to
           design a block cipher with a larger key length using DES as a building block.
           Some approaches to doing so are discussed in this section. Although we refer
           to DES frequently throughout the discussion, and DES is the most prominent
           block cipher to which these techniques have been applied, everything we say
           here applies generically to any block cipher.
         */
        TripleCipher(tripleDesInput, "CBC");
        
        /*
         * Entrada:
         * Text: Hola mundo!
         * Mode: ECB
         *
         * Encrypted: 06-58-8D-DF-09-90-B2-C5-84-F6-5E-44-2B-45-24-C7
         * Clave: 15-2E-34-9E-77-BD-95-2A-0D-31-B8-61-76-1F-35-AA-0D-8F-1A-5D-35-A2-95-CC
         *
         * Decrypted: Hola mundo!
         */
        // TripleCipher("Hola mundo!", "ECB");
        
        /*
         * Entrada:
         * Text: Hola mundo!
         * Mode: CBC
         *
         * Encrypted: A9-2D-4C-50-DF-8A-60-71-CB-62-B1-65-AA-51-53-76-4D-BD-A6-3F-7E-29-02-1E
         * Clave: CC-A6-22-7A-99-09-1D-98-4F-94-B9-3B-D2-7C-58-A1-62-17-42-02-34-0C-AD-77
         *
         * Decrypted: Hola mundo!
         */
        TripleCipher("Hola mundo!", "CBC");
        
        Console.WriteLine("\n\nPrueba con AES");
        byte[] aesImageBytes = ReadBinaryFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Exercises/BlockCipherExercise/resources/aespic.png");
        AesCipher(aesImageBytes, "ECB");
        AesCipher(aesImageBytes, "CBC");
    }    
}
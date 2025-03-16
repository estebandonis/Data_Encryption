using System.Diagnostics;
using System.Security.Cryptography;
using System.Text;
using BlockCipherExercise;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Formats.Png;
using SixLabors.ImageSharp.PixelFormats;

class Program
{
    private static byte[] GenerateRandomKey(string algorithm, int aesKeySize = 256)
    {
        int keySize = algorithm switch
        {
            "DES" => 8,
            "3DES" => 24,
            "AES" => aesKeySize / 8,
            "ChaCha20" => 32,
            _ => 0
        };
        
        byte[] key = new byte[keySize];
        using (var rng = RandomNumberGenerator.Create())
        {
            rng.GetBytes(key);
        }
        return key;
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
    
    private static void Part1()
    {
        string mode, encryptedPathTux, encryptedPathImage;
        
        var key = GenerateRandomKey("AES");
        var imageBytesTux = ReadBinaryFile("./resources/tux.ppm");
        var imageBytesImage = ReadBinaryFile("./resources/logotrust.jpeg");

        mode = "ECB";
        Console.WriteLine($"Encrypting images using {mode} mode");
        encryptedPathTux = $"./resources/encrypted_tux_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesTux, mode, encryptedPathTux, key);
        encryptedPathImage = $"./resources/encrypted_image_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesImage, mode, encryptedPathImage, key);
        
        mode = "CBC";
        Console.WriteLine($"Encrypting images using {mode} mode");
        encryptedPathTux = $"./resources/encrypted_tux_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesTux, mode, encryptedPathTux, key);
        encryptedPathImage = $"./resources/encrypted_image_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesImage, mode, encryptedPathImage, key);
    }

    private static void Part3()
    {
        var inputToEncrypt = "Hello World!";
        var inputBytes = Encoding.UTF8.GetBytes(inputToEncrypt);

        Console.WriteLine("AES 128");
        var aesKey128 = GenerateRandomKey("AES", 128);
        
        var encryptionAes128Stopwatch = Stopwatch.StartNew();
        var encryptedDataAes128 = AesImplementation.Encrypt(inputBytes, aesKey128, "CBC");
        encryptionAes128Stopwatch.Stop();
        
        var decryptionAes128Stopwatch = Stopwatch.StartNew();
        var decryptedDataAes128 = AesImplementation.Decrypt(encryptedDataAes128, aesKey128, "CBC");
        decryptionAes128Stopwatch.Stop();
        Console.WriteLine($"Decrypted data: {Encoding.UTF8.GetString(decryptedDataAes128)}");
        Console.WriteLine($"Time taken Encryption: {encryptionAes128Stopwatch.ElapsedTicks} ticks");
        Console.WriteLine($"Time taken Decryption: {decryptionAes128Stopwatch.ElapsedTicks} ticks");


        Console.WriteLine("\n\nAES 192");
        var aesKey192 = GenerateRandomKey("AES", 192);
        
        var encryptionAes192Stopwatch = Stopwatch.StartNew();
        var encryptedDataAes192 = AesImplementation.Encrypt(inputBytes, aesKey192, "CBC");
        encryptionAes192Stopwatch.Stop();
        
        var decryptionAes192Stopwatch = Stopwatch.StartNew();
        var decryptedDataAes192 = AesImplementation.Decrypt(encryptedDataAes192, aesKey192, "CBC");
        decryptionAes192Stopwatch.Stop();
        Console.WriteLine($"Decrypted data: {Encoding.UTF8.GetString(decryptedDataAes192)}");
        Console.WriteLine($"Time taken Encryption: {encryptionAes192Stopwatch.ElapsedTicks} ticks");
        Console.WriteLine($"Time taken Decryption: {decryptionAes192Stopwatch.ElapsedTicks} ticks");
        

        Console.WriteLine("\n\nAES 256");
        var aesKey256 = GenerateRandomKey("AES", 256);
        
        var encryptionAes256Stopwatch = Stopwatch.StartNew();
        var encryptedDataAes256 = AesImplementation.Encrypt(inputBytes, aesKey256, "CBC");
        encryptionAes256Stopwatch.Stop();
        
        var decryptionAes256Stopwatch = Stopwatch.StartNew();
        var decryptedDataAes256 = AesImplementation.Decrypt(encryptedDataAes256, aesKey256, "CBC");
        decryptionAes256Stopwatch.Stop();
        Console.WriteLine($"Decrypted data: {Encoding.UTF8.GetString(decryptedDataAes256)}");
        Console.WriteLine($"Time taken Encryption: {encryptionAes256Stopwatch.ElapsedTicks} ticks");
        Console.WriteLine($"Time taken Decryption: {decryptionAes256Stopwatch.ElapsedTicks} ticks");
        

        Console.WriteLine("\n\nChaCha20");
        var chachaKey = GenerateRandomKey("ChaCha20");
        
        var encryptionChaCha20Stopwatch = Stopwatch.StartNew();
        var encryptedDataChaCha = ChaCha20Implementation.Encrypt(inputBytes, chachaKey);
        encryptionChaCha20Stopwatch.Stop();
        
        var decryptionChaCha20Stopwatch = Stopwatch.StartNew();
        var decryptedDataChaCha = ChaCha20Implementation.Decrypt(encryptedDataChaCha, chachaKey);
        decryptionChaCha20Stopwatch.Stop();
        Console.WriteLine($"Decrypted data: {Encoding.UTF8.GetString(decryptedDataChaCha)}");
        Console.WriteLine($"Time taken Encryption: {encryptionChaCha20Stopwatch.ElapsedTicks} ticks");
        Console.WriteLine($"Time taken Decryption: {decryptionChaCha20Stopwatch.ElapsedTicks} ticks");

        
        var encryptionTimes = new long[] {encryptionAes128Stopwatch.ElapsedTicks, encryptionAes192Stopwatch.ElapsedTicks, encryptionAes256Stopwatch.ElapsedTicks, encryptionChaCha20Stopwatch.ElapsedTicks};
        var minEncryption= encryptionTimes.Min();
        var minEncryptPosition = Array.IndexOf(encryptionTimes, minEncryption);
        
        var maxEncryption = encryptionTimes.Max();
        var maxEncryptPosition = Array.IndexOf(encryptionTimes, maxEncryption);
        
        var decryptionTimes = new long[] {decryptionAes128Stopwatch.ElapsedTicks, decryptionAes192Stopwatch.ElapsedTicks, decryptionAes256Stopwatch.ElapsedTicks, decryptionChaCha20Stopwatch.ElapsedTicks};
        
        var minDecryption = decryptionTimes.Min();
        var minDecryptPosition = Array.IndexOf(decryptionTimes, minDecryption);
        
        var maxDecryption = decryptionTimes.Max();
        var maxDecryptPosition = Array.IndexOf(decryptionTimes, maxDecryption);
        
        string[] algorithmNames = { "AES-128", "AES-192", "AES-256", "ChaCha20" };
        
        Console.WriteLine($"\n\nFastest Encrypt: {algorithmNames[minEncryptPosition]} - {minEncryption}ticks");
        Console.WriteLine($"Slowest Encrypt: {algorithmNames[maxEncryptPosition]} - {maxEncryption}ticks");
        
        for (int i = 0; i < encryptionTimes.Length; i++)
        {
            Console.WriteLine($"{algorithmNames[i]} - {encryptionTimes[i]} ticks");
        }
        
        Console.WriteLine($"\nFastest Decrypt: {algorithmNames[minDecryptPosition]} - {minDecryption}ticks");
        Console.WriteLine($"Slowest Decrypt: {algorithmNames[maxDecryptPosition]} - {maxDecryption}ticks");
        
        for (int i = 0; i < decryptionTimes.Length; i++)
        {
            Console.WriteLine($"{algorithmNames[i]} - {decryptionTimes[i]} ticks");
        }
        
        /*
         * Resultados de test de velocidad:
         *
         * Fastest Encrypt: AES-128 - 385,667 ticks
         * Slowest Encrypt: ChaCha20 - 559,208 ticks
         * AES-128 - 385,667 ticks
         * AES-192 - 435,458 ticks
         * AES-256 - 468,542 ticks
         * ChaCha20 - 559,208 ticks
           
         * Fastest Decrypt: ChaCha20 - 101,917 ticks
         * Slowest Decrypt: AES-256 - 7,593,708 ticks
         * AES-128 - 283,292 ticks
         * AES-192 - 2,356,334 ticks
         * AES-256 - 7,593,708 ticks
         * ChaCha20 - 101,917 ticks
         *
         * DISCLAIMER: Los tiempos se tomaron ejecutando cada algoritmo uno por uno y buscando la media de los tiempos de ejecución,
         * ya que si se ejecutan todos los algoritmos como se realiza en la función, se puede ver que el primero siempre es el más lento,
         * mientras que los demás terminan siendo mucho más rápidos. Esto se debe a las optimizaciones que realiza el compilador de C#.
         */
    }

    private static void Part4(string folderPath)
    {
        string[] allFiles = Directory.GetFiles(folderPath, "*", SearchOption.AllDirectories);
        foreach (var file in allFiles)
        {
            Console.WriteLine($"File: {file}");
        }
        
        var key = GenerateRandomKey("AES");
        var mode = "CBC";

        foreach (var file in allFiles)
        {
            var fileBytes = ReadBinaryFile(file);
            File.Delete(file);
            var encrypt = AesImplementation.Encrypt(fileBytes, key, mode);
            
            var encryptedFilePath = $"{file}.encrypted";
            File.WriteAllBytes(encryptedFilePath, encrypt);
        }
        
        File.WriteAllBytes($"{folderPath}/encryptionKey.key", key);
    }

    private static void Main(string[] args)
    {
        // Part1();
        // Part3();
        Part4("./folderExampleEncrypted");
    }
}
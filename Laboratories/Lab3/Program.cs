using System.Security.Cryptography;
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
        var imageBytesTux = ReadBinaryFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Informacio\u0301n/Data_Encryption/Laboratories/Lab3/resources/tux.ppm");
        var imageBytesImage = ReadBinaryFile("/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Información/Data_Encryption/Laboratories/Lab3/resources/logotrust.jpeg");

        mode = "ECB";
        Console.WriteLine($"Encrypting images using {mode} mode");
        encryptedPathTux = $"/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Información/Data_Encryption/Laboratories/Lab3/resources/encrypted_tux_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesTux, mode, encryptedPathTux, key);
        encryptedPathImage = $"/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Información/Data_Encryption/Laboratories/Lab3/resources/encrypted_image_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesImage, mode, encryptedPathImage, key);
        
        mode = "CBC";
        Console.WriteLine($"Encrypting images using {mode} mode");
        encryptedPathTux = $"/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Información/Data_Encryption/Laboratories/Lab3/resources/encrypted_tux_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesTux, mode, encryptedPathTux, key);
        encryptedPathImage = $"/Users/estebandonis/Documents/Noveno Semestre/Cifrado de Información/Data_Encryption/Laboratories/Lab3/resources/encrypted_image_{mode}.png";
        EncryptImageAndSaveAsPng(imageBytesImage, mode, encryptedPathImage, key);
        
        /*
         * Preguntas:
         * 1. ¿Por qué el cifrado ECB revela los patrones de la imagen?
         * El cifrado ECB revela los patrones de la imagen porque como se cifra bloque por bloque independientemente de los demás, esto genera que el cifrado no sea
         * aleatorio y se puedan distinguir algunos patrones de la imagen original. Esto crea una falta de difusión en la imagen, al presentar encriptaciones similares
         * entre patrones de imágenes de una área similar.
         * 
         * 2. ¿Cómo cambia la apariencia con CBC?
         * Se puede ver que los píxeles son totalmente aleatorios, por lo que no se puede distinguir ninguna clase de patrón de la imagen digital.
         * Esto se debe a la forma en la que se realiza CBC, la cual es más segura que ECB, cifrando el bloque siguiente con el bloque anterior, asegurando un
         * cifrado mucho más seguro.
         * 
         * 3. ¿Qué tan seguro es usar ECB para cifrar datos estructurados?
         * Por su falta de difusión y ruido dentro de los patrones, ECB no es seguro para cifrar datos estructurados, siendo proceso a repetir cifrados de bloques iguales.
         * Lo que lo hace vulnerable a ataques de análisis de frecuencia y otros tipos de ataques.
         */
    }
    
    private static void Main(string[] args)
    {
        Part1();
    }
}
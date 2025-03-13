using System.Security.Cryptography;
using System.Text;

namespace BlockCipherExercise;

public class TresDesImplementation
{
    public static byte[] Encrypt(string inputString, byte[] key, string mode)
    {
        var data = Encoding.UTF8.GetBytes(inputString);

        using (SymmetricAlgorithm tripledes = TripleDES.Create())
        {
            tripledes.Key = key;
            tripledes.Mode = mode switch
            {
                "CBC" => CipherMode.CBC,
                "ECB" => CipherMode.ECB,
                _ => CipherMode.ECB
            };
            if (mode == "CBC")
            {
                tripledes.GenerateIV();
            }
            tripledes.Padding = PaddingMode.Zeros;
            var encryptor = tripledes.CreateEncryptor();
            byte[] encryptedData = encryptor.TransformFinalBlock(data, 0, data.Length);
            
            if (mode == "CBC")
            {
                byte[] result = new byte[tripledes.IV.Length + encryptedData.Length];
                Array.Copy(tripledes.IV, 0, result, 0, tripledes.IV.Length);
                Array.Copy(encryptedData, 0, result, tripledes.IV.Length, encryptedData.Length);
                return result;
            }
        
            return encryptedData;
        }
    }
    
    public static byte[] Decrypt(byte[] data, byte[] key, string mode)
    {
        using (SymmetricAlgorithm tripledes = TripleDES.Create())
        {
            tripledes.Key = key;
            tripledes.Mode = mode switch
            {
                "CBC" => CipherMode.CBC,
                "ECB" => CipherMode.ECB,
                _ => CipherMode.ECB
            };

            byte[] dataToDecrypt = data;
            if (mode == "CBC")
            {
                byte[] ivArray = new byte[tripledes.BlockSize / 8];
                Array.Copy(data, 0, ivArray, 0, ivArray.Length);
                tripledes.IV = ivArray;

                dataToDecrypt = new byte[data.Length - tripledes.IV.Length];
                Array.Copy(data, ivArray.Length, dataToDecrypt, 0, dataToDecrypt.Length);
            }
            
            tripledes.Padding = PaddingMode.Zeros;
            var decryptor = tripledes.CreateDecryptor();
            return decryptor.TransformFinalBlock(dataToDecrypt, 0, dataToDecrypt.Length);
        }
    }
}
using System.Text;

namespace BlockCipherExercise;
using System.Security.Cryptography;

public class AesImplementation
{
    public static byte[] Encrypt(string inputString, byte[] key, string mode)
    {
        var data = Encoding.UTF8.GetBytes(inputString);

        using (SymmetricAlgorithm aes = Aes.Create())
        {
            aes.Key = key;
            aes.Mode = mode switch
            {
                "CBC" => CipherMode.CBC,
                "ECB" => CipherMode.ECB,
                _ => CipherMode.ECB
            };
            if (mode == "CBC")
            {
                aes.GenerateIV();
            }
            
            aes.Padding = PaddingMode.Zeros;
            var encryptor = aes.CreateEncryptor();
            byte[] encryptedData = encryptor.TransformFinalBlock(data, 0, data.Length);
            
            if (mode == "CBC")
            {
                byte[] result = new byte[aes.IV.Length + encryptedData.Length];
                Array.Copy(aes.IV, 0, result, 0, aes.IV.Length);
                Array.Copy(encryptedData, 0, result, aes.IV.Length, encryptedData.Length);
                return result;
            }
            
            return encryptedData;
        }
    }
    
    public static byte[] Decrypt(byte[] data, byte[] keyData, string mode)
    {
        using (SymmetricAlgorithm aes = Aes.Create())
        {
            aes.Key = keyData;
            aes.Mode = mode switch
            {
                "CBC" => CipherMode.CBC,
                "ECB" => CipherMode.ECB,
                _ => CipherMode.ECB
            };
            
            byte[] dataToDecrypt = data;
            if (mode == "CBC")
            {
                byte[] ivArray = new byte[aes.BlockSize / 8];
                Array.Copy(data, 0, ivArray, 0, ivArray.Length);
                aes.IV = ivArray;

                dataToDecrypt = new byte[data.Length - aes.IV.Length];
                Array.Copy(data, ivArray.Length, dataToDecrypt, 0, dataToDecrypt.Length);
            }
            
            aes.Padding = PaddingMode.Zeros;
            var decryptor = aes.CreateDecryptor();
            return decryptor.TransformFinalBlock(dataToDecrypt, 0, dataToDecrypt.Length);
        }
    }
}
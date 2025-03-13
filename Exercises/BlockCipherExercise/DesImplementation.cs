using System.Text;

namespace BlockCipherExercise;
using System.Security.Cryptography;

public class DesImplementation
{
    public static byte[] Encrypt(string inputString, byte[] key, string mode)
    {
        var data = Encoding.UTF8.GetBytes(inputString);

        using (SymmetricAlgorithm des = DES.Create())
        {
            des.Key = key;
            des.Mode = mode switch
            {
                "CBC" => CipherMode.CBC,
                "ECB" => CipherMode.ECB,
                _ => CipherMode.ECB
            };
            if (mode == "CBC")
            {
                des.GenerateIV();
            }
            var encryptor = des.CreateEncryptor();
            byte[] encryptedData = encryptor.TransformFinalBlock(data, 0, data.Length);
            
            if (mode == "CBC")
            {
                byte[] result = new byte[des.IV.Length + encryptedData.Length];
                Array.Copy(des.IV, 0, result, 0, des.IV.Length);
                Array.Copy(encryptedData, 0, result, des.IV.Length, encryptedData.Length);
                return result;
            }
            
            return encryptedData;
        }
    }
    
    public static byte[] Decrypt(byte[] data, byte[] keyData, string mode)
    {
        using (SymmetricAlgorithm des = DES.Create())
        {
            des.Key = keyData;
            des.Mode = mode switch
            {
                "CBC" => CipherMode.CBC,
                "ECB" => CipherMode.ECB,
                _ => CipherMode.ECB
            };
            
            byte[] dataToDecrypt = data;
            if (mode == "CBC")
            {
                byte[] ivArray = new byte[des.BlockSize / 8];
                Array.Copy(data, 0, ivArray, 0, ivArray.Length);
                des.IV = ivArray;

                dataToDecrypt = new byte[data.Length - des.IV.Length];
                Array.Copy(data, ivArray.Length, dataToDecrypt, 0, dataToDecrypt.Length);
            }
            
            var decryptor = des.CreateDecryptor();
            return decryptor.TransformFinalBlock(dataToDecrypt, 0, dataToDecrypt.Length);
        }
    }
}
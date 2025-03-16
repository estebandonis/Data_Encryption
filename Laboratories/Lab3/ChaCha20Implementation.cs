namespace BlockCipherExercise;

using System.Security.Cryptography;

public class ChaCha20Implementation
{
    public static byte[] Encrypt(byte[] data, byte[] key)
    {
        var nonce = new byte[12];
        RandomNumberGenerator.Fill(nonce);
        
        var tag = new byte[16];
        RandomNumberGenerator.Fill(tag);
        
        using (var chacha20 = new ChaCha20Poly1305(key))
        {
            byte[] cipherText = new byte[data.Length];
            
            chacha20.Encrypt(nonce, data, cipherText, tag);
            
            byte[] encryptedText = new byte[nonce.Length + tag.Length + cipherText.Length];
            
            Array.Copy(nonce, 0, encryptedText, 0, nonce.Length);
            Array.Copy(tag, 0, encryptedText, nonce.Length, tag.Length);
            Array.Copy(cipherText, 0, encryptedText, nonce.Length + tag.Length, cipherText.Length);
            
            return encryptedText;
        }
    }
    
    public static byte[] Decrypt(byte[] data, byte[] keyData)
    {
        using (var chacha20 = new ChaCha20Poly1305(keyData))
        {
            byte[] nonce = new byte[12];
            Array.Copy(data, 0, nonce, 0, nonce.Length);

            byte[] tag = new byte[16];
            Array.Copy(data, nonce.Length, tag, 0, tag.Length);

            byte[] cipherText = new byte[data.Length - nonce.Length - tag.Length];
            Array.Copy(data, nonce.Length + tag.Length, cipherText, 0, data.Length - nonce.Length - tag.Length);
            
            byte[] decryptedText = new byte[data.Length - nonce.Length - tag.Length];
            
            chacha20.Decrypt(nonce, cipherText, tag, decryptedText);
            
            return decryptedText;
        }
    }
}
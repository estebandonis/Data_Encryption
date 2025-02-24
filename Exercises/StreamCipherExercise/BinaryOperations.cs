namespace StreamCipherExercise;

public class BinaryOperations
{
    public static string TextToBinary(string textString)
    {
        var result = "";
        
        foreach (var c in textString)
        {
            result += Convert.ToString(c, 2).PadLeft(8, '0');
        }

        return result;
    }
    
    public static string XorOperation(string textString, string keyString)
    {
        if (textString.Length != keyString.Length)
        {
            throw new ArgumentException("The length of the text and key must be the same.");
        }
        
        var result = "";
        
        for (var i = 0; i < textString.Length; i++)
        {
            result += textString[i] ^ keyString[i];
        }

        return result;
    }
}
namespace StreamCipherExercise;

public class BinaryOperations
{
    public static string TextToBinary(string textString)
    {
        return string.Concat(textString.Select(cha => Convert.ToString(cha, 2).PadLeft(8, '0')));
    }
    
    public static string NumbersToBinary(string textString)
    {
        return string.Concat(textString.Select(cha => NumberToBinary(long.Parse(cha.ToString()))));
    }
    
    private static string NumberToBinary(long number)
    {
        var binaryString = "";

        while (number > 0) {
            binaryString += (number % 2);
            number /= 2;
        }

        if (binaryString.Length < 4)
        {
            binaryString = binaryString.PadLeft(4, '0');
        }
        
        return binaryString;
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
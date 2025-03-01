namespace StreamCipherExercise;

public class StreamCipher
{
    private const long a = 1664525;
    private const long c = 1013904223;
    private const long m = 4294967296;
    
    private long generateRandomSeed()
    {
        var processId = Environment.ProcessId;
        var ticks = DateTime.Now.Millisecond;
        var randomSeedGenerate = ((processId * ticks) + ticks) % m;
        return randomSeedGenerate;
    }
    
    private long NextNumber(long seed)
    {
        seed = (a * seed + c) % m;
        return seed;
    }

    public string PRNG(int length)
    {
        var seed = generateRandomSeed();
        var result = "";

        for (var i = 0; i < length; i++)
        {
            seed = NextNumber(seed);
            result += ((seed % 9) + 1).ToString();
        }
        
        return result;
    }
}
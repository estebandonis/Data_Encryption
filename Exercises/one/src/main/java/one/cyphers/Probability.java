package one.cyphers;



import java.util.*;

public class Probability {

    private static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    private static Map<Character, Float> setFrequencyMap(String text) {
        Map<Character, Float> frequencyMap = new HashMap<>();

        for (int i = 0; i < ALPHABET.length(); i++) {
            char character = ALPHABET.charAt(i);
            frequencyMap.put(character, 0.0F);
        }

        return frequencyMap;
    }

    private static Map<Character, Float> sortFrequencies(Map<Character, Float> frequencyMap) {
        List<Map.Entry<Character, Float>> list = new ArrayList<>(frequencyMap.entrySet());
        list.sort(Map.Entry.<Character, Float>comparingByValue().reversed());
        Map<Character, Float> sortedFrequencyMap = new LinkedHashMap<>();
        for (Map.Entry<Character, Float> entry : list) {
            sortedFrequencyMap.put(entry.getKey(), entry.getValue());
        }

        return sortedFrequencyMap;
    }

    public static Map<Character, Float> getFrequencyMap(String text) {
        Map<Character, Float> frequencyMap = setFrequencyMap(text);

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (ALPHABET.contains(String.valueOf(character))) {
                frequencyMap.put(character, frequencyMap.get(character) + (float) 1/text.length());
            }
        }

        return sortFrequencies(frequencyMap);
    }

    public static Map<Character, Float> getAlphabetFrequencyMap() {
        Map<Character, Float> frequencyMap = new HashMap<>();

        frequencyMap.put('a', 12.53F);
        frequencyMap.put('b', 1.42F);
        frequencyMap.put('c', 4.68F);
        frequencyMap.put('d', 5.86F);
        frequencyMap.put('e', 13.68F);
        frequencyMap.put('f', 0.69F);
        frequencyMap.put('g', 1.01F);
        frequencyMap.put('h', 0.70F);
        frequencyMap.put('i', 6.25F);
        frequencyMap.put('j', 0.44F);
        frequencyMap.put('k', 0.02F);
        frequencyMap.put('l', 4.97F);
        frequencyMap.put('m', 3.15F);
        frequencyMap.put('n', 6.71F);
        frequencyMap.put('ñ', 0.31F);
        frequencyMap.put('o', 8.68F);
        frequencyMap.put('p', 2.51F);
        frequencyMap.put('q', 0.88F);
        frequencyMap.put('r', 6.87F);
        frequencyMap.put('s', 7.98F);
        frequencyMap.put('t', 4.63F);
        frequencyMap.put('u', 3.93F);
        frequencyMap.put('v', 0.90F);
        frequencyMap.put('w', 0.01F);
        frequencyMap.put('x', 0.22F);
        frequencyMap.put('y', 0.90F);
        frequencyMap.put('z', 0.52F);

        return sortFrequencies(frequencyMap);
    }
}

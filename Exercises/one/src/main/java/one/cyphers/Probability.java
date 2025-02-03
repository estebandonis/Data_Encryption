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

    public static Map<Character, Float> getFrequencyMap(String text) {
        Map<Character, Float> frequencyMap = setFrequencyMap(text);

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (ALPHABET.contains(String.valueOf(character))) {
                frequencyMap.put(character, frequencyMap.get(character) + (float) 1/text.length());
            }
        }

        List<Map.Entry<Character, Float>> list = new ArrayList<>(frequencyMap.entrySet());
        list.sort(Map.Entry.<Character, Float>comparingByValue().reversed());
        Map<Character, Float> sortedFrequencyMap = new LinkedHashMap<>();
        for (Map.Entry<Character, Float> entry : list) {
            sortedFrequencyMap.put(entry.getKey(), entry.getValue());
        }

        return sortedFrequencyMap;
    }

    public static void graphFrequencyMap(Map<Character, Float> frequencyMap) {
        for (Map.Entry<Character, Float> entry : frequencyMap.entrySet()) {
            System.out.println("Character: " + entry.getKey() + " Frequency: " + entry.getValue());
        }
    }
}

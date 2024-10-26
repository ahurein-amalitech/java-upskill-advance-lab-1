package big_data.ex_1_simulate_mapreduce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String text = "This is a sample text to count words. Here are some more words to test.";

        List<String> lines = Arrays.asList(text.split("\n"));
        Map<String, Integer> combineMapResults = lines.stream().map(Main::mapLine).reduce(new HashMap<>(), Main::reduceCounts);

        System.out.println("Word count results");
        combineMapResults.forEach((word, count) -> System.out.println(word + " " + count));
    }

    public static Map<String, Integer> reduceCounts(Map<String, Integer> map1, Map<String, Integer> map2) {
        Map<String, Integer> combinedCounts = new HashMap<>(map1);
        for(Map.Entry<String, Integer> entry : map2.entrySet()) {
            combinedCounts.put(entry.getKey(), combinedCounts.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
        return combinedCounts;
    }

    public static Map<String, Integer> mapLine(String line){
        Map<String, Integer> wordCount = new HashMap<>();
        for(String word : line.split("\\s+")) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        return wordCount;
    }
}

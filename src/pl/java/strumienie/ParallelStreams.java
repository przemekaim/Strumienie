package pl.java.strumienie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class ParallelStreams {
    public static void main(String[] args) throws IOException {
        var contents = Files.readString(Paths.get("alice30.txt"));
        List<String> wordList = List.of(contents.split("\\PL+"));

        // Przyklad bardzo zlego kodu
        var shortWords = new int[10];
        wordList.stream().parallel().forEach(s -> {
            if (s.length() < 10)
                shortWords[s.length()]++;
        });
        for (int shortWord : shortWords) {
            System.out.println(shortWord);
        }

        // KOlejny zly kod
        Arrays.fill(shortWords, 0);
        wordList.stream().parallel().forEach(s -> {
            if (s.length() < 10)
                shortWords[s.length()]++;
        });

        //Rozwiazanie problemu:
        Map<Integer, Long> shortWordCounts = wordList.stream().parallel()
                .filter(s -> s.length() < 10)
                .collect(groupingBy(String::length, counting()));

        System.out.println(shortWordCounts);

        //Kolejnosc nie jest deterministyczna
        Map<Integer, List<String>> result = wordList.stream().parallel()
                .collect(groupingByConcurrent(String::length));
        System.out.println(result.get(14));

        result = wordList.stream().parallel()
                .collect(groupingByConcurrent(String::length));
        System.out.println(result.get(14));

        Map<Integer, Long> wordCounts = wordList.stream().parallel()//.unordered()
                .collect(groupingByConcurrent(String::length, counting()));

        System.out.println(wordCounts);
    }
}

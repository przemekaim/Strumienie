package pl.java.strumienie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectingResuts {
    public static Stream<String> noVowels() throws IOException {
        String contents = Files.readString(Paths.get("alice30.txt"));
        List<String> wordList = List.of(contents.split("\\PL+"));
        //List<String> firstTen = wordList.stream().skip(1).limit(10).map(s -> s.replaceAll("[aeiouAEIOU]", "")).collect(Collectors.toList());
        //firstTen.forEach(el -> System.out.print(el + " "));
        Stream<String> words = wordList.stream();
        return words.map(s -> s.replaceAll("[aeiouAEIOU]", ""));
    }

    public static <T> void show(String label, Set<T> set) {
        System.out.println(label + ": " + set.getClass().getName());
        System.out.println("[" + set.stream().skip(1).limit(10).map(Objects::toString).collect(Collectors.joining(", ")) + "]");
    }

    public static void main(String[] args) throws IOException {
        Iterator<Integer> iter = Stream.iterate(0, n -> n + 1).limit(10).iterator();
        while (iter.hasNext())
            System.out.println(iter.next());

        Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10).toArray();
        System.out.println("Tablica obiektow:" + numbers);

        try {
            var number = (Integer) numbers[0];
            System.out.println("liczba: " + number);
            System.out.println("Kolejna instrukcja wyrzuci wyjatek:");
            //var number2 = (Integer[]) numbers;
        } catch (ClassCastException ex) {
            System.out.println(ex);
        }

        Integer[] numbers3 = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .toArray(Integer[]::new);

        Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
        show("noVowelSet", noVowelSet);

        TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet", noVowelTreeSet);

        String result = noVowels().skip(1).limit(10).collect(Collectors.joining(", "));
        System.out.println("Konkatenacja z uzyciem przecinkow: " + result);

        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Srednia dlugosc slow: " + averageWordLength);
        System.out.println("Maks. dlugosc slowa: " + maxWordLength);
        System.out.println("Statystyki: " + summary);
        System.out.println("Metoda forEach:");
        noVowels().skip(1).limit(10).forEach(System.out::println);
        System.out.println("-----------------------------");

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
//        Map<String, String> languageNames = locales.collect(
//                Collectors.toMap(Locale::getDisplayLanguage,
//                        loc -> loc.getDisplayLanguage(loc),
//                        (existingValue, newValue) -> existingValue));
        //,TreeMap::new));
        //languageNames.forEach((k, v) -> System.out.println(k +  " : " + v));


        // Trudniejszy sposob
        Map<String, Set<String>> countryLanguageSets = locales.collect(
                Collectors.toMap(
                        Locale::getDisplayCountry,
                        l -> Collections.singleton(l.getDisplayLanguage()),
                        (a, b) -> {
                            var union = new HashSet<>(a);
                            union.addAll(b);
                            return union;
                        }));
        countryLanguageSets.forEach((k, v) -> System.out.println(k + " " + v));
        //System.out.println(countryLanguageSets);
    }
}

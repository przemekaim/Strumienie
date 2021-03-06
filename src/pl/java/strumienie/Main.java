package pl.java.strumienie;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        //var contents = Files.readString(Paths.get("alice30.txt"));
        //List<String> words = List.of(contents.split("\\PL+")); //separator: wszystkie znaki inne niz litery
        //int result = words.stream().reduce(0, (total, word) -> total + word.length(), Integer::sum);
        //System.out.println(result);
//        Instant start = Instant.now();
//        long count1 = words.stream()
//                .filter(w -> w.length() > 12)
//                .sorted(String::compareToIgnoreCase)
//                .map(String::toUpperCase)
//                .peek(System.out::println)
//                .count();
//        Instant end = Instant.now();
//        System.out.println(Duration.between(start, end).toMillis());
//
//        start = Instant.now();
//        long count2 = words.parallelStream()
//                .filter(w -> w.length() > 12)
//                .count();
//        end = Instant.now();
//        System.out.println(Duration.between(start, end).toMillis());
//
//        start = Instant.now();
//        long count3 = 0;
//        for (String word : words) {
//            if (word.length() > 12)
//                count3++;
//        }
//        end = Instant.now();
//        System.out.println(Duration.between(start, end).toMillis());
//        System.out.println(count1 + " " + count2 + " " + count3);

//        start = Instant.now();
//        words.forEach(System.out::println);
//        end = Instant.now();
//        System.out.println(Duration.between(start, end).toMillis());


        //----------------------------------------------------------------------------
        // 1.2 Tworzenie strumieni, tablice
        var contents = Files.readString(Paths.get("alice30.txt"));
        Stream<String> words = Stream.of(contents.split("\\PL+"));
        //int sum = words.mapToInt(String::length).sum();
        //System.out.println(sum);
//        String collect = words.filter(w -> w.length() > 12)
//                .sorted(String::compareToIgnoreCase)
//                .collect(Collectors.joining(","));

//      IntSummaryStatistics collect1 = words.collect(Collectors.summarizingInt(String::length));
        //System.out.println(collect);
        String[] tab = {"a", "b", "c", "e"};
        Stream<String> song =
                Arrays.stream(tab, 0, 3)
                        .filter(w -> w.length() == 1)
                        .map(String::toUpperCase);

        song.forEach(System.out::println);

/*        Stream<Double> echos = Stream.generate(Math::random)
        echos.forEach(System.out::println);*/

        var limit = new BigInteger("100");
        Stream<BigInteger> integers
                = Stream.iterate(BigInteger.ZERO,
                n -> n.compareTo(limit) < 0,
                n -> n.add(BigInteger.ONE));
        integers.forEach(System.out::println);
//        String a = "a";
//        String b = "a";
//        String c = "a";
//        String d = "a";
//        Stream<String>aa = Stream.of(a,b,c,d, contents);
    }
}

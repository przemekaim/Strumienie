package pl.java.strumienie;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CreatingStreams {
    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream
                .limit(SIZE + 1)
                .collect(Collectors.toList());

        System.out.print(title + ": ");
        for (int i = 0; i < firstElements.size(); i++) {
            if (i > 0) System.out.print(", ");
            if (i < SIZE) System.out.print(firstElements.get(i));
            else System.out.print("...");
        }
        System.out.println();
    }

    public static Stream<String> codePoints(String s) {
        var result = new ArrayList<String>();
        int i = 0;

        while (i < s.length()) {
            int j = s.offsetByCodePoints(i, 1);
            result.add(s.substring(i, j));
            i = j;
        }
        //result.forEach(System.out::println);
        return result.stream();
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("alice30.txt");
        var contents = Files.readString(path);

//        Stream<String> words = Stream.of(contents.split("\\PL+")).skip(1);
//        show("words", words);
//        Stream<String> song = Stream.of("lagodnie", "w", "dol", "strumienia");
//        show("song", song);
//        Stream<String> silence = Stream.empty();
//        show("silence", silence);
//
//        Stream<String> echos = Stream.generate(() -> "Echo");
//        show("echos", echos);
//
//        Stream<Double> randoms = Stream.generate(Math::random);
//        show("randoms", randoms);
//
//        Stream<BigInteger> integers = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
//        show("integers", integers);
//
//        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
//        show("wordsAnotherWay", wordsAnotherWay);
//
//        Stream<String> scannerWay = new Scanner(contents).tokens();
//        show("scannerWay", scannerWay);
//
//        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
//            show("lines", lines);
//        }
//
//        Iterable<Path> iterable = FileSystems.getDefault().getRootDirectories();
//        Stream<Path> rootDirectories = StreamSupport.stream(iterable.spliterator(), false);
//        show("rootDirectories", rootDirectories);
//
//        Iterator<Path> iterator = Paths.get("/sr/share/dict/words").iterator();
//        Stream<Path> pathComponents = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
//        show("pathComponents", pathComponents);


        // DropWhile, takeWhile etc
        List<String> words = List.of(contents.split("\\PL+"));
        Stream<String> letters = words.stream()
                .filter(w -> w.length() > 12)
                .limit(3)
                .map(w -> w + " ")
                .flatMap(CreatingStreams::codePoints);
        //letters.forEach(System.out::println);

        String str = "312 3 4 5 Siema 1 2, olek";
        Stream<String> initialDigits = codePoints(str).takeWhile(s -> "0123456789".contains(s));

        Stream<String> digitsInText = words.stream()
                .limit(40)
                //.flatMap(CreatingStreams::codePoints)
                .dropWhile(s -> "0123456789".contains(s));

        Stream<String> combined = Stream.concat(codePoints("Przemek"), codePoints("Nosek"));

        //combined.forEach(System.out::println);

        //Distinct
        System.out.println(words.size());
        Stream<String> uniqueWords = words.stream().distinct();
        List<String> unique = uniqueWords.collect(Collectors.toList());
        System.out.println(unique.size());

        Stream<String> uq = Stream.of("siema", "elo", "siema", "elo", "tutaj", "ja", "nazywam", "sie", "a", "on", "nazywa", "sie", "przemek").distinct();
//        uq.forEach(System.out::println);

        Object[] powers = Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("Pobieram element: " + e))
                .peek(x -> {
                    if (x == 8.0)
                        System.out.println("Dziala git");
                })
                .limit(20).toArray();

        // operacje konczace terminal operations
        String[] strings = {"sadsadas","1wieczorynka", "3245", "1waj", "przemek", "dsgfjkgdsg", "1wierzcholek", "1wieczor", "x", "1wal"};
        Optional<String> max = Arrays.stream(strings).min(String::compareToIgnoreCase);
        System.out.println("maksimum: " + max.orElse(""));

        Optional<String> startsWithW = Arrays.stream(strings).parallel().filter(s -> s.startsWith("w")).findAny();
        System.out.println("slowo na W: " + startsWithW.orElse(""));

        //String result = startsWithW.orElseThrow(IllegalStateException::new);
        //System.out.println(result);

        List<String> results = new ArrayList<>();

        Optional<String> sta = Arrays.stream(strings).filter(w -> w.startsWith("s")).findFirst();
        sta.ifPresent(results::add);
        Optional<String> s = sta.map(w -> results.get(0));
        System.out.println(s.orElse("Brak"));
        s.map(String::toUpperCase).map(results::add);
        results.forEach(System.out::println);
    }
}

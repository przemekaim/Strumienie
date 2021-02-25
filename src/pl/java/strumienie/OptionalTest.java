package pl.java.strumienie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OptionalTest {
    public static void main(String[] args) throws IOException {
        var contents = Files.readString(Paths.get("alice30.txt"));
        List<String> wordList = List.of(contents.split("\\PL+"));

        Optional<String> optionalValue = wordList.stream()
                .filter(s -> s.contains("fred"))
                .findFirst();
        System.out.println(optionalValue.orElse("Brak slow") + " zawierajacych 'fred'");

        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("Brak");
        System.out.println("Wynik: " + result);
        result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("Wynik: " + result);
        try {
            result = optionalString.orElseThrow(IllegalStateException::new);
            System.out.println("Wynik: " + result);
        } catch (Throwable t) {
            //t.printStackTrace();
        }

        optionalValue = wordList.stream()
                .filter(s -> s.contains("red"))
                .findFirst();
        optionalValue.ifPresent(s -> System.out.println(s + " zawiera 'red'"));

        var results = new HashSet<String>();
        optionalValue.ifPresent(results::add);
        Optional<Boolean> added = optionalValue.map(results::contains);
        System.out.println(added);

        Iterator<String> iterator = results.iterator();
        String next = iterator.next();
        System.out.println(next);

        System.out.println(Mathematics.inverse(4.0).flatMap(Mathematics::squareRoot));
        System.out.println(Mathematics.inverse(-1.0).flatMap(Mathematics::squareRoot));
        System.out.println(Mathematics.inverse(0.0).flatMap(Mathematics::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0)
                .flatMap(Mathematics::inverse).flatMap(Mathematics::squareRoot);
        System.out.println(result2);
    }
}

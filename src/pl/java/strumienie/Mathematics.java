package pl.java.strumienie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mathematics {
    public static void main(String[] args) {
        double number = 0.25;
        Optional<Double> result = inverse(number).flatMap(Mathematics::squareRoot);
        //result.ifPresentOrElse(System.out::println, () -> System.out.println("Liczba byla ujemna/zerowa"));

        List<Double> results = new ArrayList<>(75);

        for (int i = 0; i < 100; i++) {
            double rand = Math.random() - 0.5;
            result = inverse(rand).flatMap(Mathematics::squareRoot);
            //result.ifPresentOrElse(System.out::println, () -> System.out.println("Liczba byla ujemna/zerowa"));
            result.map(results::add);
        }

        List<Double> sortedResults = results.stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < sortedResults.size(); i++) {
            System.out.println(i+".Element: " + sortedResults.get(i));
        }
        Optional<Double> r = Optional.of(number).flatMap(Mathematics::inverse).flatMap(Mathematics::squareRoot);
        r.ifPresent(System.out::println);
    }

    public static Optional<Double> inverse(double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}

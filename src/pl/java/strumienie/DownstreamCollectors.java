package pl.java.strumienie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DownstreamCollectors {
    public static class City {
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population) {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public int getPopulation() {
            return population;
        }

        public static Stream<City> readCities(String filemane) throws IOException {
            return Files.lines(Paths.get(filemane))
                    .map(l -> l.split(", "))
                    .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
        }

        public static void main(String[] args) throws IOException {
//            List<City> collect = readCities("cities.txt").sorted(Comparator.comparing(City::getName)).limit(5).collect(Collectors.toList());
//            for (City city : collect) {
//                System.out.println(city.getName() + " " + city.getState() + " " + city.getPopulation());
//            }

            Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
            locales = Stream.of(Locale.getAvailableLocales());
            Map<String, Set<Locale>> countryToLocaleSet = locales.collect(groupingBy(Locale::getCountry, toSet()));
            //System.out.println(countryToLocaleSet);

            locales = Stream.of(Locale.getAvailableLocales());
            Map<String, Long> countryToLocaleCounts = locales.collect(groupingBy(Locale::getCountry, counting()));
            //System.out.println(countryToLocaleCounts);

            Stream<City> cities = readCities("cities.txt");
            Map<String, Integer> stateToCityPopulation = cities.collect(groupingBy(City::getState,
                    summingInt(City::getPopulation)));
            //System.out.println(stateToCityPopulation);

            cities = readCities("cities.txt");
            Map<String, Optional<String>> stateToLongestCityName = cities
                    .collect(groupingBy(City::getState,
                            mapping(City::getName, maxBy(Comparator.comparing(String::length)))));
            //System.out.println(stateToLongestCityName);

            locales = Stream.of(Locale.getAvailableLocales());
            Map<String, Set<String>> countryToLanguages = locales
                    .collect(groupingBy(Locale::getDisplayCountry,
                            mapping(Locale::getDisplayCountry, toSet())));
            //System.out.println(countryToLanguages);

            cities = readCities("cities.txt");
            Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities
                    .collect(groupingBy(City::getState,
                            summarizingInt(City::getPopulation)));
            //System.out.println(stateToCityPopulationSummary);

            cities = readCities("cities.txt");
            Map<String, String> stateToCityNames = cities
                    .collect(groupingBy(City::getState,
                            reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s + ", " + t)));
            //System.out.println(stateToCityNames);

            cities = readCities("cities.txt");
            stateToCityNames = cities.collect(groupingBy(City::getState,
                    mapping(City::getName, joining(", "))));
            //System.out.println(stateToCityNames);


            // Redukcja
            Stream<Integer> numbers = Stream.iterate(1, n -> n+1).limit(10);
            List<Integer> collect = numbers.collect(toList());
            Optional<Integer> sum = collect.stream().reduce((a, b) -> a * b);
            sum.ifPresent(collect::add);
            System.out.println(sum);
            System.out.println(collect);

        }
    }
}

package pl.java.strumienie;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class CollectingIntoMaps {

    public static class Person {
        private int id;
        private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getClass().getName() + "[id=" + id + ",name=" + name + "]";
        }
    }

    public static Stream<Person> people() {
        return Stream.of(new Person(1001, "Piotr"), new Person(1002, "Pawel"), new Person(1003, "Maria"));
    }

    public static void main(String[] args) {
        Map<Integer, String> idToName = people().collect(
                Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName" + idToName);

        Map<Integer, Person> idToPerson = people().collect(
                Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson" + idToPerson.getClass().getName() + idToPerson);

        idToPerson = people().collect(
                Collectors.toMap(Person::getId, Function.identity(),
                        (existingValue, newValue) -> {
                            throw new IllegalStateException();
                        },
                        TreeMap::new));
        //System.out.println(idToPerson);
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> languageNames = locales.collect(Collectors.toMap(
                Locale::getDisplayName,
                l -> l.getDisplayLanguage(l),
                (existingValue, newValue) -> existingValue));
        //System.out.println("languageNames: " + languageNames);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryLanguageSets = locales.collect(
                Collectors.toMap(
                        Locale::getDisplayName,
                        l -> Set.of(l.getDisplayLanguage()),
                        (a, b) -> {
                            Set<String> union = new HashSet<>(a);
                            union.addAll(b);
                            return union;
                        }));
        locales = Stream.of(Locale.getAvailableLocales());

//        Map<String, Set<String>> countryToLanguages = locales.collect(groupingBy(Locale::getDisplayCountry,
//                mapping(Locale::getDisplayCountry, toSet())));

        //countryLanguageSets.forEach((k, v) -> System.out.println(v));
        //countryLanguageSets.put("a", Set.of("aaaaaaaaaaaaaaaaaaa"));
        //System.out.println("countryLanguageSets: " + countryLanguageSets);

        //Map<String, List<Locale>> countryToLocales = locales.collect(Collectors.groupingBy(Locale::getCountry));
        //System.out.println(countryToLocales);
        //List<Locale> swissLocales = countryToLocales.get("CH");
        //System.out.println(swissLocales);
//        Map<Boolean, List<Locale>> englishAndOtherLocales = locales.collect(
//                Collectors.partitioningBy(l -> l.getLanguage().equals("en")));
//        List<Locale> englishLocale = englishAndOtherLocales.get(true);
//        System.out.println(englishLocale);
    }
}

package by.sologub;

import by.sologub.model.Animal;
import by.sologub.model.Car;
import by.sologub.model.Flower;
import by.sologub.model.House;
import by.sologub.model.Person;
import by.sologub.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int zooToPrint = 2;
        int animalsPerZoo = 7;
        AtomicInteger zooIndex = new AtomicInteger(0);
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparing(Animal::getAge))
                .collect(Collectors.groupingBy(f -> zooIndex.getAndIncrement() / animalsPerZoo))
                .getOrDefault(zooToPrint, new ArrayList<>())
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> "Japanese".equals(animal.getOrigin()))
                .peek(animal -> animal.setGender(animal.getGender().toUpperCase()))
                .filter(animal -> "Female".equals(animal.getGender()))
                .map(Animal::getGender)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        long count = animals.stream()
                .filter(animal -> "Female".equals(animal.getGender())).count();
        System.out.println(count);
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isHungarianExists = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin()));
        System.out.println(isHungarianExists);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isCorrectGender = animals.stream()
                .allMatch(animal -> "Male".equals(animal.getGender()) || "Female".equals(animal.getGender()));
        System.out.println(isCorrectGender);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean noneMatchWithOceania = animals.stream()
                .noneMatch(animal -> "Oceania".equals(animal.getOrigin()));
        System.out.println(noneMatchWithOceania);
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread)).limit(100)
                .max(Comparator.comparing(Animal::getAge))
                .ifPresent(System.out::println);
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(animal -> animal.getBread().toCharArray())
                .min(Comparator.comparing(array -> array.length))
                .ifPresent(array -> System.out.println(array.length));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int ageSum = animals.stream().mapToInt(Animal::getAge).sum();
        System.out.println(ageSum);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge).average().ifPresent(System.out::println);
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> person.getGender().equals("Male"))
                .filter(person -> LocalDate.now().minusYears(18).isAfter(person.getDateOfBirth()))
                .filter(person -> LocalDate.now().minusYears(27).isAfter(person.getDateOfBirth()))
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        Predicate<House> sickAndWoundedPredicate = house -> house.getBuildingType()
                .equals("Hospital");
        Predicate<Person> childrenPredicate = person -> person.getDateOfBirth()
                .isAfter(LocalDate.now().minusYears(18));
        Predicate<Person> oldPeoplePredicate = person -> person.getDateOfBirth()
                .isBefore(LocalDate.now().minusYears(65));
        Stream<Person> sickAndWounded = houses.stream()
                .filter(sickAndWoundedPredicate)
                .flatMap(house -> house.getPersonList().stream());
        Stream<Person> childrenAndOldPeople = houses.stream()
                .filter(sickAndWoundedPredicate.negate())
                .flatMap(house -> house.getPersonList().stream())
                .filter(childrenPredicate.or(oldPeoplePredicate));
        Stream<Person> remainingPeople = houses.stream()
                .filter(sickAndWoundedPredicate.negate())
                .flatMap(house -> house.getPersonList().stream())
                .filter(childrenPredicate.negate().and(oldPeoplePredicate.negate()));
        Stream.concat(Stream.concat(sickAndWounded, childrenAndOldPeople), remainingPeople)
                .limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        //        Продолжить...
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }
}
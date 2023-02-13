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
        animals.stream().filter(animal -> animal.getOrigin().equals("Japanese"))
                .map(animal -> animal.getGender().equals("Female") ?
                        animal.getBread().toUpperCase() : animal.getBread())
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
        long count = animals.stream().filter(animal -> animal.getGender().equals("Female")).count();
        System.out.println(count);
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isHungarianExists = animals.stream().filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));
        System.out.println(isHungarianExists);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isCorrectGender = animals.stream()
                .allMatch(animal -> animal.getGender().equals("Male") || animal.getGender().equals("Female"));
        System.out.println(isCorrectGender);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean noneMatchWithOceania = animals.stream().noneMatch(animal -> animal.getOrigin().equals("Oceania"));
        System.out.println(noneMatchWithOceania);
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int maxAge = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread)).limit(100)
                .max(Comparator.comparing(Animal::getAge))
                .get().getAge();
        System.out.println(maxAge);
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int minArrayLength = animals.stream()
                .map(animal -> animal.getBread().toCharArray())
                .min(Comparator.comparing(array -> array.length))
                .get().length;
        System.out.println(minArrayLength);
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int ageSum = animals.stream().mapToInt(Animal::getAge).sum();
        System.out.println(ageSum);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        double indonesianAverageAge = animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge).average().getAsDouble();
        System.out.println(indonesianAverageAge);
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(
                        person -> person.getGender().equals("Male") &&
                        LocalDate.now().minusYears(18).isAfter(person.getDateOfBirth()) &&
                        LocalDate.now().minusYears(27).isAfter(person.getDateOfBirth())
                )
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        Predicate<House> peopleFromHospital = house -> house.getBuildingType()
                .equals("Hospital");
        Predicate<Person> children = person -> person.getDateOfBirth()
                .isAfter(LocalDate.now().minusYears(18));
        Predicate<Person> oldPeople = person -> person.getDateOfBirth()
                .isBefore(LocalDate.now().minusYears(65));
        Stream<Person> sickAndWounded = houses.stream()
                .filter(peopleFromHospital)
                .flatMap(house -> house.getPersonList().stream());
        Stream<Person> childrenAndOldPeople = houses.stream()
                .filter(peopleFromHospital.negate())
                .flatMap(house -> house.getPersonList().stream())
                .filter(children.or(oldPeople));
        Stream<Person> remainingPeople = houses.stream()
                .filter(peopleFromHospital.negate())
                .flatMap(house -> house.getPersonList().stream())
                .filter(children.negate().and(oldPeople.negate()));
        Stream.concat(sickAndWounded, Stream.concat(childrenAndOldPeople, remainingPeople))
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
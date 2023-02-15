package by.kutovenko;

import by.kutovenko.model.Animal;
import by.kutovenko.model.Car;
import by.kutovenko.model.Flower;
import by.kutovenko.model.House;
import by.kutovenko.model.Person;
import by.kutovenko.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
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
        Predicate<Car> turkmenianCarsPredicate = car -> "Jaguar".equals(car.getCarMake()) ||
                "White".equals(car.getColor());
        Predicate<Car> uzbekCarsPredicate = car -> car.getMass() <= 1500 ||
                "BMW".equals(car.getCarMake()) || "Lexus".equals(car.getCarMake()) ||
                "Chrysler".equals(car.getCarMake()) || "Toyota".equals(car.getCarMake());
        Predicate<Car> kazakhCarsPredicate = car -> car.getMass() > 4000 && "Black".equals(car.getColor()) ||
                "GMC".equals(car.getCarMake()) || "Dodge".equals(car.getCarMake());
        Predicate<Car> kyrgyzCarsPredicate = car -> car.getReleaseYear() < 1982 ||
                "Civic".equals(car.getCarModel()) || "Cherokee".equals(car.getCarMake());
        Predicate<Car> russianCarsPredicate = car -> !"Yellow".equals(car.getColor()) &&
                !"Red".equals(car.getColor()) && !"Green".equals(car.getColor()) &&
                !"Blue".equals(car.getColor()) || car.getPrice() > 40000;
        Predicate<Car> mongolianCarsPredicate = car -> car.getVin().contains("59");
        ToDoubleFunction<Car> calculateExpenses = car -> car.getMass() * 0.001 * 7.14;
        double totalCost = Stream.of(
                cars.stream().filter(turkmenianCarsPredicate)
                        .mapToDouble(calculateExpenses)
                        .sum(),
                cars.stream().filter(uzbekCarsPredicate
                                .and(turkmenianCarsPredicate.negate()))
                        .mapToDouble(calculateExpenses)
                        .sum(),
                cars.stream().filter(kazakhCarsPredicate
                                .and(turkmenianCarsPredicate.negate())
                                .and(uzbekCarsPredicate).negate())
                        .mapToDouble(calculateExpenses)
                        .sum(),
                cars.stream().filter(kyrgyzCarsPredicate
                                .and(turkmenianCarsPredicate.negate())
                                .and(uzbekCarsPredicate).negate()
                                .and(kazakhCarsPredicate).negate())
                        .mapToDouble(calculateExpenses)
                        .sum(),
                cars.stream().filter(russianCarsPredicate
                                .and(turkmenianCarsPredicate.negate())
                                .and(uzbekCarsPredicate).negate()
                                .and(kazakhCarsPredicate).negate()
                                .and(kyrgyzCarsPredicate).negate())
                        .mapToDouble(calculateExpenses)
                        .sum(),
                cars.stream().filter(mongolianCarsPredicate
                                .and(turkmenianCarsPredicate.negate())
                                .and(uzbekCarsPredicate).negate()
                                .and(kazakhCarsPredicate).negate()
                                .and(kyrgyzCarsPredicate).negate()
                                .and(russianCarsPredicate).negate())
                        .mapToDouble(calculateExpenses)
                        .sum()
                )
                .peek(System.out::println)
                .mapToDouble(value -> value)
                .sum();
        System.out.println(totalCost);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        double totalPrice = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flower -> flower.getCommonName().charAt(0) >= 'C' && flower.getCommonName().charAt(0) <= 'S')
                .filter(Flower::isShadePreferred)
                .filter(flower -> flower.getFlowerVaseMaterial().contains("Glass") ||
                        flower.getFlowerVaseMaterial().contains("Aluminium") ||
                        flower.getFlowerVaseMaterial().contains("Steel"))
                .mapToDouble(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * 365 * 5 * 1.39 / 1000)
                .sum();
        System.out.printf("%.2f", totalPrice);
    }
}
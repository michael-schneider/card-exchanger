package card.exchanger.poc;

import java.util.Random;
import java.util.stream.Stream;

/**
 *
 * @author Michael Schneider
 */
public class Main {

    public void testGetAllPossibleExchangesKnownExample() {
        // Arrange
        Person[] persons = new Person[]{
            new Person(new String[]{"A", "B"}, new String[]{"C", "D", "E", "F"}),
            new Person(new String[]{"C", "D"}, new String[]{"A", "B", "E", "F"}),
            new Person(new String[]{"E", "F"}, new String[]{"A", "B", "C", "D"})};

        // Act
        final Exchange[] exchanges = new Exchanges(persons).getAllPossibleExchanges();
        final Resolver resolver = new Resolver(exchanges);

        System.out.println("--------------------------------------");
        System.out.println("--------------ALL---------------------");

        for (Exchange[] foundExchangesChain : resolver.calculateAllExchangeCombinations()) {
            System.out.println("--------------------------------------");
            Stream.of(foundExchangesChain).forEach(System.out::println);
        }

    }

    public void testGetBestPossibleExchangesKnownExample() {
        // Arrange
        Person[] persons = new Person[]{
            new Person(new String[]{"A", "B"}, new String[]{"C", "D", "E", "F"}),
            new Person(new String[]{"C", "D"}, new String[]{"A", "B", "E", "F"}),
            new Person(new String[]{"E", "F"}, new String[]{"A", "B", "C", "D"})};

        // Act
        final Exchange[] exchanges = new Exchanges(persons).getAllPossibleExchanges();
        final Resolver resolver = new Resolver(exchanges);

        System.out.println("--------------------------------------");
        System.out.println("--------------BEST--------------------");
        System.out.println("--------------------------------------");
        Stream.of(resolver.calculateBestExchangeCombination()).forEach(System.out::println);

    }

    public void testGetBestPossibleExchangesBigExample() {
        // Arrange
        final Random r = new Random();
        final int NUM_CARD_TYPES = 375;
        final int NUM_PERSONS = 30;
        final int NUM_DOUBLE = 30;
        final int NUM_MISSING = 30;
        String[] cards = new String[NUM_CARD_TYPES];

        for (int i = 0; i < cards.length; i++) {
            cards[i] = generateName();
        }

        Person[] persons = new Person[NUM_PERSONS];
        for (int i = 0; i < persons.length; i++) {
            String[] doubleCards = new String[NUM_DOUBLE];
            for (int j = 0; j < doubleCards.length; j++) {
                doubleCards[j] = cards[r.nextInt(NUM_CARD_TYPES)];
            }
            String[] missingCards = new String[NUM_MISSING];
            for (int j = 0; j < missingCards.length; j++) {
                missingCards[j] = cards[r.nextInt(NUM_CARD_TYPES)];
            }
            persons[i] = new Person(doubleCards, missingCards);
        }

        // Act
        final Exchange[] exchanges = new Exchanges(persons).getAllPossibleExchanges();
        final Resolver resolver = new Resolver(exchanges);

        System.out.println("--------------------------------------");
        System.out.println("--------------BIG---------------------");
        System.out.println("--------------------------------------");
        Stream.of(resolver.calculateBestExchangeCombination()).forEach(System.out::println);

    }

    private String generateName() {
        Random r = new Random(); // perhaps make it a class variable so you don't make a new one every time
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char c = (char) ((int) (Math.random() * 26) + 97);
            sb.append(c);
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) {
        final Main main = new Main();

        //main.testGetBestPossibleExchangesBigExample();
        main.testGetBestPossibleExchangesKnownExample();
        main.testGetAllPossibleExchangesKnownExample();
    }
}

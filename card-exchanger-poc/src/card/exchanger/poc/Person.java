package card.exchanger.poc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Michael Schneider
 */
public class Person {

    private final String[] doubleCards;
    private final String[] missingCards;
    private final String name; // Has to be unique

    public Person(String[] doubleCards, String[] missingCards) {
        this.doubleCards = Arrays.copyOf(doubleCards, doubleCards.length);
        this.missingCards = Arrays.copyOf(missingCards, missingCards.length);
        name=generateName();
    }

    public boolean isCardMissing(String card) {
        return Arrays.asList(missingCards).contains(card);
    }

    public boolean isCardDouble(String card) {
        return Arrays.asList(doubleCards).contains(card);
    }

    public String[] getDoubleCards() {
        return Arrays.copyOf(doubleCards, doubleCards.length);
    }

    public String[] getMissingCards() {
        return Arrays.copyOf(missingCards, missingCards.length);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    private String generateName() {
        Random r = new Random(); // perhaps make it a class variable so you don't make a new one every time
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char c = (char)((int)(Math.random()*26)+97);
            sb.append(c);
        }
        return sb.toString();
    }

}

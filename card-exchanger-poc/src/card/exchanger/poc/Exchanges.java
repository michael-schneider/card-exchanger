package card.exchanger.poc;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculates all exchanges possible given users with double and missing cards.
 *
 * @author Michael Schneider
 */
public class Exchanges {

    private final Person[] persons;
    private final Set<Exchange> allPossibleExchanges = new HashSet<>();

    public Exchanges(Person[] persons) {
        this.persons = new Person[persons.length];
        System.arraycopy(persons, 0, this.persons, 0, persons.length);
        calculateExchanges();
    }

    public Exchange[] getAllPossibleExchanges() {
        return allPossibleExchanges.toArray(new Exchange[allPossibleExchanges.size()]);
    }

    private void calculateExchanges() {
        for (Person person : persons) {
            final String[] doubleCards = person.getDoubleCards();
            final String[] missingCards = person.getMissingCards();
            for (Person exchangePartner : persons) {
                if (exchangePartner == person) {
                    continue;
                }
                for (String doubleCard : doubleCards) {
                    if (exchangePartner.isCardMissing(doubleCard)) {
                        for (String missingCard : missingCards) {
                            if (exchangePartner.isCardDouble(missingCard)) {
                                // Every exchange is found two times, "Exchange" takes care of that;
                                allPossibleExchanges.add(new Exchange(doubleCard, person, missingCard, exchangePartner));
                            }
                        }
                    }
                }

            }

        }
    }
}

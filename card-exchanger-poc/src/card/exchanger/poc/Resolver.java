package card.exchanger.poc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Michael Schneider
 */
public class Resolver {

    private final Exchange[] allPossibleExchanges;
    private final Set<Set<Exchange>> foundExchangeChains = new HashSet<>();
    private Set<Exchange> foundBestExchangeChain = new HashSet<>();

    public Resolver(final Exchange[] allPossibleExchanges) {
        this.allPossibleExchanges = new Exchange[allPossibleExchanges.length];
        System.arraycopy(allPossibleExchanges, 0, this.allPossibleExchanges, 0, allPossibleExchanges.length);
    }

    public Exchange[][] findAllExchangeCombinations() {
        foundExchangeChains.clear();
        final Set<Exchange> possibleExchanges = new HashSet<>(Arrays.asList(allPossibleExchanges));
        for (final Exchange exchange : possibleExchanges) {
            findNextExchange(new HashSet<>(possibleExchanges), new HashSet<>(), exchange);
        }
        return getFoundExchangeChainsAsArray();
    }

    public Exchange[] findBestExchangeCombination() {
        foundBestExchangeChain.clear();

        final Set<Exchange> possibleExchanges = new HashSet<>(Arrays.asList(allPossibleExchanges));
        for (final Exchange exchange : possibleExchanges) {
            findNextBestExchange(new HashSet<>(possibleExchanges), new HashSet<>(), exchange);
        }
        return foundBestExchangeChain.toArray(new Exchange[foundBestExchangeChain.size()]);
    }

    private void findNextExchange(final Set<Exchange> possibleExchanges, final Set<Exchange> exchangesDone, final Exchange nextExchange) {
        doExchange(possibleExchanges, nextExchange, exchangesDone);

        if (possibleExchanges.isEmpty()) {
            addSolution(exchangesDone);
        } else {
            for (final Exchange exchange : possibleExchanges) {
                findNextExchange(new HashSet<>(possibleExchanges), new HashSet<>(exchangesDone), exchange);
            }
        }
    }

    private void findNextBestExchange(final Set<Exchange> possibleExchanges, final Set<Exchange> exchangesDone, final Exchange nextExchange) {
        doExchange(possibleExchanges, nextExchange, exchangesDone);

        if (possibleExchanges.isEmpty()) {
            testAndAddBestSolution(exchangesDone);
        } else {
            for (final Exchange exchange : possibleExchanges) {
                findNextBestExchange(new HashSet<>(possibleExchanges), new HashSet<>(exchangesDone), exchange);
            }
        }
    }

    private void doExchange(final Set<Exchange> possibleExchanges, final Exchange nextExchange, final Set<Exchange> exchangesDone) {
        possibleExchanges.remove(nextExchange);
        exchangesDone.add(nextExchange);

        possibleExchanges.removeIf(e -> e.firstCard.equals(nextExchange.firstCard) && e.firstPerson.equals(nextExchange.firstPerson));
        possibleExchanges.removeIf(e -> e.secondCard.equals(nextExchange.secondCard) && e.secondPerson.equals(nextExchange.secondPerson));
        possibleExchanges.removeIf(e -> e.firstCard.equals(nextExchange.secondCard) && e.firstPerson.equals(nextExchange.secondPerson));
        possibleExchanges.removeIf(e -> e.secondCard.equals(nextExchange.firstCard) && e.secondPerson.equals(nextExchange.firstPerson));
    }

    private synchronized void addSolution(final Set<Exchange> possibleExchanges) {
        foundExchangeChains.add(possibleExchanges);
    }

    private synchronized void testAndAddBestSolution(final Set<Exchange> exchangesDone) {
        if (exchangesDone.size() > foundBestExchangeChain.size()) {
            foundBestExchangeChain = exchangesDone;
        }
    }

    private Exchange[][] getFoundExchangeChainsAsArray() {
        Exchange[][] ret = new Exchange[foundExchangeChains.size()][];
        int index = 0;

        for (Set<Exchange> exchangeChain : foundExchangeChains) {
            ret[index++] = exchangeChain.toArray(new Exchange[exchangeChain.size()]);
        }
        return ret;
    }

}

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

    public Resolver(Exchange[] allPossibleExchanges) {
        this.allPossibleExchanges = allPossibleExchanges;
    }

    public Exchange[][] calculateAllExchangeCombinations() {
        final Set<Set<Exchange>> allExchangeChains = new HashSet<Set<Exchange>>();
        final Set<Exchange> possibleExchanges = new HashSet<>(Arrays.asList(allPossibleExchanges));
        final Set<Exchange> exchanges = new HashSet<>();
        for (Exchange exchange : possibleExchanges) {
            allExchangeChains.add(doNextExchange(new HashSet<>(possibleExchanges), new HashSet<Exchange>(exchanges), exchange));
        }
        return getFoundExchangeChains(allExchangeChains);
    }
    
    public Exchange[] calculateBestExchangeCombination() {
        Set<Exchange> currentExchangeChain = new HashSet<Exchange>();
        Set<Exchange> currentBestExchangeChain = new HashSet<Exchange>();
        int currentBest=0;
        
        final Set<Exchange> possibleExchanges = new HashSet<>(Arrays.asList(allPossibleExchanges));
        final Set<Exchange> exchanges = new HashSet<>();
        for (Exchange exchange : possibleExchanges) {
            currentExchangeChain=doNextExchange(new HashSet<>(possibleExchanges), new HashSet<Exchange>(exchanges), exchange);
            int currentValue=currentExchangeChain.size();
            if(currentValue>currentBest) {
                currentBest=currentValue;
                currentBestExchangeChain=currentExchangeChain;
            }
        }
        return currentBestExchangeChain.toArray(new Exchange[currentBestExchangeChain.size()]);
    }

    private Set<Exchange> doNextExchange(Set<Exchange> possibleExchanges, Set<Exchange> exchanges, Exchange nextExchange) {
        possibleExchanges.remove(nextExchange);
        exchanges.add(nextExchange);

        possibleExchanges.removeIf(e -> e.firstCard.equals(nextExchange.firstCard) && e.firstPerson.equals(nextExchange.firstPerson));
        possibleExchanges.removeIf(e -> e.secondCard.equals(nextExchange.secondCard) && e.secondPerson.equals(nextExchange.secondPerson));
        possibleExchanges.removeIf(e -> e.firstCard.equals(nextExchange.secondCard) && e.firstPerson.equals(nextExchange.secondPerson));
        possibleExchanges.removeIf(e -> e.secondCard.equals(nextExchange.firstCard) && e.secondPerson.equals(nextExchange.firstPerson));

        for (Exchange exchange : possibleExchanges) {
            return doNextExchange(new HashSet<>(possibleExchanges), new HashSet<>(exchanges), exchange);
        }

        return exchanges;
    }

    private Exchange[][] getFoundExchangeChains(Set<Set<Exchange>> foundExchangeChains) {
        Exchange[][] ret = new Exchange[foundExchangeChains.size()][];
        int index = 0;

        for (Set<Exchange> exchangeChain : foundExchangeChains) {
            ret[index++] = exchangeChain.toArray(new Exchange[exchangeChain.size()]);
        }
        return ret;
    }
}

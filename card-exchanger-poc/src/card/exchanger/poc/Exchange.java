package card.exchanger.poc;

import java.util.Objects;

/**
 *
 * @author Michael Schneider
 */
public class Exchange {

    final String firstCard;
    final Person firstPerson;
    final String secondCard;
    final Person secondPerson;

    public Exchange(String firstCard, Person firstPerson, String secondCard, Person secondPerson) {
        if (firstPerson.getName().compareTo(secondPerson.getName()) <0 ) {
            this.firstCard = firstCard;
            this.firstPerson = firstPerson;
            this.secondCard = secondCard;
            this.secondPerson = secondPerson;
        } else {
            this.firstCard = secondCard;
            this.firstPerson = secondPerson;
            this.secondCard = firstCard;
            this.secondPerson = firstPerson;
        }
    }

    public String getFirstCard() {
        return firstCard;
    }

    public Person getFirstPerson() {
        return firstPerson;
    }

    public String getSecondCard() {
        return secondCard;
    }

    public Person getSecondPerson() {
        return secondPerson;
    }

    @Override
    public String toString() {
        return String.format("Exchange %s:%s -> %s:%s", firstPerson, firstCard, secondPerson, secondCard);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.firstCard);
        hash = 43 * hash + Objects.hashCode(this.firstPerson);
        hash = 43 * hash + Objects.hashCode(this.secondCard);
        hash = 43 * hash + Objects.hashCode(this.secondPerson);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Exchange other = (Exchange) obj;
        if (!Objects.equals(this.firstCard, other.firstCard)) {
            return false;
        }
        if (!Objects.equals(this.secondCard, other.secondCard)) {
            return false;
        }
        if (!Objects.equals(this.firstPerson, other.firstPerson)) {
            return false;
        }
        if (!Objects.equals(this.secondPerson, other.secondPerson)) {
            return false;
        }
        return true;
    }

}

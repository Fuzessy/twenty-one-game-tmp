package hu.fuz.twentyone.model;

import hu.fuz.twentyone.CardRank;

import java.util.Objects;

public class Card {
    private final CardRank rank;

    public Card(CardRank rank) {
        this.rank = rank;
    }

    public CardRank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object otherCard) {
        return otherCard != null
                && otherCard.getClass() == this.getClass()
                && rank == ((Card) otherCard).rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank);
    }
}

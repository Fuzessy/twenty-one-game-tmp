package hu.fuz.twentyone.model;

import hu.fuz.twentyone.CardRank;

public class Card {
    private final CardRank rank;

    public Card(CardRank rank) {
        this.rank = rank;
    }

    public CardRank getRank() {
        return rank;
    }
}

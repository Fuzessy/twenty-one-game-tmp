package hu.fuz.twentyone;

import hu.fuz.twentyone.model.Card;

public class CardEvaluator {
    public int evaluate(Card card) {
        checkCard(card);
        return evaluateCardByRank(card);
    }

    private Integer evaluateCardByRank(Card card) {
        switch (card.getRank()){
            case UNTER: return 2;
            case OBER:  return 3;
            case KING:  return 4;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE:  return 9;
            case TEN:   return 10;
            case ACE:   return 11;
        }
        throw new InvalidCardRangeException();
    }

    private void checkCard(Card card) {
        if(card == null) throw new CardIsNullException();
        if(card.getRank() == null) throw new CardRankIsNullException();
    }
}

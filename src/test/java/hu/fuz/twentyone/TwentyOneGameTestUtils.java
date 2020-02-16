package hu.fuz.twentyone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import hu.fuz.twentyone.model.Card;

public class TwentyOneGameTestUtils {

    private final TwentyOneGame twentyOneGame;
    private List<Card> dummyCards;
    private List<Card> oberSevenEightSevenUnterPack;
    private List<Card> dummyInvalidUnters;
    private List<Card> dummyInvalidTens;

    public TwentyOneGameTestUtils(TwentyOneGame twentyOneGame) {
        this.twentyOneGame = twentyOneGame;
        dummyCards = Arrays.asList(new Card(CardRank.KING), new Card(CardRank.ACE), new Card(CardRank.KING)
                , new Card(CardRank.SEVEN), new Card(CardRank.EIGHT), new Card(CardRank.TEN));

        oberSevenEightSevenUnterPack = Arrays.asList(
                new Card(CardRank.OBER), new Card(CardRank.SEVEN),
                new Card(CardRank.EIGHT), new Card(CardRank.SEVEN),
                new Card(CardRank.UNTER));

        dummyInvalidUnters = createCards(CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER
                ,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER
                ,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER);

        dummyInvalidTens = createCards(CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN
                ,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN
                ,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN);
    }


    public List<Card> getDummyCards() {
        return dummyCards;
    }

    public List<Card> getOberSevenEightSevenUnterPack() {
        return oberSevenEightSevenUnterPack;
    }

    public List<Card> getDummyInvalidUnters() {
        return dummyInvalidUnters;
    }

    public void assertCardsOfPlayer(int player, CardRank...ranks){
        List<Card> cardsShouldBeInHand = createCards(ranks);
        assertThat(cardsShouldBeInHand, is(twentyOneGame.getCardsOfPlayer(player)));
    }

    public List<Card> createCards(CardRank... ranks) {
        return Arrays.stream(ranks).map(Card::new).collect(Collectors.toList());
    }

    public void initGame(int players, List<Card> cards) {
        Dealer<Card> dealer = new DealerForGetCardsOfPlayertest(cards);
        twentyOneGame.setDealer(dealer);
        twentyOneGame.startGame(players);
    }

    public List<Card> getDummyInvalidTens() {
        return dummyInvalidTens;
    }

    public void drawsCardAndPlayNext(int repeatNumber) {
        for (int i = 0; i < repeatNumber; i++) {
            twentyOneGame.actualPlayerDrawsCard();
            twentyOneGame.playNext();
        }
    }

    private class DealerForGetCardsOfPlayertest implements Dealer<Card> {
        private final List<Card> cards;
        private int counter = -1;

        public DealerForGetCardsOfPlayertest(List<Card> cards) {
            this.cards = cards;
        }

        public Card getNextCard(){
            counter++;
            return cards.get(counter);
        }
    }

}

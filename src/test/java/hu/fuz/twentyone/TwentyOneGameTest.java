package hu.fuz.twentyone;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import hu.fuz.twentyone.model.Card;

public class TwentyOneGameTest {

    private TwentyOneGame twentyOneGame;
    private List<Card> dummyCards;

    @Before
    public void init(){
        twentyOneGame = new TwentyOneGame();
        dummyCards = Arrays.asList(new Card(CardRank.KING), new Card(CardRank.ACE), new Card(CardRank.KING)
                , new Card(CardRank.SEVEN), new Card(CardRank.EIGHT), new Card(CardRank.TEN));
    }

    @Test
    public void startGameWithOnePlayerTest(){
        initGame(1,dummyCards);
        assertEquals(1,twentyOneGame.getPlayersCount());
    }

    @Test
    public void startGameWithThreePlayerTest(){
        initGame(3, dummyCards);
        assertEquals(3,twentyOneGame.getPlayersCount());
    }

    @Test
    public void getSizeOfCardsOfPlayerTest(){
        initGame(2,dummyCards);
        assertEquals(2,twentyOneGame.getCardsOfPlayer(1).size());
    }

    @Test
    public void getCardsOfPlayerTest(){
        List<Card> playerOneCards = Arrays.asList(new Card(CardRank.KING), new Card(CardRank.ACE));
        initGame(1,playerOneCards);
        assertThat(playerOneCards,is(twentyOneGame.getCardsOfPlayer(0)));
    }


    @Test
    public void getCardsValeOfPlayerOne(){
        List<Card> playerOneCards = Arrays.asList(new Card(CardRank.KING), new Card(CardRank.ACE));
        initGame(1,playerOneCards);
        assertEquals(15,twentyOneGame.getCardsValueOfPlayer(0));
    }

    private void initGame(int players, List<Card> cards) {
        Dealer<Card> dealer = new DealerForGetCardsOfPlayertest(cards);
        twentyOneGame.setDealer(dealer);
        twentyOneGame.startGame(players);
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

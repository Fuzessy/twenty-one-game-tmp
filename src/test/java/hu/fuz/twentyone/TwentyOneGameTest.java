package hu.fuz.twentyone;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import hu.fuz.twentyone.model.Card;

public class TwentyOneGameTest {

    private TwentyOneGame twentyOneGame;
    private List<Card> dummyCards;
    private List<Card> oberSevenEightSevenUnterPack;

    @Before
    public void init(){
        twentyOneGame = new TwentyOneGame();
        dummyCards = Arrays.asList(new Card(CardRank.KING), new Card(CardRank.ACE), new Card(CardRank.KING)
                , new Card(CardRank.SEVEN), new Card(CardRank.EIGHT), new Card(CardRank.TEN));

        oberSevenEightSevenUnterPack = Arrays.asList(
                new Card(CardRank.OBER), new Card(CardRank.SEVEN),
                new Card(CardRank.EIGHT), new Card(CardRank.SEVEN),
                new Card(CardRank.UNTER));
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
    public void startGameAndGetCountOfCardsTest(){
        initGame(2,dummyCards);
        assertEquals(2,twentyOneGame.getCardsOfPlayer(1).size());
        assertEquals(2,twentyOneGame.getCardsOfPlayer(0).size());
    }

    @Test
    public void startGameAndGetValueOfCardsTest(){
        initGame(1,oberSevenEightSevenUnterPack);
        assertEquals(10,twentyOneGame.getCardsValueOfPlayer(0));
    }

    @Test
    public void startGameAndGetActualGamerTest(){
        initGame(1,dummyCards);

        assertEquals(0,twentyOneGame.getActualPlayer());
    }

    @Test
    public void startGameAndActualPlayerDrawsCardTest(){
        initGame(2, oberSevenEightSevenUnterPack);

        twentyOneGame.actualPlayerDrawsCard();

        assertThat(Arrays.asList(
                new Card(CardRank.OBER), new Card(CardRank.EIGHT),new Card(CardRank.UNTER)),
                is(twentyOneGame.getCardsOfPlayer(0)));
    }

    @Test(expected = UserCantStopWhenHandValueUnder15Exception.class)
    public void stopGameUnder15WhenCardValueIs10Test(){
        initGame(1,oberSevenEightSevenUnterPack);

        assertTrue(twentyOneGame.getCardsValueOfPlayer(0) <= 15);
        twentyOneGame.stopActualPlayer();
    }

    @Test(expected = UserCantStopWhenHandValueUnder15Exception.class)
    public void stopGameUnder15WhenCardValueIs14Test(){
        initGame(1,Arrays.asList(new Card(CardRank.TEN), new Card(CardRank.KING)));

        assertTrue(twentyOneGame.getCardsValueOfPlayer(0) <= 15);
        twentyOneGame.stopActualPlayer();
    }

    @Test
    public void secondPlayersCardCheckTest(){
        initGame(2, oberSevenEightSevenUnterPack);
        twentyOneGame.actualPlayerDrawsCard();

        twentyOneGame.playNext();

        assertCardsOfPlayer(1,CardRank.SEVEN, CardRank.SEVEN);
    }

    @Test
    public void playNextAndCheckActualPlayerTest(){
        initGame(2, oberSevenEightSevenUnterPack);

        twentyOneGame.playNext();

        assertEquals(1,twentyOneGame.getActualPlayer());
    }

    @Test
    public void playNextAndDrawCardTest(){
        initGame(2, oberSevenEightSevenUnterPack);

        twentyOneGame.playNext();
        twentyOneGame.actualPlayerDrawsCard();

        assertCardsOfPlayer(0, CardRank.OBER, CardRank.EIGHT);
        assertCardsOfPlayer(1, CardRank.SEVEN, CardRank.SEVEN,CardRank.UNTER);
    }

    private void assertCardsOfPlayer(int player, CardRank...ranks){
        List<Card> cardsShouldBeInHand = Arrays.stream(ranks).map(Card::new).collect(Collectors.toList());
        assertThat(cardsShouldBeInHand, is(twentyOneGame.getCardsOfPlayer(player)));
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

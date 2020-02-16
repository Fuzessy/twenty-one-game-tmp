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
    private TwentyOneGameTestUtils utils;

    @Before
    public void init(){
        twentyOneGame = new TwentyOneGame();
        utils = new TwentyOneGameTestUtils(twentyOneGame);
    }
    @Test
    public void startGameAndActualPlayerDrawsCardTest(){
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());

        twentyOneGame.actualPlayerDrawsCard();

        assertThat(Arrays.asList(
                new Card(CardRank.OBER), new Card(CardRank.EIGHT),new Card(CardRank.UNTER)),
                is(twentyOneGame.getCardsOfPlayer(0)));
    }

    @Test(expected = UserCantStopWhenHandValueUnder15Exception.class)
    public void stopGameUnder15WhenCardValueIs10Test(){
        utils.initGame(1, utils.getOberSevenEightSevenUnterPack());

        assertTrue(twentyOneGame.getCardsValueOfPlayer(0) <= 15);
        twentyOneGame.stopActualPlayer();
    }

    @Test(expected = UserCantStopWhenHandValueUnder15Exception.class)
    public void stopGameUnder15WhenCardValueIs14Test(){
        utils.initGame(1,Arrays.asList(new Card(CardRank.TEN), new Card(CardRank.KING)));

        assertTrue(twentyOneGame.getCardsValueOfPlayer(0) <= 15);
        twentyOneGame.stopActualPlayer();
    }

    @Test
    public void secondPlayersCardCheckTest(){
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());
        twentyOneGame.actualPlayerDrawsCard();

        twentyOneGame.playNext();

        utils.assertCardsOfPlayer(1,CardRank.SEVEN, CardRank.SEVEN);
    }

    @Test
    public void playNextAndCheckActualPlayerTest(){
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());
        twentyOneGame.actualPlayerDrawsCard();

        twentyOneGame.playNext();

        assertEquals(1,twentyOneGame.getActualPlayer());
    }

    @Test
    public void playNextAndDrawCardTest(){
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());

        twentyOneGame.actualPlayerDrawsCard();
        twentyOneGame.playNext();

        utils.assertCardsOfPlayer(0, CardRank.OBER, CardRank.EIGHT, CardRank.UNTER);
        utils.assertCardsOfPlayer(1, CardRank.SEVEN, CardRank.SEVEN);
    }

    @Test
    public void actualPlayerIsEmptyWhenAllPlayerStoppedTest(){
        utils.initGame(1, utils.createCards(CardRank.TEN,CardRank.TEN));

        twentyOneGame.stopActualPlayer();
        twentyOneGame.playNext();

        assertEquals(-1,twentyOneGame.getActualPlayer());
    }

    @Test(expected = PlayerShouldStopOrDrawsCardException.class)
    public void playWith3PlayersWhenFirstPlayerCallNextTest(){
        utils.initGame(3, utils.createCards(
                CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER,CardRank.UNTER));

        twentyOneGame.playNext();
        assertEquals(1,twentyOneGame.getActualPlayer());
    }


    @Test
    public void playWith3PlayersWhenFirstAndSecondPlayerCallNextTest(){
        utils.initGame(3, utils.getDummyInvalidUnters());

        twentyOneGame.actualPlayerDrawsCard();twentyOneGame.playNext();
        twentyOneGame.actualPlayerDrawsCard();twentyOneGame.playNext();
        assertEquals(2,twentyOneGame.getActualPlayer());
    }

    @Test
    public void playWith3PlayersWhenFirstAndSecondAndThirdPlayerCallNextTest(){
        utils.initGame(3, utils.getDummyInvalidUnters());

        twentyOneGame.actualPlayerDrawsCard();twentyOneGame.playNext();
        twentyOneGame.actualPlayerDrawsCard();twentyOneGame.playNext();
        twentyOneGame.actualPlayerDrawsCard();twentyOneGame.playNext();
        assertEquals(0,twentyOneGame.getActualPlayer());
    }

    @Test
    public void playWith3PlayersWhenFirstAndSecondAndThirdPlayerCallNextAndStopTest(){
        utils.initGame(3, utils.createCards(CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,
                CardRank.UNTER));

        twentyOneGame.stopActualPlayer(); twentyOneGame.playNext();
        twentyOneGame.stopActualPlayer(); twentyOneGame.playNext();
        twentyOneGame.actualPlayerDrawsCard(); twentyOneGame.playNext();
        assertEquals(2,twentyOneGame.getActualPlayer());
    }

    @Test
    public void nextPlayerWith3PlayersTest(){
        utils.initGame(3, utils.getDummyInvalidUnters());

        twentyOneGame.actualPlayerDrawsCard(); twentyOneGame.playNext();
        assertEquals(1,twentyOneGame.getActualPlayer());
    }

    @Test(expected = HandValueMoreThan21Exception.class)
    public void playerDrawCardWhenHandValueMoreThen21(){
        List<Card> dummyInvalidTens = utils.createCards(CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN
                ,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN,CardRank.TEN);
        utils.initGame(1, dummyInvalidTens);

        twentyOneGame.actualPlayerDrawsCard(); twentyOneGame.playNext();
        twentyOneGame.actualPlayerDrawsCard();
    }

}

package hu.fuz.twentyone;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import hu.fuz.twentyone.model.Card;

public class TwentyOneGameTest {

    private TwentyOneGame twentyOneGame;
    private TwentyOneGameTestUtils utils;

    @Before
    public void init() {
        twentyOneGame = new TwentyOneGame();
        utils = new TwentyOneGameTestUtils(twentyOneGame);
    }

    @Test
    public void startGameAndActualPlayerDrawsCardTest() {
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());

        twentyOneGame.actualPlayerDrawsCard();

        assertThat(Arrays.asList(
                new Card(CardRank.OBER), new Card(CardRank.EIGHT), new Card(CardRank.UNTER)),
                is(twentyOneGame.getCardsOfPlayer(0)));
    }

    @Test(expected = UserCantStopWhenHandValueUnder15Exception.class)
    public void stopGameUnder15WhenCardValueIs10Test() {
        utils.initGame(1, utils.getOberSevenEightSevenUnterPack());
        assertTrue(twentyOneGame.getCardsValueOfPlayer(0) <= 15);
        twentyOneGame.stopActualPlayer();
    }

    @Test(expected = UserCantStopWhenHandValueUnder15Exception.class)
    public void stopGameUnder15WhenCardValueIs14Test(){
        utils.initGame(1, utils.getDummyInvalidUnters());

        assertTrue(twentyOneGame.getCardsValueOfPlayer(0) <= 15);
        twentyOneGame.stopActualPlayer();
    }

    @Test
    public void secondPlayersCardCheckTest() {
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());
        utils.drawsCardAndPlayNext(1);

        utils.assertCardsOfPlayer(1, CardRank.SEVEN, CardRank.SEVEN);
    }

    @Test
    public void playNextAndCheckActualPlayerTest() {
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());
        utils.drawsCardAndPlayNext(1);

        assertEquals(1, twentyOneGame.getActualPlayer());
    }

    @Test
    public void playNextAndDrawCardTest() {
        utils.initGame(2, utils.getOberSevenEightSevenUnterPack());

        utils.drawsCardAndPlayNext(1);

        utils.assertCardsOfPlayer(0, CardRank.OBER, CardRank.EIGHT, CardRank.UNTER);
        utils.assertCardsOfPlayer(1, CardRank.SEVEN, CardRank.SEVEN);
    }

    @Test(expected = PlayerShouldStopOrDrawsCardException.class)
    public void playWith3PlayersWhenFirstPlayerCallNextTest() {
        utils.initGame(3, utils.getDummyInvalidUnters());

        twentyOneGame.playNext();
        assertEquals(1, twentyOneGame.getActualPlayer());
    }


    @Test
    public void playWith3PlayersWhenFirstAndSecondPlayerCallNextTest() {
        utils.initGame(3, utils.getDummyInvalidUnters());

        utils.drawsCardAndPlayNext(2);

        assertEquals(2, twentyOneGame.getActualPlayer());
    }

    @Test
    public void playWith3PlayersWhenFirstAndSecondAndThirdPlayerCallNextTest() {
        utils.initGame(3, utils.getDummyInvalidUnters());

        utils.drawsCardAndPlayNext(3);

        assertEquals(0, twentyOneGame.getActualPlayer());
    }

    @Test
    public void playWith3PlayersWhenFirstAndSecondAndThirdPlayerCallNextAndStopTest() {
        utils.initGame(3, utils.getDummyInvalidTens());
        twentyOneGame.stopActualPlayer();
        twentyOneGame.playNext();
        twentyOneGame.stopActualPlayer();
        twentyOneGame.playNext();

        utils.drawsCardAndPlayNext(1);

        assertEquals(2, twentyOneGame.getActualPlayer());
    }

    @Test
    public void nextPlayerWith3PlayersTest() {
        utils.initGame(3, utils.getDummyInvalidUnters());

        utils.drawsCardAndPlayNext(1);

        assertEquals(1, twentyOneGame.getActualPlayer());
    }

    @Test(expected = HandValueMoreThan21Exception.class)
    public void playerDrawCardWhenHandValueMoreThen21() {
        utils.initGame(1, utils.getDummyInvalidTens());

        utils.drawsCardAndPlayNext(1);

        twentyOneGame.actualPlayerDrawsCard();
    }

    @Test
    public void allPlayerStopATest() {
        utils.initGame(2, utils.getDummyInvalidTens());

        twentyOneGame.stopActualPlayer();
        twentyOneGame.playNext();
        twentyOneGame.stopActualPlayer();
        twentyOneGame.playNext();

        assertEquals(-1, twentyOneGame.getActualPlayer());
    }

}

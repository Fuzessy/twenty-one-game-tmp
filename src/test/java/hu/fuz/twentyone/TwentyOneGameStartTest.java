package hu.fuz.twentyone;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TwentyOneGameStartTest {

    private TwentyOneGame twentyOneGame;
    private TwentyOneGameTestUtils utils;

    @Before
    public void init(){
        twentyOneGame = new TwentyOneGame();
        utils = new TwentyOneGameTestUtils(twentyOneGame);
    }

    @Test
    public void startGameWithOnePlayerTest(){
        utils.initGame(1,utils.getDummyCards());
        assertEquals(1,twentyOneGame.getPlayersCount());
    }

    @Test
    public void startGameWithThreePlayerTest(){
        utils.initGame(3, utils.getDummyCards());
        assertEquals(3,twentyOneGame.getPlayersCount());
    }

    @Test
    public void startGameAndGetCountOfCardsTest(){
        utils.initGame(2, utils.getDummyCards());
        assertEquals(2,twentyOneGame.getCardsOfPlayer(1).size());
        assertEquals(2,twentyOneGame.getCardsOfPlayer(0).size());
    }

    @Test
    public void startGameAndGetValueOfCardsTest(){
        utils.initGame(1,utils.getOberSevenEightSevenUnterPack());
        assertEquals(10,twentyOneGame.getCardsValueOfPlayer(0));
    }

    @Test
    public void startGameAndGetActualGamerTest(){
        utils.initGame(1, utils.getDummyCards());

        assertEquals(0,twentyOneGame.getActualPlayer());
    }

}

package hu.fuz.twentyone;

import hu.fuz.twentyone.model.Card;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TwentyOneGameTest {
    @Test
    public void startGameWithOnePlayerTest(){
        TwentyOneGame twentyOneGame = new TwentyOneGame();
        twentyOneGame.startGame(1);
        assertEquals(1,twentyOneGame.getPlayersCount());
    }

    @Test
    public void startGameWithThreePlayerTest(){
        TwentyOneGame twentyOneGame = new TwentyOneGame();
        twentyOneGame.startGame(3);
        assertEquals(3,twentyOneGame.getPlayersCount());
    }


    @Test
    public void getCardsOfPlayerTest(){
        TwentyOneGame twentyOneGame = new TwentyOneGame();
        twentyOneGame.startGame(3);
        assertEquals(2,twentyOneGame.getCardsOfPlayer(2).size());
    }

}

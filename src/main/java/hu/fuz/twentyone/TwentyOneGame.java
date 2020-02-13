package hu.fuz.twentyone;

import hu.fuz.twentyone.model.Card;

import java.util.Arrays;
import java.util.List;

public class TwentyOneGame {

    private int players;

    public void startGame(int players) {
        this.players = players;
    }

    public int getPlayersCount() {
        return players;
    }

    public List<Card> getCardsOfPlayer(int playerNumber) {
        return Arrays.asList(new Card(CardRank.ACE), new Card(CardRank.OBER));
    }
}

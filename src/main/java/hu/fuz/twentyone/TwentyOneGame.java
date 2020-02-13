package hu.fuz.twentyone;

import hu.fuz.twentyone.model.Card;

import java.util.*;
import java.util.stream.IntStream;

public class TwentyOneGame {

    private CardEvaluator cardEvaluator;

    private int players;
    private Dealer<Card> dealer;
    private Map<Integer,List<Card>> cardsOfPlayers;

    public TwentyOneGame(){
        this.cardEvaluator = new CardEvaluator();
    }

    public int getPlayersCount() {
        return players;
    }

    public List<Card> getCardsOfPlayer(int playerNumber) {
        return cardsOfPlayers.get(playerNumber);
    }

    public void setDealer(Dealer<Card> dealer) {
        this.dealer = dealer;
    }

    public void startGame(int players) {
        this.players = players;
        initGame();
    }

    private void initGame() {
        initCardsOfPlayer();
        dealToPlayers();
        dealToPlayers();
    }

    private void initCardsOfPlayer() {
        cardsOfPlayers = new HashMap<>();
        for (int i = 0; i < players; i++) {
            cardsOfPlayers.put(i,new ArrayList<Card>());
        }
    }

    private void dealToPlayers() {
        for (int i = 0; i < players; i++) {
            cardsOfPlayers.get(i).add(dealer.getNextCard());
        }
    }

    public int getCardsValueOfPlayer(int i) {
         return cardsOfPlayers.get(i).stream().map(c -> cardEvaluator.evaluate(c)).reduce(0, Integer::sum);
    }
}

package hu.fuz.twentyone;

import hu.fuz.twentyone.model.Card;

import java.util.*;

public class TwentyOneGame {

    private CardEvaluator cardEvaluator;

    private int players;
    private Dealer<Card> dealer;
    private Map<Integer,List<Card>> cardsOfPlayers;
    private Set<Integer> stoppedPlayers;
    private int actualPlayer;

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
        stoppedPlayers = new HashSet<>();
        actualPlayer = 0;
        initCardsAndDealStarter();
    }

    private void initCardsAndDealStarter() {
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

    public int getActualPlayer() {
        return this.actualPlayer;
    }

    public void stopActualPlayer() {
        if(getCardsValueOfPlayer(actualPlayer) < 15){
            throw new UserCantStopWhenHandValueUnder15Exception();
        }
        stoppedPlayers.add(actualPlayer);
    }

    public void actualPlayerDrawsCard() {
        cardsOfPlayers.get(actualPlayer).add(dealer.getNextCard());
    }

    public void playNext() {
        setNextPlayer();
    }

    private void setNextPlayer() {
        int i = 0;
        boolean nextPlayerFound;
        int supposedNextPlayer = actualPlayer;
        do{
            supposedNextPlayer = getSupposedNextPlayer(supposedNextPlayer);
            nextPlayerFound = !stoppedPlayers.contains(supposedNextPlayer);
            i++;
        }while(i < players && !nextPlayerFound);
        actualPlayer = nextPlayerFound ? supposedNextPlayer : -1;
    }

    private int getSupposedNextPlayer(int supposedNextPlayer) {
        return supposedNextPlayer+1 >= players ? 0 : supposedNextPlayer + 1;
    }
}

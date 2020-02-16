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
    private GameStatus gameStatus;

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

    public boolean isPlayerStopped(int supposedNextPlayer) {
        return !stoppedPlayers.contains(supposedNextPlayer);
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
        gameStatus = GameStatus.PLAYER_STOPPED;
    }

    public void actualPlayerDrawsCard() {
        checkDrawsCard();
        cardsOfPlayers.get(actualPlayer).add(dealer.getNextCard());
        this.gameStatus = GameStatus.PLAYER_DROW_A_CARD;
    }

    private void checkDrawsCard() {
        if( getCardsValueOfPlayer(actualPlayer) >= 21 )
            throw new HandValueMoreThan21Exception();
    }

    public void playNext() {
        checkPlayNextStatus();
        setNextPlayer();
    }

    private void checkPlayNextStatus() {
        if(this.gameStatus != GameStatus.PLAYER_STOPPED && this.gameStatus != GameStatus.PLAYER_DROW_A_CARD){
            throw new PlayerShouldStopOrDrawsCardException();
        }
    }

    private void setNextPlayer() {
        int i = 0;
        boolean nextPlayerFound;
        int suggestedNextPlayer = actualPlayer;
        do{
            suggestedNextPlayer = getSuggestedNextPlayer(suggestedNextPlayer);
            nextPlayerFound = isPlayerStopped(suggestedNextPlayer);
            i++;
        }while(i < players && !nextPlayerFound);
        actualPlayer = nextPlayerFound ? suggestedNextPlayer : -1;
    }

    private int getSuggestedNextPlayer(int playerIndex) {
        return playerIndex+1 >= players ? 0 : playerIndex + 1;
    }
}

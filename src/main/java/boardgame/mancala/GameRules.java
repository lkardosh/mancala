package mancala;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable{
    final ArrayList<Store> stores;
    final ArrayList<Player> players;
    final private MancalaDataStructure gameBoard;
    private int currentPlayer =1; // Player number (1 or 2)

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        stores = new ArrayList<>();
        players=new ArrayList<>();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }
    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(final int pitNum) {
        int indexNum;
        if(pitNum<7){
            indexNum=1;
        }else{
            indexNum=7;
        }
        final int finalIndex=indexNum+6;
        for(int i=indexNum;i<finalIndex;i++){
            if(getDataStructure().getNumStones(i)> 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(Player one, Player two) {
        final Store storeOne = new Store();
        final Store storeTwo=new Store();
        stores.add(storeOne);
        stores.add(storeTwo);
        players.add(one);
        players.add(two);
        one.setStore(stores.get(0));
        two.setStore(stores.get(1));
        getDataStructure().setStore(storeOne, 1);
        getDataStructure().setStore(storeTwo, 2);
        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    @Override
    public String toString() {
        final StringBuilder boardString = new StringBuilder();

        // Display pits for Player One
        for (int i = 1; i <= 6; i++) {
            boardString.append("Pit ").append(i).append(": ").append(getNumStones(i)).append(" stones\n");
        }
        boardString.append("Player 1 store: ").append(getDataStructure().getStoreCount(1)).append(" stones\n");
    
        // Display pits for Player Two
        for (int i = 7; i <= 12; i++) {
            boardString.append("Pit ").append(i).append(": ").append(getNumStones(i)).append(" stones\n");
        }
        boardString.append("Player 2 store: ").append(getDataStructure().getStoreCount(2)).append(" stones\n");
    
        return boardString.toString();
    }
}

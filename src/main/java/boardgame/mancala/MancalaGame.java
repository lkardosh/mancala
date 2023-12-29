package mancala;

import java.io.Serializable;
import java.util.ArrayList;

public class MancalaGame implements Serializable{
   
    private GameRules board;
    private Player currentPlayer;
    private Player playerOne;
    private Player playerTwo;
    //private boolean gameEnd;

    public MancalaGame(){
        this(new KalahRules());
    }

    public MancalaGame(final GameRules theGameRules){
        board=theGameRules;
        playerOne= new Player("Player One");
        playerTwo= new Player("Player Two");
        setPlayers(playerOne, playerTwo);
        board.resetBoard();
        currentPlayer=playerOne;
    }

    public GameRules getBoard(){
        return board;
    }

    /**
     * @param 
     * @return currentplayer
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getNumStones(final int pitNum)throws PitNotFoundException{
        if(pitNum<1||pitNum>12){
            throw new PitNotFoundException("Pit does not exist");
        }
        return board.getNumStones(pitNum);
    }

    public ArrayList<Player> getPlayers(){
        final ArrayList<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
        return players;
    }

    public int getStoreCount(final Player player)throws NoSuchPlayerException{
        int aPlayerNum=2;
        if(!player.equals(playerOne) && !player.equals(playerTwo)){
            throw new NoSuchPlayerException("The player entered does not exist");
        }

        if(player.equals(playerOne)){
            aPlayerNum=1;
        }

        return board.getDataStructure().getStoreCount(aPlayerNum);
        // player.getStoreCount();
        //player.getStore().getStoneCount();
    }

    public Player getWinner()throws GameNotOverException{
        int countOne=0;
        int countTwo=0;
        if(!isGameOver()){
            throw new GameNotOverException("The game has not ended yet");
        }

        try{
            countOne=getStoreCount(playerOne);
        } catch (NoSuchPlayerException ex){
            System.out.println("No such player exception");
        }
        try{
            countTwo=getStoreCount(playerTwo);
        } catch (NoSuchPlayerException ex){
            System.out.println("No such player exception");
        }
        if(countOne<countTwo){
            return playerTwo;
        }else if(countOne>countTwo){
            return playerOne;
        }else{
            return null;
        }
    }

    public boolean isGameOver(){
        final boolean oneSide= board.isSideEmpty(1);
        final boolean twoSide= board.isSideEmpty(7);
        
        if(twoSide||oneSide){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isValidMove(final int startPit){
        final Player holdCurrent=getCurrentPlayer();
        if (holdCurrent.equals(playerOne) && (startPit < 1 || startPit > 6)) {
            return false;
        }else if (holdCurrent.equals(playerTwo) && (startPit < 7 || startPit > 12)) {
            return false;
        }else if(getBoard().getDataStructure().getNumStones(startPit)==0){
            return false;
        }
        return true;
    }

    public int move(final int startPit) throws PitNotFoundException{
        if (!isValidMove(startPit)) {
            throw new PitNotFoundException("Pit is not on your side. Please try again.");
          }
        int sideTotal=0;
        try{
            if(getCurrentPlayer().equals(playerTwo)){
                board.moveStones(startPit, 2);
            }
            else{
                board.moveStones(startPit, 1);
            }
        } catch(InvalidMoveException ex){
            System.out.println("Invalid move exception");
        }
        if(getCurrentPlayer().equals(playerOne)){
            for(int i=1;i<7;i++){
                try{
                    sideTotal+=getNumStones(i);    
                }catch (PitNotFoundException ex){
                    System.out.println("Pit not found exception");
                }
            }
        }
        if(currentPlayer.equals(playerTwo)){
            for(int i=7;i<13;i++){
                try{
                    sideTotal+=getNumStones(i);    
                }catch (PitNotFoundException ex){
                    System.out.println("Pit not found exception");
                }               
            }
        }
        if(board.getCurrentPlayer()==1){
            setCurrentPlayer(playerOne);
        }else if (board.getCurrentPlayer()==2){
            setCurrentPlayer(playerTwo);
        }

        return sideTotal;
    }

    public void setBoard(final GameRules theBoard){
        this.board =theBoard;
    }

    public void setCurrentPlayer(final Player player){
        this.currentPlayer=player;
    }

    public void setPlayers(final Player onePlayer, final Player twoPlayer){
        playerOne=onePlayer;
        playerTwo=twoPlayer;
        board.registerPlayers(playerOne, playerTwo);
        currentPlayer = playerOne;
    }

    public void startNewGame(){
        board.resetBoard();
        currentPlayer=playerOne;
    }
    
    @Override
    public String toString() {
        final StringBuilder boardString= new StringBuilder();
        for(int i=1;i<7;i++){
            try{
            boardString.append("Pit ").append(i).append(": ").append(getNumStones(i)).append(" stones\n");
            }catch (PitNotFoundException e){}
        }
        boardString.append("Player 1 store: ").append(getBoard().getDataStructure().getStoreCount(1)).append(" stones\n");

        for(int i=7;i<12;i++){
            try { 
            boardString.append("Pit ").append(i+1).append(": ").append(getNumStones(i)).append(" stones\n");
        } catch (PitNotFoundException e) {
            System.out.println("Pit not found exception");
        }
        }
        boardString.append("Player 2 store: ").append(getBoard().getDataStructure().getStoreCount(2)).append(" stones\n");
        return boardString.toString();
    }
}
package mancala;
import java.io.Serializable;

public class Pit implements Countable, Serializable{

    private int stones;

    public Pit(){
        stones=0;
    }
    @Override
    public void addStone(){
        this.stones++;
    }
    @Override
    public void addStones(final int amount){
        this.stones=stones+amount;
    }
    @Override
    public int getStoneCount(){
        return stones;
    }
    @Override
    public int removeStones(){
        final int counter=stones;
        this.stones=0;
        return counter;
    }
    // @Override
    // public String toString(){
    //     return "Pit, Stones: "+stones;    
    // }
}

package mancala;

import java.io.Serializable;

public class Store implements Serializable, Countable{

    private int stones;
    private Player playerStore;

    public Store() {
        this.stones=0;
    }
    @Override
    public void addStones(final int amount){
        this.stones=stones+amount;
    }
    @Override
    public void addStone(){
        this.stones++;
    }
    @Override
    public int removeStones(){
        final int counter=this.stones;
        this.stones=0;
        return counter;
    }

    public Player getOwner(){
        return this.playerStore;
    }
    @Override
    public int getStoneCount(){
        return this.stones;
    }

    public void setOwner(final Player player){
        this.playerStore= player;
    }

    // @Override
    // public String toString(){
    //     if(playerStore!=null){
    //         return "StoreInfo: Player: "+ playerStore.getName() + ", Stones in store:" + stones;
    //     }
    //     else{
    //     return "player store does not exist";
    //     }
    //}
}

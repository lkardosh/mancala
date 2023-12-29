package mancala;

import java.io.Serializable;

public class Player implements Serializable{

    private String playerName;
    private Store playerStore;
    private int storeCount;
    private UserProfile profile;

    public Player() {
        this.playerName = "name";
        this.playerStore= new Store();
    }

    public Player(final String name) {
        this.playerName = name;
        this.playerStore= new Store();
    }
    public String getName() {
        return this.playerName;
    }
    public Store getStore() {
        return this.playerStore;
    }
    public int getStoreCount() {
        return this.storeCount;
    }
    public void setName(final String name) {
        this.playerName=name;
    }
    public void setStore(final Store store) {
        this.playerStore=store;
    }
    public void setProfile(final UserProfile aFile) {
        this.profile = aFile;
    }
    public UserProfile getProfile() {
        return this.profile;
    }

    @Override
    public String toString(){
        if(playerName!=null){
            return playerName;
        }else{
            return "No player attached to store";
        }
    }
}
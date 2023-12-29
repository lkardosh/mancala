package mancala;
import java.io.Serializable;
import java.io.IOException;

public class UserProfile implements Serializable{
    private String name;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahGamesWon;
    private int ayoGamesWon;

    public UserProfile() {
    }

    public UserProfile(final String name,final int kalahGamesPlayed,final int ayoGamesPlayed, final int kalahGamesWon, final int ayoGamesWon) {
        this.name = name;
        this.kalahGamesPlayed = kalahGamesPlayed;
        this.ayoGamesPlayed = ayoGamesPlayed;
        this.kalahGamesWon = kalahGamesWon;
        this.ayoGamesWon = ayoGamesWon;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getKalahGamesPlayed() {
        return kalahGamesPlayed;
    }

    public void setKalahGamesPlayed(final int kalahGamesPlayed) {
        this.kalahGamesPlayed = kalahGamesPlayed;
    }

    public int getAyoGamesPlayed() {
        return ayoGamesPlayed;
    }

    public void setAyoGamesPlayed(final int ayoGamesPlayed) {
        this.ayoGamesPlayed = ayoGamesPlayed;
    }

    public int getKalahGamesWon() {
        return kalahGamesWon;
    }

    public void setKalahGamesWon(final int kalahGamesWon) {
        this.kalahGamesWon = kalahGamesWon;
    }

    public int getAyoGamesWon() {
        return ayoGamesWon;
    }

    public void setAyoGamesWon(final int ayoGamesWon) {
        this.ayoGamesWon = ayoGamesWon;
    }

    public void addKalahGamesWon() {
        kalahGamesWon++;
    }

    public void addAyoGamesPlayed() {
        ayoGamesPlayed++;
    }
    public void addAyoGamesWon() {
        ayoGamesWon++;
    }
    public void addKalahGamesPlayed() {
        kalahGamesPlayed++;
    }

    public static UserProfile loadProfile(final String fileName) throws IOException, ClassNotFoundException {
        return (UserProfile) Saver.loadObject(fileName);
    }

    @Override
    public String toString() {
        return "UserProfile {" +
                "name='" + name + '\'' +
                ", kalahGamesPlayed=" + kalahGamesPlayed +
                ", ayoGamesPlayed=" + ayoGamesPlayed +
                ", kalahGamesWon=" + kalahGamesWon +
                ", ayoGamesWon=" + ayoGamesWon +
                '}';
    }

}

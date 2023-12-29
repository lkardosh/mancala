package mancala;
import java.io.*;

public class Saver {


    private static void createAssets() {
        File assetsDirectory = new File("assets/");
        if (!assetsDirectory.exists()) {
            final boolean created = assetsDirectory.mkdirs();
        }
    }

    public static void saveObject(final Serializable toSave, final String filename) throws IOException {
        createAssets();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("assets/" + filename))) {
            oos.writeObject(toSave);
        }catch (IOException e) {
        }
    }

    public static Serializable loadObject(final String filename) throws IOException, ClassNotFoundException {
        createAssets();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("assets/" + filename))) {
            return (Serializable) ois.readObject();
        }catch (IOException e) {
            return null;
        }
    }
}

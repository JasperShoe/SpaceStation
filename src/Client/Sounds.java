package Client;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Sounds {
    public void play(String name) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classLoader.getResourceAsStream("res/" + name + ".wav");
            AudioStream audio = new AudioStream(stream);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading sound file.");
        }
    }
}
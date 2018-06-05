package Client;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;

public class Sounds {
    public void play(String name) {
        try {
            AudioStream audio = new AudioStream(new FileInputStream("./src/res/" + name + ".wav"));
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading sound file.");
        }
    }
}

import java.awt.image.BufferedImage;

public class Images {
    public static BufferedImage player_front = new ImageReader().readBufferedImage("player_front");

    public static BufferedImage player_back = new ImageReader().readBufferedImage("player_back");

    public static BufferedImage player_left = new ImageReader().readBufferedImage("player_left");

    public static BufferedImage player_right = new ImageReader().readBufferedImage("player_right");
}
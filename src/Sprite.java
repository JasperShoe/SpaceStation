import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private int x, y;

    private boolean alive;

    private BufferedImage img;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        alive = true;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(img, null, x, y);
    }
}

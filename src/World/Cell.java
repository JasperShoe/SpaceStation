package World;

import java.awt.*;

/**
 * Created by student on 5/29/18.
 */
public class Cell {

    public static final int defaultWidth = 500, defaultHeight = 500;
    private Point coords;
    public Cell(int x, int y){
        coords = new Point(x, y);
    }

    public void draw(Graphics2D g2){
        g2.setColor(new Color(0,50, 200, 127));
        g2.fillRect(coords.x, coords.y, defaultWidth, defaultHeight);
        g2.setColor(Color.gray);
        g2.drawRect(coords.x, coords.y, defaultWidth, defaultHeight);
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }
}

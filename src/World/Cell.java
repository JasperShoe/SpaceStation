package World;

import Client.Images;

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
        g2.drawImage(Images.list.get("floor_tiled"), null, coords.x, coords.y);
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }
}

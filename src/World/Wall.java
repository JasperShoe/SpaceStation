package World;

import java.awt.*;

/**
 * Created by student on 5/29/18.
 */
public class Wall {

    private Point a, b;
    //private Cell adjacent;
    public Wall(Point a, Point b/*, Cell adjacent*/){
        this.a = a;
        this.b = b;
        //this.adjacent = adjacent;
    }

    public void draw(Graphics2D g2){
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(a.x, a.y, b.x, b.y);
        g2.setStroke(stroke);
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }
}

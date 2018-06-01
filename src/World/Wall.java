package World;
import Character.*;

import java.awt.*;

/**
 * Created by student on 5/29/18.
 */
public class Wall extends Sprite{

    private Point a, b;
    private int orientation;
    public static final int HORIZONTAL = 0, VERTICAL = 1;
    private boolean doorOpen;
    private Door door;
    //private Cell adjacent;
    public Wall(Point a, Point b, boolean hasDoor, Floor parent){
        super((a.x<b.x)?a.x:b.x, (a.y<b.y)?a.y:b.y, (b.x-a.x != 0)?Math.abs(b.x - a.x):10, (b.y-a.y != 0)?Math.abs(b.y - a.y):10);
        this.a = a;
        this.b = b;
        doorOpen = true;
        orientation = (a.x - b.x != 0)?HORIZONTAL:VERTICAL;
        if(hasDoor){
        Rectangle doorBuild = (Rectangle)getBoundingRectangle().clone();
        if(orientation == HORIZONTAL) {
            doorBuild.x += doorBuild.width * 2 / 5;
            doorBuild.width /= 5;
        }
        else{
            doorBuild.y += doorBuild.height * 2/5;
            doorBuild.height /= 5;
        }
        door = new Door(doorBuild, parent);
        }
        else{
            door = null;
        }
    }

    public void draw(Graphics2D g2){ //update with images
        Stroke stroke = g2.getStroke();
        g2.setColor(Color.red);
//        g2.setStroke(new BasicStroke(10));
//        g2.drawLine(a.x, a.y, b.x, b.y);
//        g2.setStroke(stroke);
        g2.fill(getBoundingRectangle());
        g2.setColor(Color.green);

        if(door!=null) {
            door.draw(g2);
        }
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public void collide(Sprite other){
        if(door==null||!door.isOpen() || !door.intersects(other.getBoundingRectangle())){
            String blocked;
            if(orientation == HORIZONTAL){
                blocked = (other.getCenter().y < a.y)?"Down":"Up";
            }
            else{
                blocked = (other.getCenter().x < a.x)?"Right":"Left";
            }
            other.getDirections().put(blocked, false);
        }

    }
}

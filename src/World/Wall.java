package World;
import Characters.*;
import Client.Images;

import java.awt.*;

public class Wall extends Sprite {

    private Point a, b;
    private int orientation;
    public static final int HORIZONTAL = 0, VERTICAL = 1;
    private boolean hasDoor;
    private Door door;
    //private Cell adjacent;
    public Wall(Point a, Point b, boolean hasDoor, Floor parent) {
        super((a.x<b.x)?a.x:b.x, (a.y<b.y)?a.y:b.y, (b.x-a.x != 0)?Math.abs(b.x - a.x):10, (b.y-a.y != 0)?Math.abs(b.y - a.y):10);
        this.a = a;
        this.b = b;
        orientation = (a.x - b.x != 0)?HORIZONTAL:VERTICAL;
        this.hasDoor = hasDoor;
        if(hasDoor){
        Rectangle doorBuild = (Rectangle)getBoundingRectangle().clone();
        if(orientation == HORIZONTAL) {
            doorBuild.x += doorBuild.width * 2 / 5;
            doorBuild.width /= 5;
        }
        else{
            doorBuild.y += doorBuild.height * 2 / 5;
            doorBuild.height /= 5;
        }
        door = new Door(doorBuild, parent);
        }
        else{
            door = null;
        }
    }

    public void draw(Graphics2D g2){ //update with images
        if(Math.abs(getA().x - getB().x) > 10 || Math.abs(getA().y - getB().y) > 10) {
            if (orientation == HORIZONTAL) {
                if(hasDoor) {
                    g2.drawImage(Images.list.get("wall_front"), null, getX(), getY() - Images.list.get("wall_front").getHeight() + 10);
                } else {
                    g2.drawImage(Images.list.get("wall_external_front"), null, getX(), getY() - Images.list.get("wall_external_front").getHeight() + 10);
                }
            } else {
                if(hasDoor) {
                    g2.drawImage(Images.list.get("wall_side"), null, getX() - (Images.list.get("wall_side").getWidth() - 10) / 2, getY() - Images.list.get("wall_front").getHeight() + 10);
                } else {
                    g2.drawImage(Images.list.get("wall_external_side"), null, getX() - (Images.list.get("wall_external_side").getWidth() - 10) / 2, getY() - Images.list.get("wall_front").getHeight() + 10);
                }
            }
        }

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
        if(door==null || !door.intersects(other.getBoundingRectangle())){
            String blocked;
            if(orientation == HORIZONTAL){
                blocked = (other.getCenter().y < a.y)?"Down":"Up";
            }
            else{
                blocked = (other.getCenter().x < a.x)?"Right":"Left";
            }
            other.getDirections().put(blocked, false);
        } else {
            door.setOpen(true);
        }
    }

    public int getOrientation() {
        return orientation;
    }
}

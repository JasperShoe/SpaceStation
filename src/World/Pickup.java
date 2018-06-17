package World;

import Characters.Sprite;

import java.awt.*;

/**
 * Created by student on 6/15/18.
 */
public class Pickup extends Sprite {

    private Gun pickup;
    private Floor floor;
    public Pickup(Gun pickup, Floor floor){
        super(0, 0, pickup.getW(), pickup.getH());
        setImg(pickup.getImg());
        this.pickup = pickup;
        this.floor = floor;
    }

    public void init(int x, int y, Floor f){
        setX(x);
        setY(y);
        floor = f;
        floor.addPickup(this);
    }

    public void draw(Graphics2D g2){
        super.draw(g2);
        g2.setColor(Color.green);
        g2.draw(getBoundingRectangle());
    }

    public Gun getPickup() {
        return pickup;
    }

    public void setPickup(Gun pickup) {
        this.pickup = pickup;
    }
}

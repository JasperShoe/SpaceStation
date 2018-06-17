package World;

import Characters.Sprite;

import java.awt.*;

/**
 * Created by student on 6/16/18.
 */
public class Explosion extends Sprite{

    private int damage, range, radius;
    public Explosion(int damage, int range){
        super(0,0,0,0);
        this.damage = damage;
        this.range = range;
    }

    public void init(int x, int y, Floor floor){
        setX(x);
        setY(y);
        floor.addExplosion(this);
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.blue);
        setX(getX()-1);
        setY(getY()-1);
        setDimensions(getX(), getY(), getW() + 2, getH() + 2);
        g2.draw(getBoundingRectangle());
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }
}

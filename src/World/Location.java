package World;

import Characters.Player;
import Characters.Sprite;
import Client.Images;
import Client.Main;

import java.awt.*;

/**
 * Created by student on 6/19/18.
 */
public abstract class Location extends Sprite {

    private boolean used;
    public Location(int x, int y, String image){
        super(x, y, 0, 0);
        setImg(Images.list.get("loc_" + image));

    }

    public void init(Floor floor){
        floor.addWall(new Point(getX(), getY()), new Point(getW(),0), false, false);
        floor.addWall(new Point(getX(), getY()), new Point(0, getH()), false, false);
        floor.addWall(new Point(getX() + getW(), getY()), new Point(0, getH()), false, false);
        floor.addWall(new Point(getX(), getY() + getH()), new Point(getW(), 0), false, false);
    }

    public abstract void interact(Player player);

    public abstract Location clone();

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}

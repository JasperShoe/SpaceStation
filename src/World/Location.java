package World;

import Characters.Player;
import Characters.Sprite;
import Client.Images;

/**
 * Created by student on 6/19/18.
 */
public abstract class Location extends Sprite {

    private boolean used;
    public Location(int x, int y, String image){
        super(x, y, 0, 0);
        setImg(Images.list.get("loc_" + image));

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

package World;

import Characters.Player;
import Client.Images;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by student on 6/20/18.
 */
public class Chest extends Location {

    private ArrayList<Gun> drops;
    public Chest(int x, int y, int num_drops){
        super(x, y, "chest");
        drops = new ArrayList<>();
        for (int i = 0; i < num_drops; i++) {
            drops.add(Gun.getRandom());
        }
    }
    @Override
    public void interact(Player player) {
        System.out.println("interacting");
        double a = Math.toRadians(360 / drops.size());
        int i = 0;
        for(Gun g : drops){
            Pickup p = new Pickup(g, player.getFloor());
            p.init(getX() + (int)(80 * Math.cos(i*a)), getY() + (int) (80 * Math.sin(i*a)), player.getFloor());
            i++;
        }
        setUsed(true);
        setImg(Images.list.get("open"));
    }

    @Override
    public Location clone() {
        return new Chest(getX(), getY(), drops.size());
    }

}

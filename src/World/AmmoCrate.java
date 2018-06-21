package World;

import Characters.Player;
import Client.Images;

/**
 * Created by student on 6/20/18.
 */
public class AmmoCrate extends Location {

    public AmmoCrate(int x, int y){
        super(x, y, "ammo");
    }

    @Override
    public void interact(Player player) {
        for(Gun g : player.getInventory()){
            g.refillMag();
        }
        setImg(Images.list.get("loc_open"));
    }

    @Override
    public Location clone() {
        return null;
    }
}

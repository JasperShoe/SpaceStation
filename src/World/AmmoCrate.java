package World;

import Characters.Player;

/**
 * Created by student on 6/20/18.
 */
public class AmmoCrate extends Location {

    public AmmoCrate(int x, int y){
        super(x, y, "ammo");
    }

    @Override
    public void interact(Player player) {

    }

    @Override
    public Location clone() {
        return null;
    }
}

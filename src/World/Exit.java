package World;

import Characters.Player;
import Client.Main;

/**
 * Created by student on 6/20/18.
 */
public class Exit extends Location {

    public Exit(int x, int y){
        super(x, y, "elevator_opened");
    }

    @Override
    public Location clone() {
        return new Exit(getX(), getY());
    }

    @Override
    public void interact(Player player) {
        player.resetPosition();
        player.addXP(100);
        player.setHealth(player.getMaxHealth());
        Main.graphics.setFloor(player.getFloor().getTier() + 1);
    }
}

package Characters;

import javax.swing.*;
import java.awt.*;

/**
 * Created by student on 6/11/18.
 */
public interface AI {

    Point setGoal(Rectangle bounds);
    void customize(Enemy enemy);
    void move(Character moving, Player player);
    Timer attack(Character attacker);

}

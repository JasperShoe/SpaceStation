package World;
import Character.*;

import java.awt.*;

/**
 * Created by student on 5/31/18.
 */
public class Door extends Sprite {
    private boolean open;
    private int orientation;
    public Door(Rectangle rect, Floor parent){
        super(rect.x, rect.y, rect.width, rect.height);
        open = true;
        orientation = (rect.width > rect.height)?Wall.HORIZONTAL:Wall.VERTICAL;

        if(orientation == Wall.HORIZONTAL) {
            parent.addWall(new Point(rect.x, rect.y), new Point(0, rect.height), false);
            parent.addWall(new Point(rect.x + rect.width, rect.y), new Point(0, rect.height), false);
        }
        else{
            parent.addWall(new Point(rect.x, rect.y), new Point(rect.width, 0), false);
            parent.addWall(new Point(rect.x, rect.y + rect.height), new Point(rect.width, 0), false);
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void draw(Graphics2D g2){
        g2.draw(getBoundingRectangle());
    }
}

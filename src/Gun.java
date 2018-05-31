import java.awt.*;

/**
 * Created by student on 5/31/18.
 */

public class Gun extends Sprite{

    private int gunIndex, bulletSpeed, bulletDamage, bulletRange, fireRate;

    public Gun(int gunIndex, int x, int y, int w, int h){

        super(x, y, w, h);
        
        this.gunIndex = gunIndex;

        setGunStats(gunIndex);

    }

    public void update(Player player, Graphics2D g2, Point mouseCoords) {

        move(player.getX(), player.getY());

    }

    public void setGunStats(int index){

        if(index ==  0){
            //mp5

            bulletSpeed = 5;
            bulletDamage = 5;
            bulletRange = 5;
            fireRate = 5;

        }

    }

    public void gunStatChange(int bsChange, int bdChange, int brChange, int frChange){

        bulletSpeed += bsChange;
        bulletDamage += bdChange;
        bulletRange += brChange;
        fireRate += frChange;

    }

}

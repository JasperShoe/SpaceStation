package World;

import Characters.*;
import Client.*;

/**
 * Created by student on 6/2/18.
 */
public class Bullet extends Sprite {

    private Gun source;
    private int vx, vy;
    private int[] start = new int[2];
    private int idx;
    private boolean expired;
    public Bullet(int x, int y, int vx, int vy, String imageName, Gun source){
        super(x, y, 0, 0);
        setImg(Images.list.get(imageName));
        this.source = source;
        this.vx = vx;
        this.vy = vy;
        start[0] = x;
        start[1] = y;
        setRotation(source.getRotation() + 90);
    }

    public int getDamage(){
        return source.getBulletDamage();
    }

    public int getRange(){
        return source.getBulletRange();
    }

    public int getSpeed(){
        return source.getSpeed();
    }

    public void update(){
        translate(vx, vy);
        if(Math.sqrt(Math.pow(getX() - start[0], 2) + Math.pow(getY() - start[1], 2)) >= getRange() || directionCount() < 4){
            expired = true;
        }
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int[] getStart() {
        return start;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void collide(Characters.Character other){
        if(!(other.getClass().equals(source.getOwner().getClass()))){
            other.damage(getDamage());
        }
    }



//    public void draw(Graphics2D g2){
//        super.draw(g2);
////        g2.draw(getBoundingRectangle());
//    }
}

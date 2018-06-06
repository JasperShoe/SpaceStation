package Character;

import Client.GraphicsPanel;
import Client.Images;
import World.Floor;
import World.Gun;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Player extends Sprite implements KeyListener, MouseListener{

    private boolean moveLeft, moveRight, moveUp, moveDown;

    private int direction;
    //0 is left
    //1 is right
    //2 is up
    //3 is down

    private Gun equipped;
    private ArrayList<Gun> inventory;
    int inv_index;

    Floor floor;

    public Player(int x, int y, int w, int h, Floor floor) {
        super(x, y, w, h);
        this.floor = floor;
        inv_index = 0;
        inventory = new ArrayList<>();
        inventory.add(Gun.list.get("uzi"));
        inventory.add(Gun.list.get("mp5"));
        inventory.add(Gun.list.get("laser"));
        equipGun(inventory.get(inv_index));


    }


    public void update(Graphics2D g2, Point mouse) {
        Point playerCenter = new Point(getX() + getTransX() + getW()/2, getY()+getTransY() + getH()/2);
        if (moveUp) {
//            setImg(Images.player_back);
            move(2);
           // System.out.println("Mouse: " + mouse + "; Character.Player: " + new Point(getX() + getTransX(), getY() + getTransY()));
        }
        if (moveDown) {
//            setImg(Images.player_front);
            move(3);
        }
        if (moveLeft) {

            move(0);
        } if (moveRight) {
//            setImg(Images.player_right);
            move(1);
        }

        if(mouse != null) {
            double mouseAngle = Math.toDegrees(Math.atan2(mouse.y - playerCenter.y, mouse.x - playerCenter.x));
            if (mouseAngle > -105 && mouseAngle < -75) {
                setImg(Images.list.get("player_back"));
            } else if (mouseAngle < 105 && mouseAngle > 75) {
                setImg(Images.list.get("player_front"));
            } else {
                if (Math.abs(mouseAngle) < 90) {
                    setImg(Images.list.get("player_right"));
                } else {
                    setImg(Images.list.get("player_left"));
                }
            }
            equipped.update(this, mouseAngle);
            if(equipped.isForeground()){
                draw(g2);
            }
            equipped.draw(g2);
        }
        super.update();

        //draw(g2);
    }

    public void switch_gun(){
        inv_index = (inv_index == inventory.size() - 1)?0:inv_index+1;
        equipped.stop();
        equipGun(inventory.get(inv_index));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A) {
            moveLeft = true;
        } else if (keyCode == KeyEvent.VK_D) {
            moveRight = true;
        } else if (keyCode == KeyEvent.VK_W) {
            moveUp = true;
        } else if (keyCode == KeyEvent.VK_S) {
            moveDown = true;
        }
        else if(keyCode == KeyEvent.VK_Q){
            switch_gun();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A) {

            moveLeft = false;

        } else if (keyCode == KeyEvent.VK_D) {

            moveRight = false;

        } else if (keyCode == KeyEvent.VK_W) {

            moveUp = false;

        } else if (keyCode == KeyEvent.VK_S) {

            moveDown = false;

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void translate(int dx, int dy){
        super.translate(dx, dy);
        setTransX(getTransX() + -dx);
        setTransY(getTransY() + -dy);
    }

    public void equipGun(Gun gun){
        equipped = gun;
        equipped.setEnvironment(floor);
    }

    public Gun getEquipped(){
        return equipped;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        equipped.fire();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        equipped.stop();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
package Characters;

import Client.GraphicsPanel;
import Client.Images;
import World.Cell;
import World.Floor;
import World.Gun;
import World.Pickup;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Player extends Character implements KeyListener, MouseListener{

    private boolean moveLeft, moveRight, moveUp, moveDown;

    private int direction;
    //0 is left
    //1 is right
    //2 is up
    //3 is down

    private ArrayList<Gun> inventory;
    int inv_index;


    public Player(int x, int y, Floor floor) {
        super(x, y, 100, 2, floor);
        inv_index = 0;
        inventory = new ArrayList<>();
        inventory.add(Gun.get("pistol"));
//        inventory.add(Gun.get("uzi"));
//        inventory.add(Gun.get("mp5"));
//        inventory.add(Gun.get("laser"));
//      inventory.add(Gun.get("sniper"));
//        inventory.add(Gun.get("p90"));
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
            double mouseAngle = GraphicsPanel.getAngle(playerCenter, mouse);
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
            getEquipped().update(this, mouseAngle);
            if(getEquipped().isForeground()){
                draw(g2);
            }
            getEquipped().draw(g2);
        }
        super.update();

        //draw(g2);
    }

    public void switch_gun(){
        inv_index = (inv_index == inventory.size() - 1)?0:inv_index+1;
        getEquipped().stop();
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
        } else if(keyCode == KeyEvent.VK_R){
            getEquipped().reload();
        }
        else if(keyCode == KeyEvent.VK_SPACE){
            for(int i = getFloor().getPickups().size() - 1; i >= 0; i--){
                Pickup pickup = getFloor().getPickups().get(i);
                if(pickup.getBoundingRectangle().intersects(GraphicsPanel.cursor)){
                    inventory.add(pickup.getPickup());
                    getFloor().removePickup(pickup);
                }
            }
        }
        else if(keyCode==KeyEvent.VK_I){
            getFloor().getParent().getGui().toggleInv();
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

    public void addInventory(int i, Gun gun){
        inventory.add(i, gun);
    }

    public void removeInventory(int i){
        inventory.remove(i);
    }

    public ArrayList<Gun> getInventory(){
        return inventory;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        getEquipped().fire();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        getEquipped().stop();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void kill() {
        setHealth(getMaxHealth());
    }
}
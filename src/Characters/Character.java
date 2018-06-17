package Characters;

import World.Floor;
import World.Gun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by student on 6/12/18.
 */
public abstract class Character extends Sprite {

    private int health, maxHealth;

    private Gun equipped;

    private Floor floor;

    private boolean immune;

    private Timer immunity;

    public Character(int x, int y, int health, int speed, Floor floor) {
        super(x, y, 32, 32);
        this.health = health;
        this.maxHealth = health;
        this.floor = floor;
        setSpeed(speed);

        immunity = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                immune = false;
                immunity.stop();
            }
        });
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
        if(health > maxHealth){
            setMaxHealth(health);
        }
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        if(health > maxHealth){
            health = maxHealth;
        }
    }

    public void damage(int damage){
        if(!immune) {
            health -= damage;
            if (!(health > 0)) {
                health = 0;
                kill();
            }
            setImmune();
        }
    }

    public abstract void kill();

    public void equipGun(Gun gun){
        equipped = gun;
        equipped.setEnvironment(floor);
        equipped.setOwner(this);
    }

    public Gun getEquipped(){
        return equipped;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(Color.BLACK);
        g2.drawRect(getX(), getY() - 20, getW(), 5);
        g2.setColor(Color.red);
        g2.fillRect(getX(), getY() - 20, getW(), 5);
        g2.setColor(Color.green);
        g2.fillRect(getX(), getY() - 20, getW() * health / maxHealth, 5);
    }

    public Floor getFloor(){
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public boolean isImmune() {
        return immune;
    }

    public void setImmune() {
        immune = true;
        immunity.start();
    }

    public void update(Graphics2D g2, double angle){
        if(getEquipped()!= null) {
            getEquipped().update(this, Math.toDegrees(angle));
            if (getEquipped().isForeground()) {
                draw(g2);
            }
            getEquipped().draw(g2);
        }

        super.update();
    }
}

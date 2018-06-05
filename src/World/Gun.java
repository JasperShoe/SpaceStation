package World;

import Client.*;
import Character.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by student on 5/31/18.
 */

public class Gun extends Sprite{

    public static HashMap<String, Gun> list;
    static {
        list = new HashMap<>();
        list.put("mp5", new Gun("mp5", 5, 10, 500, 3));
    }

    private int bulletSpeed, bulletDamage, bulletRange, fireRate;

    private boolean foreground, automatic;

    private String name;

    private Timer rounds;

    private Floor environment;

    public Gun(String name, int damage, int speed, int range, int fireRate){

        super(0, 0, 0, 0); //image location set when gun needs to be drawn
        setImg(Images.list.get(name));
        this.name = name;
        this.bulletDamage = damage;
        this.bulletSpeed = speed;
        this.bulletRange = range;
        automatic = !(fireRate == 0);
        this.fireRate = (fireRate == 0)?1:fireRate;

        foreground = true;

        rounds = new Timer(1500/fireRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });
    }

    public void update(Player player, double mouseAngle) {

        translate(player.getX() - getX(), player.getY() - getY() + 2);
        if(mouseAngle > -75 && mouseAngle < 105){
            foreground = true;
            setHorizFlip(false);
        }
        else{
            foreground = false;
            setHorizFlip(true);
        }

        setRotation(mouseAngle);
    }


    public boolean isForeground() {
        return foreground;
    }

    public void setForeground(boolean foreground) {
        this.foreground = foreground;
    }

    public void setEnvironment(Floor floor){
        environment = floor;
    }

    public Floor getEnvironment() {
        return environment;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public int getBulletRange() {
        return bulletRange;
    }

    public void setBulletRange(int bulletRange) {
        this.bulletRange = bulletRange;
    }

    public void fire(){
        shoot();
        if(automatic){
            rounds.start();
        }
    }

    public void stop(){
        rounds.stop();
    }

    public void shoot(){
        Gun source = this;
        Point bulletSource = new Point((int)(getCenter().x + getW() / 2 * Math.cos(Math.toRadians(getRotation()))), (int)(getCenter().y + getW() / 2 * Math.sin(Math.toRadians(getRotation()))));
        int vx = (int)(Math.cos(Math.toRadians(getRotation())) * bulletSpeed);
        int vy = (int)(Math.sin(Math.toRadians(getRotation())) * bulletSpeed);
        Bullet bullet = new Bullet(bulletSource.x, bulletSource.y, vx, vy, "red_bullet", source);
        int idx = environment.addBullet(bullet);
        bullet.setIdx(idx);
    }
}

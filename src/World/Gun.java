package World;

import Characters.*;

import Characters.Character;
import Client.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by student on 5/31/18.
 */

public class Gun extends Sprite {

    public static HashMap<String, Gun> list;
    static {
        list = new HashMap<>();
        list.put("pistol", new Gun("pistol", "bullet_yellow", 200, 20, 500, 60, 800, Integer.MAX_VALUE, 3));
        list.put("mp5", new Gun("mp5", "bullet_red", 5, 10, 500, 5, 20, 300, 4));
        list.put("uzi", new Gun("uzi", "bullet_yellow", 3, 10, 350, 15, 50, 350, 2));
        list.put("laser", new Gun("laser", "bullet_purple", 10, 10, 800, 2, 6, 50, 2));
        list.put("sniper", new Gun("sniper", "bullet_yellow", 15, 15, 1000, 1, 3, 25, 1));
        list.put("p90", new Gun("p90", "bullet_red", 7, 10, 500, 0, 20, 350, 2));
    }

    private int bulletSpeed, bulletDamage, bulletRange, fireRate, clipSize, clip, reloadTime, magazine, fullMag;

    private boolean foreground, automatic, delayed, stopped;

    private String name, bullet_image;

    private Timer rounds, reloadDelay;

    private Floor environment;

    private Character owner;

    public Gun(String name, String bullet_image, int damage, int speed, int range, int fireRate, int clipSize, int mag, int reloadSpeed){

        super(0, 0, 0, 0); //image location set when gun needs to be drawn
        setImg(Images.list.get("gun_" + name));
        this.name = name;
        this.bullet_image = bullet_image;
        this.bulletDamage = damage;
        this.bulletSpeed = speed;
        this.bulletRange = range;
        this.clipSize = clipSize;
        this.clip = clipSize;
        this.magazine = mag;
        this.fullMag = magazine;
        this.reloadTime = 6000/reloadSpeed;
        this.fireRate = fireRate;
        automatic = !(fireRate == 0);
        fireRate = (fireRate == 0)?1:fireRate;

        foreground = true;

        rounds = new Timer(1500/fireRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!reloadDelay.isRunning()){
                    delayed = false;
                }

                if(automatic && !stopped){
                    shoot();
                }
                if(stopped){
                    rounds.stop();
                }
            }
        });

        reloadDelay = new Timer(reloadTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delayed = false;
                magazine -= clipSize - clip;
                clip = clipSize;
                if(!stopped){
                    fire();
                }
                reloadDelay.stop();
            }
        });
    }

    public void update(Character character, double mouseAngle) {

        translate(2 + character.getX() - getX(), 7 + character.getY() - getY());
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

    public String getName() {
        return name;
    }

    public void fire(){
        if(!delayed) {
            stopped = false;
            delayed = true;
            shoot();
            rounds.start();
        }
        else if(!automatic && !reloadDelay.isRunning()){
            shoot();
        }
    }

    public void stop(){
        stopped = true;
    }

    public void shoot(){
        if(magazine > 0) {
            Gun source = this;
            Point bulletSource = new Point((int) (getCenter().x + getW() / 2 * Math.cos(Math.toRadians(getRotation()))), (int) (getCenter().y + getW() / 2 * Math.sin(Math.toRadians(getRotation()))));
            int vx = (int) (Math.cos(Math.toRadians(getRotation())) * bulletSpeed);
            int vy = (int) (Math.sin(Math.toRadians(getRotation())) * bulletSpeed);
            Bullet bullet = new Bullet(bulletSource.x, bulletSource.y, vx, vy, bullet_image, source);
            int idx = environment.addBullet(bullet);
            bullet.setIdx(idx);
            clip--;
            if (clip <= 0) {
                reload();
            }
        }
        else{
            magazine = 0;
            clip = 0;
        }
    }

    public void reload(){
        rounds.stop();
        delayed = true;
        reloadDelay.start();
    }

    public Character getOwner() {
        return owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }

    public static Gun get(String name){
        Gun model = Gun.list.get(name);
        return new Gun(model.name, model.bullet_image, model.bulletDamage, model.bulletSpeed, model.bulletRange, model.fireRate, model.clipSize, model.fullMag, 6000/model.reloadTime);
    }

    public static Gun getRandom(){
        String s = (String)(list.keySet().toArray()[(int)(Math.random() * list.keySet().size())]);
        return get(s);
    }

    public int getClipSize(){
        return clipSize;
    }

    public int getClip(){
        return clip;
    }

    public int getReloadTime(){
        return reloadTime;
    }

    public Timer getReloadDelay(){
        return reloadDelay;
    }

    private void setDelay(boolean delay, String loc){
        this.delayed = delay;
        System.out.println(loc);
    }

    public int getMagazine() {
        return magazine;
    }

    public String getMagazineText(){
        return "" + ((fullMag == Integer.MAX_VALUE)?"∞":magazine);
    }
}

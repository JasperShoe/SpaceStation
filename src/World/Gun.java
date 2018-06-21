package World;

import Characters.*;

import Characters.Character;
import Client.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by student on 5/31/18.
 */

public class Gun extends Sprite {

    public static HashMap<String, Gun> list;
    public static Gun op = new Gun("pistol", "bullet_yellow", 200, 20, 1000, 60, 1000, Integer.MAX_VALUE, 3, Integer.MAX_VALUE);
    static {
        list = new HashMap<>();

        list.put("pistol", new Gun("pistol", "bullet_yellow", 200, 20, 1000, 60, 1000, Integer.MAX_VALUE, 3, Integer.MAX_VALUE));
        list.put("mp5", new Gun("mp5", "bullet_red", 5, 10, 500, 5, 20, 150, 4, 1));
        list.put("uzi", new Gun("uzi", "bullet_yellow", 3, 10, 350, 15, 50, 200, 2, 1));
        list.put("laser", new Gun("laser", "bullet_blue", 10, 10, 800, 2, 6, 30, 2, Integer.MAX_VALUE));
        list.put("sniper", new Gun("sniper", "bullet_yellow", 15, 15, 1000, 1, 3, 5, 1, 5));
        list.put("p90", new Gun("p90", "bullet_red", 7, 10, 500, 0, 20, 120, 2, 1));
        list.put("lmg", new Gun("lmg", "bullet_green", 7, 10, 800, 13, 80, 320, 1, 3));
    }

    private int bulletSpeed, bulletDamage, bulletRange, fireRate, clipSize, clip, reloadTime, magazine, fullMag, penetration;

    private boolean foreground, automatic, delayed, stopped;

    private String name, bullet_name;

    private BufferedImage bullet_image;

    private Timer rounds, reloadDelay;

    private Floor environment;

    private Character owner;

    private Gun baseline;

    public Gun(String name, String bullet_image, int damage, int speed, int range, int fireRate, int clipSize, int mag, double reloadSpeed, int penetration){

        super(0, 0, 0, 0); //image location set when gun needs to be drawn
        setImg(Images.list.get("gun_" + name));
        this.name = name;
        this.bullet_name = bullet_image;
        this.bullet_image = Images.list.get(bullet_image);
        this.bulletDamage = damage;
        this.bulletSpeed = speed;
        this.bulletRange = range;
        this.clipSize = clipSize;
        this.clip = clipSize;
        this.penetration = penetration;
        this.magazine = mag;
        this.fullMag = magazine;
        this.reloadTime = (int)(6000/reloadSpeed);
        this.fireRate = fireRate;
        automatic = !(fireRate == 0);
        fireRate = (fireRate == 0)?1:fireRate;

        baseline = this;

        foreground = true;

        rounds = new Timer(1500/fireRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!reloadDelay.isRunning()) {
                    delayed = false;

                    if (automatic && !stopped) {
                        shoot();
                    }
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
                if(magazine < 0){
                    clip += magazine;
                    magazine = 0;
                }
                if(clip <= 0){
                    clip = 0;
                    magazine = 0;
                }

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
        if(clip>0){
            Gun source = this;
            int sign = (Math.abs(getRotation()) > 120 || getRotation() < -30)?1:-1;
            Point bulletSource = new Point((int) (getCenter().x + getW() / 2 * Math.cos(Math.toRadians(getRotation()))
            +sign* Math.sin(Math.toRadians(getRotation())) * bullet_image.getHeight()/2),
                    (int) (getCenter().y + getW() / 2 * Math.sin(Math.toRadians(getRotation()))
            +sign* Math.cos(Math.toRadians(getRotation())) * bullet_image.getHeight()/2));

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
        return model.clone();
    }

    public Gun clone(){
        return new Gun(name, bullet_name, bulletDamage, bulletSpeed, bulletRange, fireRate, clipSize, fullMag, 6000/reloadTime, penetration);
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

    public void addToMag(int ammo){
        int toFull = fullMag - magazine;
        magazine+= (ammo > toFull)?toFull:ammo;
    }

    public void refillMag(){
        magazine = fullMag;
    }

    public String getMagazineText(){
        return "" + ((fullMag == Integer.MAX_VALUE)?"∞":magazine);
    }

    public int getPenetration() {
        return penetration;
    }

    public void setBaseline(Gun g){
        baseline = g;
    }

    public void applyBuffs(Character c){
        bulletDamage = baseline.bulletDamage + c.getDamageBoost();
        bulletSpeed = baseline.bulletSpeed + c.getbSpeedBoost();
        reloadTime = (int)(6000/((double)(6000/baseline.reloadTime) + c.getReloadBoost()));
        fireRate = baseline.fireRate + c.getFireRateBoost();
        clipSize = baseline.clipSize + c.getClipBoost() * baseline.clipSize/10;
        if(fullMag!=Integer.MAX_VALUE)
            fullMag = baseline.fullMag + c.getMagBoost() * baseline.fullMag / 10;
    }
}

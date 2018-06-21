package Characters;

import Client.GraphicsPanel;
import Client.Images;
import Client.Main;
import World.Explosion;
import World.Floor;
import World.Gun;
import World.Pickup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Enemy extends Character {

    private AI controller;
    private Timer attack;
    private ArrayList<Pickup> pickups;

    public static HashMap<String, Enemy> list;
    static{
        list = new HashMap<>();
        list.put("pursuing_gunner",
                new Enemy(32, 2, Gun.get("uzi"), new AI() {
                    @Override
                    public Point setGoal(Rectangle bounds) {
                        return null;
                    }

                    @Override
                    public void move(Character moving, Player player) {
                        moveToPlayer(moving, player);
                    }

                    @Override
                    public Timer attack(Character attacker) {

                        return new Timer(2000, new ActionListener() {
                            int count = 1;
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(count%3 == 0){
                                    attacker.getEquipped().fire();
                                }
                                else{
                                    attacker.getEquipped().stop();
                                }
                                count++;
                            }
                        });

                    }

                    @Override
                    public void customize(Enemy enemy) {
                        double dropChance = .05;
                        if(Math.random() < .1){
                            enemy.equipGun(Gun.getRandom());
                            dropChance = 1;
                        }
                        if(Math.random() < dropChance) {
                            enemy.addPickup(new Pickup(enemy.getEquipped(), enemy.getFloor()));
                        }
                    }
                })
                );

        list.put("sniper", new Enemy(50, 2, Gun.get("sniper"), new AI() {
            @Override
            public Point setGoal(Rectangle bounds) {
                return null;
            }

            @Override
            public void move(Character moving, Player player) {
                double relAngle = Math.toRadians(GraphicsPanel.getAngle(moving.getCenter(), player.getCenter()));
                if(moving.getDistance(player) < 240){

                    moving.translate((int) (-Math.cos(relAngle) * moving.getSpeed()), (int) (-Math.sin(relAngle) * moving.getSpeed()));
                }
                else if(moving.getDistance(player) > moving.getEquipped().getBulletRange()){
                    moving.translate((int)(Math.cos(relAngle)*moving.getSpeed()), (int)(Math.sin(relAngle)*moving.getSpeed()));
                }
            }

            @Override
            public Timer attack(Character attacker) {
                int delay = 1000 * (2 + (int)(Math.random() * 3));
                return new Timer(delay, new ActionListener() {
                    boolean firing = false;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!firing){
                            attacker.getEquipped().fire();
                            firing = true;
                        }

                    }
                });
            }

            @Override
            public void customize(Enemy enemy) {
                if(Math.random() < .1){
                    enemy.addPickup(new Pickup(enemy.getEquipped(), enemy.getFloor()));
                }
            }
        }));

        list.put("psycho", new Enemy(40, 3, null, new AI() {
            @Override
            public Point setGoal(Rectangle bounds) {
                return null;
            }

            @Override
            public void customize(Enemy enemy) {

            }

            @Override
            public void move(Character moving, Player player) {
                moveToPlayer(moving, player);
                if(moving.getDistance(player) < 80){
                    moving.setSpeed(6);
                    if(moving.getDistance(player) < 35) {
                        Explosion explosion = new Explosion(15, 120);
                        explosion.init(moving.getX(), moving.getY(), player.getFloor());
                        moving.kill();
                    }
                }
            }

            @Override
            public Timer attack(Character attacker) {
                return null;
            }
        }));
    }

    public Enemy(int health, int speed, Gun gun, AI controller){
        super(0, 0, health, speed, null);
        setImg(Images.list.get("enemy_front"));
        pickups = new ArrayList<>();
        this.controller = controller;

        if(gun!=null){
            equipGun(gun);
        }


        attack = controller.attack(this);
        controller.customize(this);

    }

    public void init(int x, int y, Floor floor){
        setX(x);
        setY(y);
        setFloor(floor);
        if(getEquipped()!= null){
            equipGun(getEquipped());
        }
        getFloor().addEnemy(this);
        if(attack!=null){
            attack.start();
        }
    }

    public void update(Graphics2D g2, Player player, double angle){
        controller.move(this, player);
        if(attack!=null){
            if(player.getDistance(this) > (getEquipped().getBulletRange() + 20)){
                stopAttack();
            }
            else if(!attack.isRunning()){
                startAttack();
            }
        }
        if(getEquipped() != null && getEquipped().getMagazine() <= 0) {
            getEquipped().refillMag();
            if(attack!=null){
                startAttack();
            }
        }
        super.update(g2, angle);
    }

    public double getPlayerAngle(Player player){
        return Math.toRadians(GraphicsPanel.getAngle(getCenter(), player.getCenter()));
    }

    public AI getController(){
        return controller;
    }

    @Override
    public void kill() {
        stopAttack();
        for (int i = 0; i < pickups.size(); i++) {
            pickups.get(i).init(getX() + 20 * i, getY() + 20 * i, getFloor());
        }

        Main.graphics.getPlayer().addXP(50);
        getFloor().removeEnemy(this);

    }

    public void stopAttack(){
        if(getEquipped()!=null){
            getEquipped().stop();
        }
        if(attack!=null){
            attack.stop();
        }
    }

    public void startAttack(){
        attack = controller.attack(this);
        attack.start();
    }

    @Override
    public Enemy clone() {
        Gun equipped = (getEquipped()==null)?null:Gun.get(getEquipped().getName());
        return new Enemy(getMaxHealth(), getSpeed(), equipped, getController());
    }

    public static Enemy get(String key){
        return list.get(key).clone();
    }

    public void addPickup(Pickup pickup){
        pickups.add(pickup);
    }

    public void scaleHealth(int tier){
        double scale = .5 * Math.pow(tier, 2);
        setMaxHealth((int)(getMaxHealth() * scale));
        setHealth(getMaxHealth());
    }

    public static void moveToPlayer(Character moving, Player player){
        double relAngle = Math.toRadians(GraphicsPanel.getAngle(moving.getCenter(), player.getCenter()));
        moving.translate((int)(Math.cos(relAngle)*moving.getSpeed()), (int)(Math.sin(relAngle)*moving.getSpeed()));
    }
}
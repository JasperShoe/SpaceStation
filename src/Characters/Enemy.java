package Characters;

import Client.GraphicsPanel;
import Client.Images;
import World.Floor;
import World.Gun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Enemy extends Character {

    private AI controller;
    private Timer attack;

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
                    public void move(Sprite moving, Player player) {
                        double relAngle = Math.toRadians(GraphicsPanel.getAngle(moving.getCenter(), player.getCenter()));
                        moving.translate((int)(Math.cos(relAngle)*moving.getSpeed()), (int)(Math.sin(relAngle)*moving.getSpeed()));
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
                })
                );
        list.put("sniper", new Enemy(50, 3, Gun.get("sniper"), new AI() {
            @Override
            public Point setGoal(Rectangle bounds) {
                return null;
            }

            @Override
            public void move(Sprite moving, Player player) {
                if(moving.getDistance(player) < 240){
                    double relAngle = Math.toRadians(GraphicsPanel.getAngle(moving.getCenter(), player.getCenter()));
                    moving.translate((int)(-Math.cos(relAngle)*moving.getSpeed()), (int)(-Math.sin(relAngle)*moving.getSpeed()));
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
        }));
    }

    public Enemy(int health, int speed, Gun gun, AI controller){
        super(0, 0, health, speed, null);
        setImg(Images.list.get("enemy_front"));

        this.controller = controller;

        if(gun!=null){
            equipGun(gun);
        }


        attack = controller.attack(this);

    }

    public void init(int x, int y, Floor floor){
        setX(x);
        setY(y);
        setFloor(floor);
        equipGun(getEquipped());
        getFloor().addEnemy(this);
        attack.start();
    }

    public void update(Graphics2D g2, Player player, double angle){
        controller.move(this, player);
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
        getEquipped().stop();
        attack.stop();
        getFloor().removeEnemy(this);
    }

    @Override
    public Enemy clone() {
        return new Enemy(getMaxHealth(), getSpeed(), Gun.get(getEquipped().getName()), getController());
    }

    public static Enemy get(String key){
        return list.get(key).clone();
    }
}
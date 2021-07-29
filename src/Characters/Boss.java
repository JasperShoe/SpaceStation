package Characters;

import Client.Images;
import World.Floor;
import World.Gun;
import World.Pickup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Boss extends Enemy {

    public static HashMap<String, Boss> bossList;
    static {
        bossList = new HashMap<>();
        bossList.put("Captain", new Boss("The Captain", 500, 2, 10, Gun.get("lmg"), "captain", new AI() {
            @Override
            public Point setGoal(Rectangle bounds) {
                return null;
            }

            @Override
            public void customize(Enemy enemy) {
                enemy.getEquipped().setBulletDamage(enemy.getEquipped().getBulletDamage() + enemy.getDamageBoost());
                enemy.addPickup(new Pickup(enemy.getEquipped(), enemy.getFloor()));
            }

            @Override
            public void move(Character moving, Player player) {
                moveToPlayer(moving, player);
            }

            @Override
            public Timer attack(Character attacker) {
                return new Timer(3000, new ActionListener() {
                    boolean firing;
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

        bossList.put("Scientist", new Boss("The Scientist", 200, 3, 20, Gun.get("laser"), "scientist", new AI() {
            @Override
            public Point setGoal(Rectangle bounds) {
                return null;
            }

            @Override
            public void customize(Enemy enemy) {
                enemy.getEquipped().setBulletDamage(enemy.getEquipped().getBulletDamage() + enemy.getDamageBoost());
                enemy.addPickup(new Pickup(enemy.getEquipped(), enemy.getFloor()));
            }

            @Override
            public void move(Character moving, Player player) {
                moveToPlayer(moving, player);
            }

            @Override
            public Timer attack(Character attacker) {
                return new Timer(2000, new ActionListener() {
                    boolean firing;
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

    private String name, imageName;
    private Player p;
    public Boss(String name, int health, int speed, int damageBoost, Gun gun, String image, AI controller){
        super(health, speed, gun, controller);
        setImg(Images.list.get("boss_" + image));//"boss_"+image
        this.name = name;
        this.imageName = image;
         setDamageBoost(damageBoost);
    }

    @Override
    public void init(int x, int y, Floor floor) {
        super.init(x, y, floor);
        p = floor.getParent().getPlayer();
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.BOLD, 64));
        g2.drawString(name, 0 - p.getTransX(), 700 - p.getTransY());
    }

    @Override
    public Enemy clone() {
        return new Boss(name, getMaxHealth(), getSpeed(), getDamageBoost(), Gun.get(getEquipped().getName()), imageName, getController());
    }

    @Override
    public void kill() {
        super.kill();
        p.addXP(225);
    }
}

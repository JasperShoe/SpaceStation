package Client;

import World.Cell;
import World.Floor;
import World.Wall;

import Characters.*;
import World.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
    private Player player;
    private GUI gui;

    private int speed = 2;
    private Floor floor;

    public static Rectangle cursor;
    private int[] translate = {0, 0};

    private ArrayList<Sprite> moving;

    public GraphicsPanel(){


        moving = new ArrayList<Sprite>();

        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);
        setBackground(Color.BLACK);

        floor = new Floor(this, 1);

        player = new Player(400-16, 400-16, floor);
        player.setSpeed(speed);

        player.setImg(Images.list.get("player_front"));
        addKeyListener(player);
        addMouseListener(player);
        addSprite(player);

        gui = new GUI(player);
        add(gui);

        cursor = new Rectangle(0, 0, 10, 10);

//        for (int i = 0; i < 6; i++) { //temporary enemy spawn for testing purposes
//            Enemy e = Enemy.get((String)(Enemy.list.keySet().toArray()[(int)(Math.random() * Enemy.list.keySet().size())]));
//            e.init(400 - i * 80, 400 - i * 80, floor);
//        }

//        sounds.play("theme");
//        Timer playTheme = new Timer(74000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sounds.play("theme");
//            }
//        });
//        playTheme.start();

        Timer update = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grabFocus();
                repaint();
            }
        });

        update.start();


    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        translate[0] = player.getTransX();
        translate[1] = player.getTransY();
        g2.translate(translate[0], translate[1]);
        gui.update(translate);

        Point mousePosition = getMousePosition();
        Point mouse = (mousePosition != null)?mousePosition:new Point(400, 400);
        cursor.setBounds(mouse.x - translate[0], mouse.y - translate[1], 10, 10);

        Cell curr_cell = floor.getMap()[(player.getY())/Cell.defaultHeight][(player.getX())/Cell.defaultWidth];
        if(!curr_cell.isRevealed() && curr_cell.getRect().contains(player.getBoundingRectangle())){
            curr_cell.reveal(floor);
        }
        for(Cell[] cells : floor.getMap()){
            for(Cell cell : cells){
                cell.draw(g2);
            }
        }

        for(Wall wall : floor.getWalls()){
            wall.draw(g2);
            for(Sprite sprite : moving){
                if(wall.intersects(sprite.getBoundingRectangle())){
                    wall.collide(sprite);
                }
            }
        }

        player.update(g2, mouse);
        if(!player.getEquipped().isForeground()){
            player.draw(g2);
        }

        for(int i = floor.getEnemies().size() - 1; i >=0; i--){
            Enemy enemy = floor.getEnemies().get(i);
            enemy.update(g2, player, enemy.getPlayerAngle(player));
            if(enemy.getEquipped()== null || !enemy.getEquipped().isForeground()){
                enemy.draw(g2);
            }
            if(enemy.intersects(player.getBoundingRectangle())){
                player.damage(5);
            }
        }

        for (int i = 0; i < floor.getBullets().size(); i++) {
            Bullet bullet = floor.getBullets().get(i);

            if(bullet.intersects(player.getBoundingRectangle())){
                bullet.collide(player);
            }
            for (int j = floor.getEnemies().size() - 1; j >= 0; j--) {
                Enemy enemy = floor.getEnemies().get(j);
                if(bullet.intersects(enemy.getBoundingRectangle())){
                    bullet.collide(enemy);
                }
            }

            if(bullet.isExpired()){
                floor.getBullets().remove(i);
                i--;
            }
            else{
                bullet.update();
                bullet.draw(g2);
            }

        }

        for(Wall wall : floor.getWalls()){
            wall.draw(g2);
            for(Sprite sprite : moving){
                if(wall.intersects(sprite.getBoundingRectangle())){
                    wall.collide(sprite);
                }
            }
        }

        for(Pickup pickup : floor.getPickups()){
            pickup.draw(g2);
        }

        for (int i = floor.getExplosions().size()-1; i >=0 ; i--) {
            Explosion explosion = floor.getExplosions().get(i);
            if(explosion.getW() < explosion.getRange()){
                explosion.draw(g2);
                if(explosion.intersects(player.getBoundingRectangle())){
                    player.damage(explosion.getDamage());
                }
                for (int j = floor.getEnemies().size() - 1; j >= 0; j--) {
                    Enemy enemy = floor.getEnemies().get(j);
                    if(explosion.intersects(enemy.getBoundingRectangle())){
                        enemy.damage(explosion.getDamage());
                    }
                }
            }
            else{
                floor.removeExplosion(explosion);
            }
        }

        //        g2.draw(cursor);


    }

    public void addSprite(Sprite sprite){
        moving.add(sprite);
    }

    public void removeSprite(Sprite sprite){
        moving.remove(sprite);
    }

    public void grabFocus(){
        super.grabFocus();
    }

    public static double getAngle(Point a, Point b){
        return Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));
    }

    public GUI getGui() {
        return gui;
    }
}

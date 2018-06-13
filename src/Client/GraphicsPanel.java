package Client;

import Characters.Character;
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

    private int speed = 2;
    private Floor floor;
    private Sounds sounds = new Sounds();

    private Rectangle cursor;
    private int[] translate = {0, 0};

    private ArrayList<Sprite> moving;

    public GraphicsPanel(){

//        Enemy.buildList();
        moving = new ArrayList<Sprite>();

        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);

        floor = new Floor(this);

        player = new Player(400-16, 400-16, floor);
        player.setSpeed(speed);



        player.setImg(Images.list.get("player_front"));
        addKeyListener(player);
        addMouseListener(player);
        addSprite(player);

        cursor = new Rectangle(0, 0, 10, 10);

        /*for (int i = 0; i < 4; i++) { //temporary enemy spawn for testing purposes
            Enemy e = Enemy.get((String)(Enemy.list.keySet().toArray()[(int)(Math.random() * Enemy.list.keySet().size())]));
            e.init(400 - i * 80, 400 - i * 80, floor);
        }*/

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

        Point mousePosition = getMousePosition();
        Point mouse = (mousePosition != null)?mousePosition:new Point(400, 400);
        cursor.setBounds(mouse.x - translate[0], mouse.y - translate[1], 10, 10);


        for(Cell[] cells : floor.getMap()){
            for(Cell cell : cells){
                cell.draw(g2);
            }
        }

        player.update(g2, mouse);
        if(!player.getEquipped().isForeground()){
            player.draw(g2);
        }

        for(Enemy enemy : floor.getEnemies()){
            enemy.update(g2, player, enemy.getPlayerAngle(player));
            if(!enemy.getEquipped().isForeground()){
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
        System.out.println("focused");
    }

    public static double getAngle(Point a, Point b){
        return Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));
    }
}

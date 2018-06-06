package Client;

import World.Cell;
import World.Floor;
import World.Wall;

import Character.*;
import World.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
    private Player player;
    private Gun mp5;


    private int speed = 2;
    private Floor floor;
    private Sounds sounds = new Sounds();

    private Rectangle cursor;
    private int[] translate = {0, 0};

    private ArrayList<Sprite> moving;

    public GraphicsPanel(){


        moving = new ArrayList<Sprite>();

        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);

        floor = new Floor(this);

        player = new Player(400-16, 400-16, 32, 32, floor);
        player.setSpeed(speed);



        player.setImg(Images.list.get("player_front"));
        addKeyListener(player);
        addMouseListener(player);
        addSprite(player);

        cursor = new Rectangle(0, 0, 10, 10);

        sounds.play("theme");
        Timer playTheme = new Timer(74000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.play("theme");
            }
        });
        playTheme.start();

        Timer update = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });

        update.start();

        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Images.list.get("cursor"), new Point(0,0), "cursor"));
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        translate[0] = player.getTransX();
        translate[1] = player.getTransY();
        g2.translate(translate[0], translate[1]);

        Point mouse = (getMousePosition() != null)?getMousePosition():new Point(400, 400);
        cursor.setBounds(mouse.x - translate[0], mouse.y - translate[1], 10, 10);
/* Mike Chen's preliminary gun implementation:

        mp5.update(player, g2, MouseInfo.getPointerInfo().getLocation());
        player.update();

        //TODO: rotate the gun so that it faces the mouse

        //gun is being held right handed
        if(player.getDirection() == 0){

            //left
            mp5.draw(g2);
            player.draw(g2);

        }
        else if(player.getDirection() == 1){

            //right
            player.draw(g2);
            mp5.draw(g2);

        }
        else if(player.getDirection() == 2){

            //up
            mp5.draw(g2);
            player.draw(g2);

        }
        else{

            //down
            player.draw(g2);
            mp5.draw(g2);

        }
*/


        for(Cell[] cells : floor.getMap()){
            for(Cell cell : cells){
                cell.draw(g2);
            }
        }

        player.update(g2, mouse);
        if(!player.getEquipped().isForeground()){
            player.draw(g2);
        }

        for (int i = 0; i < floor.getBullets().size(); i++) {
            Bullet bullet = floor.getBullets().get(i);

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
}

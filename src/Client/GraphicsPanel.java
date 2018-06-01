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

public class GraphicsPanel extends JPanel {

    private Player player;
    private Gun mp5;

    private int speed = 4;
    private Floor floor;

    public GraphicsPanel(){

        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);

        player = new Player(400-16, 400-16, 32, 32);
        player.setSpeed(speed);

        floor = new Floor();

        player.setImg(Images.list.get("player_front"));
        addKeyListener(player);

        mp5 = new Gun(0, 400-16, 400-16, 32, 32);
        mp5.setSpeed(speed);
        mp5.setImg(Images.list.get("gun_mp5"));

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
        g2.translate(player.getTransX(), player.getTransY());
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
        //floor.drawIntersections(g2);

        player.draw(g2);
        for(Wall wall : floor.getWalls()){
            wall.draw(g2);
            if(wall.intersects(player.getBoundingRectangle())){
                wall.collide(player);
            }
        }


        player.update(g2, getMousePosition());

    }
}

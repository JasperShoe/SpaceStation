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
    private Sounds sounds = new Sounds();

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
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(player.getTransX(), player.getTransY());

        for(Cell[] cells : floor.getMap()){
            for(Cell cell : cells){
                cell.draw(g2);
            }
        }

        player.update(getMousePosition());
        player.draw(g2);

        for(Wall wall : floor.getWalls()){
            wall.draw(g2);
            if(wall.intersects(player.getBoundingRectangle())){
                wall.collide(player);
            }
        }
    }
}

package Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Enemy extends Sprite {
    private boolean hasRandomLoc;

    private int randomX, randomY, randomRange;

    public Enemy(int x, int y, int w, int h){
        super(x, y, w, h);
        hasRandomLoc = false;
        randomRange = 200;

        Timer randomlyMove = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new Random().nextInt(100) < 33 && !hasRandomLoc) {
                    randomX = (new Random().nextInt(randomRange*2) - randomRange + getX())/getSpeed()*getSpeed();
                    randomY = (new Random().nextInt(randomRange*2) - randomRange + getY())/getSpeed()*getSpeed();
                    hasRandomLoc = true;
                }
            }
        });
        randomlyMove.start();
    }

    public void update(Graphics2D g2){
        if(hasRandomLoc){
            if(getX() != randomX){
                if(randomX < getX()){
                    move(0);
                } else {
                    move(1);
                }
            } else if(getY() != randomY){
                if(randomY < getY()){
                    move(2);
                } else {
                    move(3);
                }
            } else {
                hasRandomLoc = false;
            }
        }
        super.update();
        draw(g2);
    }
}
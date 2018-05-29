import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private int x, y, speed;

    private boolean isAlive;

    private BufferedImage img;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        isAlive = true;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(img, null, x, y);
    }

    public void move(int dir){
        int vX = 0, vY = 0;

        if(dir == 0){ //Left
            vX = -speed;
        } else if (dir == 1){ //Right
            vX = speed;
        } else if(dir == 2){ //Up
            vY = -speed;
        }  else if(dir == 3){ //Down
            vY = speed;
        }

        x += vX;
        y += vY;
    }

    //Setters
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    //Getters
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSpeed(){
        return speed;
    }

    public boolean isAlive(){
        return isAlive;
    }
}
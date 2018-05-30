import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private int x, y, transX, transY, speed;

    private boolean isAlive;

    private BufferedImage img;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        transX = 0;
        transY = 0;
        speed = 0;
        isAlive = true;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(img, null, x, y);
    }

    public void move(int dir){
        if(dir == 0){ //Left
            x -= speed;
            transX += speed;
        } else if (dir == 1){ //Right
            x += speed;
            transX -= speed;
        } else if(dir == 2){ //Up
            y -= speed;
            transY += speed;
        }  else if(dir == 3){ //Down
            y += speed;
            transY -= speed;
        }
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

    public void setImg(BufferedImage img){
        this.img = img;
    }

    //Getters
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getTransX(){
        return transX;
    }

    public int getTransY(){
        return transY;
    }

    public int getSpeed(){
        return speed;
    }

    public boolean isAlive(){
        return isAlive;
    }
}
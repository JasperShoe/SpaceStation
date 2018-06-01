package Character;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class Sprite {
    private int x, y, w, h, transX, transY, speed;

    private Rectangle dimensions;

    private boolean isAlive;

    private BufferedImage img;

    private HashMap<String, Boolean> directions;

    public Sprite(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        transX = 0;
        transY = 0;
        speed = 0;
        isAlive = true;
        dimensions = new Rectangle(x, y, w, h);

        directions = new HashMap<>();
        directions.put("Up", true);
        directions.put("Right", true);
        directions.put("Left", true);
        directions.put("Down", true);
    }

    public void draw(Graphics2D g2){
        g2.drawImage(img, null, x, y);
        g2.drawRect(getBoundingRectangle().x, getBoundingRectangle().y, getBoundingRectangle().width, getBoundingRectangle().height);
    }

    public void move(int dir){
        if(dir == 0 && directions.get("Left")){ //Left
            x -= speed;
            transX += speed;
        } else if (dir == 1 && directions.get("Right")){ //Right
            x += speed;
            transX -= speed;
        } else if(dir == 2 && directions.get("Up")){ //Up
            y -= speed;
            transY += speed;
        }  else if(dir == 3 && directions.get("Down")){ //Down
            y += speed;
            transY -= speed;
        }

        dimensions.x = x;
        dimensions.y = y;
    }

    public boolean intersects(Rectangle other){
//        if((getX() >= other.x && getX() <= other.x + other.width) || (getX() + getW() >= other.x && getX() + getW() <= other.x + other.width)){
//            if((getY() >= other.y && getY() <= other.y + other.height) || (getY() + getH() >= other.y && getY() + getH() <= other.y + other.height)){
//                return true;
//            }
//        }
//        return false;

        return(dimensions.intersects(other));
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
        dimensions = new Rectangle(getX(), getY(), img.getWidth(), img.getHeight());
    }

    //Getters
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getW(){
        return dimensions.width;
    }

    public int getH(){
        return dimensions.height;
    }

    public int getTransX(){
        return transX;
    }

    public int getTransY(){
        return transY;
    }

    public void setTransX(int transX) {
        this.transX = transX;
    }

    public void setTransY(int transY) {
        this.transY = transY;
    }

    public int getSpeed(){
        return speed;
    }

    public boolean isAlive(){
        return isAlive;
    }

    //public abstract void collide(Character.Sprite other);

    public void translate(int dx, int dy){
        setX(x + dx);
        setY(y + dy);
    }

    public Rectangle getBoundingRectangle(){
        return dimensions;
    }

    public Point getCenter(){
        return new Point(x + w/2, y + h/2);
    }

    public HashMap<String, Boolean> getDirections() {
        return directions;
    }

    public void update(){
        for (String s : directions.keySet()) {
            directions.put(s, true);
        }
    }
}
package World;

import Characters.Enemy;
import Client.Images;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by student on 5/29/18.
 */
public class Cell {

    public static final int defaultWidth = 500, defaultHeight = 500;
    private Point coords;
    private boolean revealed;
    private HashMap<String, Boolean> walls;
    private HashMap<String, Cell> neighbors;
    private ArrayList<Enemy> enemies;
    private int tier;

    public Cell(int x, int y, int tier){
        coords = new Point(x, y);
        this.tier = tier;
        enemies = new ArrayList<>();
        addEnemies((int)(Math.random() * 4 + 2));
        revealed = false;
        walls = new HashMap<>();
        walls.put("Up", false);
        walls.put("Left", false);
        walls.put("Right", false);
        walls.put("Down", false);
    }

    public void draw(Graphics2D g2){
        if(revealed){
            g2.drawImage(Images.list.get("floor_tiled"), null, coords.x, coords.y);
        }
        else{
            g2.setColor(Color.black);
            g2.fill(getRect());
        }
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public void addWall(Point a, Point b){
        Point wallStart = (a.x > b.x || a.y > b.y)?b:a;
        Point wallEnd = (wallStart.equals(a))?b:a;
        String delta = getDelta(wallStart, wallEnd);
        if(delta != null){
            walls.put(delta, true);
        }
    }

    public String getDelta(Point a, Point b){
        Point delta = new Point(b.x - a.x, b.y - a.y);
        if(onCell(a) && onCell(b)) {
            if (delta.equals(Floor.neighborDeltas.get("Down"))) {
                if (a.equals(coords))
                    return "Left";
                else
                    return "Right";

            } else if (delta.equals(Floor.neighborDeltas.get("Right"))) {
                if(a.equals(coords))
                    return "Up";
                else
                    return "Down";
            }
        }
        return null;
    }

    public boolean onCell(Point p){
        Point[] corners = new Point[]{
                new Point(coords.x + defaultWidth, coords.y),
                new Point(coords.x + defaultWidth, coords.y + defaultHeight),
                new Point(coords.x, coords.y + defaultHeight),
                coords
        };
        for(Point x : corners){
            if(x.equals(p)){
                return true;
            }
        }
        return false;
    }

    public HashMap<String, Boolean> getWalls() {
        return walls;
    }

    public Rectangle getRect(){
        return new Rectangle(coords.x, coords.y, defaultWidth, defaultHeight);
    }

    public boolean isRevealed(){
        return revealed;
    }

    public void reveal(Floor floor){
        revealed = true;
        if(floor.getEnemies().size() > 3) {
            for (Wall wall : floor.getWalls()) {
                Door door = wall.getDoor();
                if (door != null) {
                    wall.getDoor().setOpen(false);
                    wall.getDoor().setLocked(true);
                }
            }
        }
        for(Enemy enemy : enemies){
            int eX = coords.x + 64 + (int)(Math.random() * (Cell.defaultWidth-100));
            int eY = coords.y + 64 + (int)(Math.random() * (Cell.defaultHeight -100));
            enemy.init(eX, eY, floor);
        }
        for(String s : neighbors.keySet()){
            if(!walls.get(s) && !neighbors.get(s).isRevealed())
                neighbors.get(s).reveal(floor);
        }
    }

    public HashMap<String, Cell> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(HashMap<String, Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public void addEnemies(int max){
        for (int i = 0; i < (int)(Math.random() * max); i++) {
            Enemy toAdd = Enemy.get((String)(Enemy.list.keySet().toArray()[(int)(Math.random() * Enemy.list.keySet().size())]));
            toAdd.scaleHealth(tier);
            enemies.add(toAdd);
        }
    }
}

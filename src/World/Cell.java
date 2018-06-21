package World;

import Characters.Boss;
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
    private boolean revealed, safe;
    private HashMap<String, Boolean> walls;
    private HashMap<String, Cell> neighbors;
    private ArrayList<Enemy> enemies;
    private ArrayList<Location> locations;
    private int tier, num_walls;
    private Floor floor;

    public Cell(int x, int y, int tier, Floor floor){
        coords = new Point(x, y);
        this.tier = tier;
        enemies = new ArrayList<>();
        locations = new ArrayList<>();
        addEnemies((int)(Math.random() * 4 + 2));
        revealed = false;
        walls = new HashMap<>();
        walls.put("Up", false);
        walls.put("Left", false);
        walls.put("Right", false);
        walls.put("Down", false);
        this.floor = floor;
    }

    public void draw(Graphics2D g2){
        if(revealed){
            g2.drawImage(Images.list.get("floor_tiled"), null, coords.x, coords.y);
            for(Location l : locations){
                l.draw(g2);
            }
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
            num_walls++;
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

        for(Location l : locations){
            l.init(floor);
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

    public void addEnemy(Enemy e){
        enemies.add(e);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void populate(){
        double chestChance = Math.random();
        if(chestChance < .03 || (num_walls > 3 && chestChance < .4) || (num_walls > 2 && chestChance < .2)){
            int num_items = (Math.random() < .5)?(Math.random()<.5)?1:2:(Math.random() <.8)?(Math.random() < .5)?3:4:(Math.random()<.5)?5:6;
            Chest chest = new Chest(getCoords().x + defaultWidth/2 - 18, getCoords().y + defaultHeight/2 - 18, num_items);
            locations.add(chest);
        }

    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
        clearEnemies();
    }

    public void clearEnemies(){
        enemies.clear();
    }

    public void addChest(){
        int num_items = (Math.random() < .5)?(Math.random()<.5)?1:2:(Math.random() <.8)?(Math.random() < .5)?3:4:(Math.random()<.5)?5:6;
        Chest chest = new Chest(getCoords().x + defaultWidth/2 - 18, getCoords().y + defaultHeight/2 - 18, num_items);
        locations.add(chest);
    }

    public void addCrate(){
        locations.add(new AmmoCrate(getCoords().x + defaultWidth/2 - 18, getCoords().y + defaultHeight/2 - 18));
    }
}

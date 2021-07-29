package World;

import Characters.Boss;
import Client.GraphicsPanel;

import Characters.Enemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Floor {

    private int tier;

    private Cell[][] map; //cells on floor
    private Point[][] intersections; //corners of all the cells; walls connect two intersections
    private ArrayList<Wall> walls, vertical, horizontal;
    private HashMap<Point, HashMap<String, Boolean>> neighbors;
    public static HashMap<String, Point> neighborDeltas;
    private HashMap<String, Boolean> neighborTemplate;
    private HashMap<String, String> oppositesDirections;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Pickup> pickups;
    private ArrayList<Explosion> explosions;
    private ArrayList<Cell> customOptions;
    private GraphicsPanel parent;
    public Floor(GraphicsPanel parent, int tier){
        this.parent = parent;
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        pickups = new ArrayList<>();
        explosions = new ArrayList<>();
        customOptions = new ArrayList<>();

        this.tier = tier;

        oppositesDirections = new HashMap<>();
        oppositesDirections.put("Up","Down");
        oppositesDirections.put("Down", "Up");
        oppositesDirections.put("Right", "Left");
        oppositesDirections.put("Left", "Right");

        neighborTemplate = new HashMap<>();
        neighborTemplate.put("Up", false);
        neighborTemplate.put("Down", false);
        neighborTemplate.put("Right", false);
        neighborTemplate.put("Left", false);

        neighborDeltas = new HashMap<>();
        neighborDeltas.put("Up", new Point(0, -1 * Cell.defaultHeight));
        neighborDeltas.put("Right", new Point(Cell.defaultWidth, 0));
        neighborDeltas.put("Down", new Point(0, Cell.defaultHeight));
        neighborDeltas.put("Left", new Point(-1 * Cell.defaultWidth, 0));

        neighbors = new HashMap<>();

        walls = new ArrayList<>();
        map = new Cell[6][6];
        intersections = new Point[map.length + 1][map[0].length + 1];
        addIntersection(0, 0, new Point(0,0));
        for (int x = 0; x < map[0].length; x++) {
            addIntersection(0, x+1, new Point((x+1) * Cell.defaultWidth, 0));
        }
        for(int i = 0; i < map.length; i++){
            addIntersection(i+1, 0, new Point(0, (i + 1) * Cell.defaultHeight));
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Cell(j * Cell.defaultWidth, i * Cell.defaultHeight, tier, this);
                customOptions.add(map[i][j]);
                addIntersection(i+1, j+1, new Point((j+1) * Cell.defaultWidth, (i+1) * Cell.defaultHeight));
            }
        }
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                HashMap<String, Cell> cellNeighbors = new HashMap<>();
                if(onMap(r-1, c))
                    cellNeighbors.put("Up", map[r-1][c]);
                if(onMap(r, c+1))
                    cellNeighbors.put("Right", map[r][c+1]);
                if(onMap(r+1, c))
                    cellNeighbors.put("Down", map[r+1][c]);
                if(onMap(r, c-1))
                    cellNeighbors.put("Left", map[r][c-1]);
                map[r][c].setNeighbors(cellNeighbors);
            }
        }
        customOptions.remove(0);
        placeWalls();
    }

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public void addIntersection(int r, int c, Point p){
        HashMap<String, Boolean> neighborMap = (HashMap<String,Boolean>)neighborTemplate.clone();

        intersections[r][c] = p;
        neighbors.put(p, neighborMap);
    }

    public void drawIntersections(Graphics2D g2){
        g2.setColor(Color.red);
        for(Point[] points : intersections){
            for(Point point : points){
                g2.fillOval(point.x - 2, point.y - 2, 4, 4);
            }
        }
    }

    public void placeWalls(){
        placeExternalWalls();
        addWall(intersections[0][1], "Down", true);
        addWall(intersections[1][1], "Left", true);
        map[0][0].setSafe(true);
        while(walls.size() < 200) {
            Wall start = walls.get((int) (Math.random() * walls.size()));
            Point startPoint = start.getA();
            while (availableDirections(startPoint).size() == 0 || (start.getW() < Cell.defaultWidth && start.getH() < Cell.defaultHeight)) {
                start = walls.get((int) (Math.random() * walls.size()));
                startPoint = start.getA();
            }

            placeInternalWall(startPoint);
        }

        ArrayList<Wall> verticalWalls = new ArrayList<>();
        //Removing vertical walls from "walls" and adding them to "verticalWalls"
        for(int i = walls.size()-1; i >= 0; i--){
            if(walls.get(i).getOrientation() == Wall.VERTICAL){
                verticalWalls.add(walls.get(i));
                walls.remove(i);
            }
        }

        //Sorting vertical walls in "verticalWalls" by y position
        Collections.sort(verticalWalls, new Comparator<Wall>() {
            @Override
            public int compare(Wall wall, Wall other) {
                if(wall.getY() < other.getY()){
                    return -1;
                } else if(wall.getY() > other.getY()){
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        //Adding vertical walls back to "walls"
        for(Wall vertical : verticalWalls){
            walls.add(vertical);
        }

//        for(Cell[] row : map){
//            for(Cell c : row){
//                c.populate();
//            }
//        }
        Cell bossCell = randomCell();
        bossCell.clearEnemies();

        String boss;
        switch ((int)(Math.random()*2)){
            case 0:
                boss = "Captain";
                break;
            case 1:
                boss = "Scientist";
                break;
            default:
                boss = "Captain";
        }

        bossCell.addEnemy(Boss.bossList.get(boss).clone());

        Cell exit = randomCell();
        Point coords = exit.getCoords();
        int eX = coords.x + Cell.defaultWidth/2 - 64;
        int eY = coords.y + Cell.defaultHeight/2 - 64;
        exit.getLocations().add(new Exit(eX, eY));

        for (int i = 0; i < 5; i++) {
            randomCell().addChest();
        }

        for (int i = 0; i < 5; i++) {
            randomCell().addCrate();
        }
    }

    public void placeExternalWalls(){
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < intersections.length; j++) { //assumes map is square
                Point start;
                String delta;
                switch(i){
                    case(0):
                        start = intersections[0][j-1];
                        neighbors.get(start).put("Up", true);
                        neighbors.get(start).put("Left", true);
                        delta = "Right";
                        break;
                    case(1):
                        start = intersections[j-1][map.length];
                        neighbors.get(start).put("Right", true);
                        neighbors.get(start).put("Up", true);
                        delta = "Down";
                        break;
                    case(2):
                        start = intersections[map.length][intersections.length-j];
                        neighbors.get(start).put("Down", true);
                        neighbors.get(start).put("Right", true);
                        delta = "Left";
                        break;
                    case(3):
                        start = intersections[intersections.length -j][0];
                        neighbors.get(start).put("Left", true);
                        neighbors.get(start).put("Down", true);
                        delta = "Up";
                        break;
                    default:
                        start = intersections[0][0];
                        delta = "Up";
                        break;
                }
                addWall(start, delta, false);
            }
        }
    }

    public void placeInternalWall(Point start){
        ArrayList<String> directions = availableDirections(start);
        String delta = directions.get((int)(Math.random() * directions.size()));
        Wall placed = addWall(start, delta, true);
        if(availableDirections(placed.getA()).size() > ((Math.random() > .5)?2:1)){
            placeInternalWall(placed.getA());
        }
        if(availableDirections(placed.getB()).size() > 1){
            placeInternalWall(placed.getB());
        }
    }

    public boolean isEdge(Point point){
        return (point.x == 0 || point.x == Cell.defaultWidth * map.length || point.y == 0 || point.y == Cell.defaultHeight * map.length);
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public Wall addWall(Point start, String delta, boolean hasDoor){//builds wall from start to point in direction of delta
        Point translation = neighborDeltas.get(delta);
        Point end = start.getLocation();
        end.translate(translation.x, translation.y);
        Wall toAdd = new Wall(start, end, hasDoor, this);
        walls.add(toAdd);
        neighbors.get(start).put(delta, true);
        neighbors.get(intersections[end.y/Cell.defaultHeight][end.x/Cell.defaultWidth]).put(oppositesDirections.get(delta), true);

        placeWallsInCells(start, end);
        return toAdd;
    }

    public Wall addWall(Point start, Point delta, boolean hasDoor, boolean visible){
        Point end = start.getLocation();
        end.translate(delta.x, delta.y);
        Wall toAdd = new Wall(start, end, hasDoor, this);
        toAdd.setVisible(visible);
        walls.add(toAdd);
        return toAdd;
    }

    public ArrayList<String> availableDirections(Point p){
        ArrayList<String> available = new ArrayList<>();
        Point intersection = intersections[p.y/Cell.defaultHeight][p.x/Cell.defaultWidth];
        for(String s : neighbors.get(intersection).keySet()){
            if(!neighbors.get(intersection).get(s))
                available.add(s);
        }
        return available;
    }

    public int addBullet(Bullet bullet){
        bullets.add(bullet);
        parent.addSprite(bullet);
        return bullets.size() - 1;
    }

    public void removeBullet(int idx){
        parent.removeSprite(bullets.get(idx));
        bullets.remove(idx);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void addEnemy(Enemy enemy){
        parent.addSprite(enemy);
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy){
        parent.removeSprite(enemy);
        enemies.remove(enemy);
        if(enemies.size()<=3){
            for(Wall wall : walls){
                Door door = wall.getDoor();
                if(door!=null){
                    door.setLocked(false);
                }

            }
        }
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }

    public void placeWallsInCells(Point a, Point b){
        int r = a.y / Cell.defaultHeight;
        int c = a.x / Cell.defaultWidth;
        if(onMap(r, c))
            map[r][c].addWall(a, b);
        if(onMap(r - 1, c))
            map[r-1][c].addWall(a,b);
        if(onMap(r-1,c-1))
            map[r-1][c-1].addWall(a,b);
        if(onMap(r, c-1))
            map[r][c-1].addWall(a,b);
    }

    public boolean onMap(int r, int c){
        return (r >= 0 && c >= 0 && r < map.length && c < map[0].length);
    }

    public void addPickup(Pickup pickup){
        pickups.add(pickup);
    }

    public void removePickup(Pickup pickup){
        pickups.remove(pickup);
    }

    public ArrayList<Pickup> getPickups() {
        return pickups;
    }

    public void addExplosion(Explosion explosion){
        explosions.add(explosion);
    }

    public void removeExplosion(Explosion explosion){
        explosions.remove(explosion);
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public GraphicsPanel getParent() {
        return parent;
    }

    public int getTier() {
        return tier;
    }

    public Cell randomCell(){
        Cell c = customOptions.get((int)(Math.random() * customOptions.size()));
        customOptions.remove(c);
        return c;
    }
}

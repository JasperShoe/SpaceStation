package Client;

import Characters.Enemy;
import Characters.Player;
import World.Cell;
import World.Floor;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class Map extends JPanel {
    private int originalVW, originalVH, visibleWidth, visibleHeight, originalW, originalH, width, height, floorW, floorH,pX, pY, pW, pH;

    private Floor floor;

    private Player player;

    private int[] translate;

    private BasicStroke stroke;

    private CompoundBorder compoundBorder = new CompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 150,104)), BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(172, 172,172)));

    private boolean expand = false;

    public Map(Player player, Floor floor, int x, int y, int w, int h){
        this.player = player;
        this.floor = floor;
        originalVW = w;
        originalVH = h;
        this.visibleWidth = w;
        this.visibleHeight = h;
        width = visibleWidth * 4;
        height = visibleHeight * 4;
        originalW = width;
        originalH = height;
        setBounds(x, y, visibleWidth, visibleHeight);
        setBorder(compoundBorder);

        floorW = (floor.getMap()[0].length * Cell.defaultWidth);
        floorH = (floor.getMap().length * Cell.defaultHeight);
        pX = (int) (1.0 * width * player.getStart().x/ floorW);
        pY = (int) (1.0 * height * player.getStart().y / floorH);
        pW = (int) (width * 1.0 * player.getW() / floorW);
        pH = (int) (height * 1.0 * player.getH() / floorH);
        stroke = new BasicStroke(Images.list.get("wall_side_closed").getWidth()*width/floorW);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(stroke);
        g2.setColor(new Color(0, 0, 0, 127));
        g2.fillRect(0, 0, width, height);
        for (int r = 0; r < floor.getMap().length; r++) {
            for (int c = 0; c < floor.getMap()[r].length; c++) {
                Cell cell = floor.getMap()[r][c];
                int x = (int) (1.0 * width * (cell.getCoords().x + translate[0])/ floorW), y = (int) (1.0 * height * (cell.getCoords().y + translate[1])/floorH), w = (int) (1.0 * width / floor.getMap()[0].length), h = (int) (1.0 * height / floor.getMap().length);

                if(cell.isRevealed()) {
                    g2.setColor(new Color(30, 30, 30, 127));
                    g2.fillRect(x, y, w, h);


                    g2.setColor(new Color(111, 111, 111, 127));
                    if (cell.getWalls().get("Up")) {
                        g2.drawLine(x, y, x + w, y);
                    }

                    if (cell.getWalls().get("Down")) {
                        g2.drawLine(x, y + h, x + w, y + h);
                    }

                    if (cell.getWalls().get("Left")) {
                        g2.drawLine(x, y, x, y + h);
                    }

                    if (cell.getWalls().get("Right")) {
                        g2.drawLine(x + w, y, x + w, y + h);
                    }
                } else {
                    g2.setColor(new Color(0, 0, 0, 127));
                    g2.fillRect(x, y, w, h);
                }
            }
        }

        for(Enemy enemy : floor.getEnemies()){
            int eX = (int) (1.0 * width * (enemy.getX()+ translate[0]) / floorW), eY = (int) (1.0 * height * (enemy.getY()+ translate[1]) / floorH), eW = (int) (width * 1.0 * enemy.getW() / floorW), eH = (int) (height * 1.0 * enemy.getH() / floorH);
            g2.setColor(new Color(255, 0, 0));
            g2.fillOval(eX, eY, eW, eH);
        }

        g2.setColor(new Color(245, 245, 245));
        g2.drawOval(pX, pY, pW, pH);
    }

    public void toggleSize(int w, int h){
        if(visibleWidth == originalVW){
            visibleWidth = w;
            visibleHeight = h;
            width = w;
            height = h;
            setTranslate(new int[]{0, 0});
            pX = (int) (1.0 * width * player.getX() / floorW);
            pY = (int) (1.0 * height * player.getY() / floorH);
        } else {
            visibleWidth = originalVW;
            visibleHeight = originalVH;
            width = originalW;
            height = originalH;
            pX = (int) (1.0 * width * player.getStart().x/ floorW);
            pY = (int) (1.0 * height * player.getStart().y / floorH);
        }
        setBounds(800-visibleWidth, 0, visibleWidth, visibleHeight);
        pW = (int) (width * 1.0 * player.getW() / floorW);
        pH = (int) (height * 1.0 * player.getH() / floorH);
        expand = !expand;
    }

    public void setTranslate(int[] translate){
        this.translate = translate;
    }

    public void setPlayerLoc(){
        pX = (int) (1.0 * width * player.getX() / floorW);
        pY = (int) (1.0 * height * player.getY() / floorH);
    }

    public void setFloor(Floor f){
        this.floor = f;
    }

    public boolean getExpand(){
        return expand;
    }
}

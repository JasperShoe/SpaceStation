package Client;

import Characters.Player;
import World.Cell;
import World.Floor;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class Map extends JPanel {
    private int visibleWidth, visibleHeight, width, height, floorW, floorH,pX, pY, pW, pH;

    private Floor floor;

    private Player player;

    private int[] translate;

    private double dPX, dPY, dPW, dPH;

    private BasicStroke stroke;

    private CompoundBorder compoundBorder = new CompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 150,104)), BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(172, 172,172)));

    public Map(Player player, Floor floor, int x, int y, int w, int h){
        this.player = player;
        this.floor = floor;
        this.visibleWidth = w;
        this.visibleHeight = h;
        width = visibleWidth * 4;
        height = visibleHeight * 4;
        setBounds(x, y, visibleWidth, visibleHeight);
        setBorder(compoundBorder);

        floorW = (floor.getMap()[0].length * Cell.defaultWidth);
        floorH = (floor.getMap().length * Cell.defaultHeight);
        dPX = 1.0 * width * player.getX() / floorW;
        dPY = 1.0 * height * player.getY() / floorH;
        dPW = width * (1.0 * player.getW() / floorW) * 1.0;
        dPH = height * (1.0 * player.getH() / floorH);
        pX = (int) dPX;
        pY = (int) dPY;
        pW = (int) dPW;
        pH = (int) dPH;
        stroke = new BasicStroke(Images.list.get("wall_side_closed").getWidth()*width/floorW);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(stroke);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        for (int r = 0; r < floor.getMap().length; r++) {
            for (int c = 0; c < floor.getMap()[r].length; c++) {
                Cell cell = floor.getMap()[r][c];
                double dX = 1.0 * width * (cell.getCoords().x + translate[0])/ floorW, dY = 1.0 * height * (cell.getCoords().y + translate[1])/floorH, dW = 1.0 * width / floor.getMap()[0].length, dH = 1.0 * height / floor.getMap().length;
                int x = (int) dX, y = (int) dY, w = (int) dW, h = (int) dH;

                if(cell.isRevealed()) {
                    g2.setColor(new Color(30, 30, 30));
                    g2.fillRect(x, y, w, h);


                    g2.setColor(new Color(111, 111, 111));
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
                    g2.setColor(Color.BLACK);
                    g2.fillRect(x, y, w, h);
                }

                g2.setColor(Color.WHITE);
                g2.fillRect(pX, pY, pW, pH);
            }
        }
    }

    public void setTranslate(int[] translate){
        this.translate = translate;
    }
}

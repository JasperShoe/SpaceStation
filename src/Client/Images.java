package Client;

import World.Cell;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Images {

    public static HashMap<String, BufferedImage> list;
    static {
        list = new HashMap<>();
        String[] loader = {//add your picture strings here!
                "player_front",
                "player_back",
                "player_left",
                "player_right",
                "enemy_front",
                "wall_panel",
                "wall_side",
                "wall_external_side",
                "door_open",
                "door_closed",
                "gun_mp5",
                "cursor",
                "bullet_red",
                "floor",
                "bullet_yellow",
                "gun_uzi",
                "gun_laser",
                "gun_sniper",
                "bullet_purple",
                "gun_p90",
                "gui_main_panel",
                "icon_inventory",
                "gun_pistol"
        };
        for(String s : loader){
            list.put(s, readImg(s));
        }
        list.put("wall_front", createCombinedImage(list.get("wall_panel"), 1, 5, new ArrayList(), new ArrayList(){{add(2);}}));
        list.put("wall_external_front", createCombinedImage(list.get("wall_panel"), 1, 5, new ArrayList(), new ArrayList()));
        list.put("floor_tiled", createCombinedImage(list.get("floor"), Cell.defaultHeight/list.get("floor").getHeight(), Cell.defaultWidth/list.get("floor").getWidth(), new ArrayList(), new ArrayList()));
    }

    public static URL buildImageFile(String file){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource("res/" + file + ".png");
    }

    public static BufferedImage createCombinedImage(BufferedImage img, int r, int c, ArrayList rExceptions, ArrayList cExceptions){
        int width = c * img.getWidth(), height = r * img.getHeight();
        BufferedImage newImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        g2.fillRect(0, 0, width, height);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2.setColor(oldColor);
        for(int row = 0; row < r; row++){
            if(!rExceptions.contains(row)) {
                for (int col = 0; col < c; col++) {
                    if(!cExceptions.contains(col)){
                        g2.drawImage(img, null, col*img.getWidth(), row*img.getHeight());
                    }
                }
            }
        }
        g2.dispose();

        return newImage;
    }

    public static BufferedImage readImg(String file){
        BufferedImage ans = null;
        try {
            URL url = buildImageFile(file);
            ans =  ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        return ans;
    }
}
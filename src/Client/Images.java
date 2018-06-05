package Client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
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
                "gun_mp5",
                "wall_panel",
                "wall_side",
                "wall_external_side",
                "door_open",
                "door_closed"
        };
        for(String s : loader){
            list.put(s, readImg(s));
        }
        list.put("wall_front", makeFrontWall(list.get("wall_panel")));
        list.put("wall_external_front", makeExternalFrontWall(list.get("wall_panel")));
    }

    public static URL buildImageFile(String file){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource("res/" + file + ".png");
    }

    public static BufferedImage makeFrontWall(BufferedImage wall){
        int width = wall.getWidth() * 5;
        int height = wall.getHeight();

        BufferedImage newImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        g2.fillRect(0, 0, width, height);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2.setColor(oldColor);
        g2.drawImage(wall, null, 0, 0);
        g2.drawImage(wall, null, wall.getWidth(), 0);
        g2.drawImage(wall, null, wall.getWidth()*3, 0);
        g2.drawImage(wall, null, wall.getWidth()*4, 0);
        g2.dispose();

        return newImage;
    }

    public static BufferedImage makeExternalFrontWall(BufferedImage wall){
        int width = wall.getWidth() * 5;
        int height = wall.getHeight();

        BufferedImage newImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        g2.fillRect(0, 0, width, height);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2.setColor(oldColor);
        g2.drawImage(wall, null, 0, 0);
        g2.drawImage(wall, null, wall.getWidth(), 0);
        g2.drawImage(wall, null, wall.getWidth()*2, 0);
        g2.drawImage(wall, null, wall.getWidth()*3, 0);
        g2.drawImage(wall, null, wall.getWidth()*4, 0);
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
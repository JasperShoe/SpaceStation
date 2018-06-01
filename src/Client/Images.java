package Client;

import javax.imageio.ImageIO;
import javax.imageio.*;
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
                "wall_front",
                "wall_side"
        };
        for(String s : loader){
            list.put(s, readImg(s));
        }
    }


    public static URL buildImageFile(String file){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource("res/" + file + ".png");
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
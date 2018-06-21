package Client;

import World.Cell;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Images {

    public static HashMap<String, BufferedImage> list;
    static {
        list = new HashMap<>();
        String[] loader = {//add your picture strings here!

                //Player Images
                "player_front",
                "player_back",
                "player_left",
                "player_right",

                //Enemy Images
                "enemy_front",

                //Wall Images
                "wall_panel",
                "wall_side_closed",
                "wall_side_opened",
                "wall_side_locked",
                "wall_external_side",

                //Door
                "door_opened",
                "door_closed",
                "door_locked",

                //Weapons
                "gun_mp5",
                "gun_uzi",
                "gun_laser",
                "gun_sniper",
                "gun_p90",
                "gun_pistol",
                "gun_lmg",

                //Floors
                "floor_0",
                "floor_1",
                "floor_2",
                "floor_3",
                "floor_4",
                "floor_5",

                //Bullets
                "bullet_yellow",
                "bullet_purple",
                "bullet_red",
                "bullet_blue",
                "bullet_green",
                "bullet_orange",

                //Extras
                "loc_ammo",
                "loc_chest",
                "loc_open",
                "icon_inventory",
                "icon_upgrades",
                "icon_map_player",
                "icon_map_enemy",
                "gui_main_panel_overlay",
                "gui_gunslot_underlay",
                "cursor",

                //Images
                "boss_captain",
                "boss_scientist",

                "loc_elevator_opened",
                "loc_chest",
                "loc_ammo",
                "loc_open"

        };
        for(String s : loader){
            list.put(s, readImg(s));
        }
        list.put("wall_front", createCombinedImage(new ArrayList<BufferedImage>(){{add(list.get("wall_panel"));}}, 1, 5, new ArrayList(), new ArrayList<Integer>(){{add(100);}}, new ArrayList(){{add(2);}}, 0, 0));
        list.put("wall_external_front", createCombinedImage(new ArrayList<BufferedImage>(){{add(list.get("wall_panel"));}}, 1, 5, new ArrayList<Integer>(){{add(100);}}, new ArrayList(), new ArrayList(), 0, 0));
        list.put("floor_tiled", createCombinedImage(new ArrayList<BufferedImage>(){
            {
            add(list.get("floor_0"));
            add(list.get("floor_1"));
            add(list.get("floor_2"));
            add(list.get("floor_3"));
            add(list.get("floor_4"));
            add(list.get("floor_5"));
            }
        }, Cell.defaultHeight/list.get("floor_0").getHeight(), Cell.defaultWidth/list.get("floor_0").getWidth(), new ArrayList<Integer>(){
            {
             add(20);
             add(20);
             add(20);
             add(20);
             add(15);
             add(5);
            }
        }, new ArrayList(), new ArrayList(), 0, 0));}

    public static URL buildImageFile(String file){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource("res/" + file + ".png");
    }

    public static BufferedImage createCombinedImage(ArrayList<BufferedImage> images, int r, int c, ArrayList<Integer> percentages, ArrayList rExceptions, ArrayList cExceptions, int xPad, int yPad){
        int width = c * images.get(0).getWidth(), height = r * images.get(0).getHeight();
        BufferedImage newImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        g2.fillRect(0, 0, width, height);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2.setColor(oldColor);
        if(r == 1 && c == 1){
            for(BufferedImage img : images){
                g2.drawImage(img, null, width/2 - img.getWidth()/2 + xPad, height/2 - img.getHeight()/2 + yPad);
            }
        } else {
            for (int row = 0; row < r; row++) {
                if (!rExceptions.contains(row)) {
                    for (int col = 0; col < c; col++) {
                        if (!cExceptions.contains(col)) {
                            BufferedImage img = images.get(0);
                            int random = new Random().nextInt(100);
                            int percent = 0;
                            for (int i = 0; i < percentages.size(); i++) {
                                percent += percentages.get(i);
                                if (random < percent) {
                                    img = images.get(i);
                                    break;
                                }
                            }
                            g2.drawImage(img, null, col * img.getWidth(), row * img.getHeight());
                        }
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
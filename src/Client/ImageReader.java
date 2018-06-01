package Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {

    public BufferedImage readBufferedImage(String imageName) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("./res/" + imageName + ".png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return img;
    }
}
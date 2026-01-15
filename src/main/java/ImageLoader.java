import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class ImageLoader {
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(new File(path));
        }catch(IOException e){
            System.out.print("Failed to get image at "+ path + e.getMessage());
            exit(-1);
        }
        return null;
    }
}

package org.hui.design;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ImageTest {
    @Test
    public void test() {
        try {
            /*BufferedImage bufferedImage = ImageIO.read(new File("C:/Users/Mexn-/Pictures/WeiXin/mmexport1594349992316.png"));
            assertNotNull(bufferedImage);*/
            BufferedImage image = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

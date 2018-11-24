package squaredungeon.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	private BufferedImage image;

	public BufferedImage loadImage(String path) { //get the specified image and read it
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
}

package squaredungeon.particles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import squaredungeon.gameObjects.Entity;
import squaredungeon.gameObjects.ID;
import squaredungeon.gfx.Animation;
import squaredungeon.gfx.SpriteSheet;

public class TorchLight extends Entity {
	//ALL OF THIS IS PROBABLY TEMP
	//TODO OPENGL LIGHTING
	private int counter2 = 1;
	private boolean less = false;
	Random random = new Random();
	private BufferedImage TorchLight;
	private BufferedImage[] torch_image = new BufferedImage[3];
	Animation anim;

	private float size;

	public TorchLight(int x, int y, ID id, SpriteSheet ss, int Size) {
		super(x, y, id, ss);
		TorchLight = ss.grabImage(2, 4, 32, 32);

		torch_image[0] = ss.grabImage(2, 1, 32, 32);
		torch_image[1] = ss.grabImage(2, 2, 32, 32);
		torch_image[2] = ss.grabImage(2, 3, 32, 32);

		anim = new Animation(3, torch_image[0], torch_image[1], torch_image[2]);
		this.size = Size;
	}

	@Override
	public void tick() {
		anim.runAnimation();

		if (counter2 >= 20) {
			counter2 = 0;
			if (!less)
				less = true;
			else if (less)
				less = false;
		}

		counter2++;

	}

	@Override
	public void render(Graphics g) {
		
		anim.drawAnimation(g, x-6 , y-6, 12, 12, 0);

		// g.drawImage(torch_image, (int) x-8, (int) y-8, 16, 16, null);
		if (less) {
			size-=0.05f;
			g.drawImage(TorchLight, (int) x - (int)size / 2, (int) y - (int)size / 2, (int)size, (int)size, null);
		} else {
			size+=0.05f;
			g.drawImage(TorchLight, (int) x - (int)size / 2, (int) y - (int)size / 2, (int)size, (int)size, null);
		}

	}

	@Override
	public Rectangle getBounds() {
 		return null;
	}

}

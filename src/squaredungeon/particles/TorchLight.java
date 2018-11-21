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

	private int counter2 = 1;
	private boolean less = false;
	Random random = new Random();
	private BufferedImage TorchLight;
	private BufferedImage[] torch_image = new BufferedImage[3];
	Animation anim;

	private int size;

	public TorchLight(int x, int y, ID id, SpriteSheet ss, int Size) {
		super(x, y, id, ss);
		TorchLight = ss.grabImage(3, 32, 32, 32);

		torch_image[0] = ss.grabImage(2, 29, 32, 32);
		torch_image[1] = ss.grabImage(3, 29, 32, 32);
		torch_image[2] = ss.grabImage(4, 29, 32, 32);

		anim = new Animation(3, torch_image[0], torch_image[1], torch_image[2]);
		this.size = Size;
	}

	@Override
	public void tick() {
		anim.runAnimation();

		if (counter2 >= random.nextInt(1) + 20) {
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
		anim.drawAnimation(g, x - 13, y - 13, 0);

		// g.drawImage(torch_image, (int) x-8, (int) y-8, 16, 16, null);
		if (less) {
			size = (int) ((size + counter2 / 19));
			g.drawImage(TorchLight, (int) x - size / 2, (int) y - size / 2, size, size, null);
		} else {
			size = (int) ((size - counter2 / 19));
			g.drawImage(TorchLight, (int) x - size / 2, (int) y - size / 2, size, size, null);
		}

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}

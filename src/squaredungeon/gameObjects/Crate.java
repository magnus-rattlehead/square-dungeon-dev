package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import squaredungeon.gfx.Animation;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Main;

public class Crate extends Entity {

	private BufferedImage[] crate_image = new BufferedImage[7];
	Animation anim;

	public Crate(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);

		crate_image[0] = ss.grabImage(1, 1, 32, 32);
		crate_image[1] = ss.grabImage(1, 2, 32, 32);
		crate_image[2] = ss.grabImage(1, 3, 32, 32);
		crate_image[3] = ss.grabImage(1, 4, 32, 32);
		crate_image[4] = ss.grabImage(1, 5, 32, 32);
		crate_image[5] = ss.grabImage(1, 6, 32, 32);
		crate_image[6] = ss.grabImage(1, 7, 32, 32);

		anim = new Animation(3, crate_image[0], crate_image[1], crate_image[2], crate_image[3], crate_image[4],
				crate_image[5], crate_image[6]); //sexy bouncing animation, vish
	}

	@Override
	public void tick() {
		anim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		if(Main.main.camera.getX() < x+32 && Main.main.camera.getX()+Main.WIDTH > x && Main.main.camera.getY() < y+32 && Main.main.camera.getY()+Main.HEIGHT > y) 
			anim.drawAnimation(g, x, y, 32 , 32, 0);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}

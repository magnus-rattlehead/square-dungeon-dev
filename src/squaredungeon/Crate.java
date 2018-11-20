package squaredungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crate extends GameObject{

	private BufferedImage[] crate_image = new BufferedImage[7];
	Animation anim;
	
	public Crate(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id,ss);
		
		crate_image[0] = ss.grabImage(1, 26, 32, 32);
		crate_image[1] = ss.grabImage(1, 27, 32, 32);
		crate_image[2] = ss.grabImage(1, 28, 32, 32);
		crate_image[3] = ss.grabImage(1, 29, 32, 32);
		crate_image[4] = ss.grabImage(1, 30, 32, 32);
		crate_image[5] = ss.grabImage(1, 31, 32, 32);
		crate_image[6] = ss.grabImage(1, 32, 32, 32);
		
		anim = new Animation(3, crate_image[0], crate_image[1],crate_image[2],crate_image[3],crate_image[4],crate_image[5],crate_image[6]);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		anim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		anim.drawAnimation(g,x,y,0);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x,(int)y,32,32);
	}

}

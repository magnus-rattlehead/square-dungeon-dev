package squaredungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends GameObject{
	private BufferedImage block_image;
	
	public Block(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		//block_image = ss.grabImage(0, 0, 32, 32); //placeholer, replace later
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, 32, 32);
		//g.drawImage(block_image, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x,(int)y,32,32);
	}

}

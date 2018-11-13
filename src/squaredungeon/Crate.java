package squaredungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crate extends GameObject{

	private BufferedImage crate_image;
	
	public Crate(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id,ss);
		
		//crate_image = ss.grabImage(0, 0, 32, 32);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.PINK);
		g.fillRect(x, y, 32, 32);
		//g.drawImage(crate_image, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y,32,32);
	}

}

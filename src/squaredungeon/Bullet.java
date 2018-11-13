package squaredungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject{
	private Handler handler;
	
	public Bullet(double x, double y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		
		int bv = 1;
		double angle = Math.atan2(mx - x, my - y);
        double vx = (bv) * Math.cos(angle);
        double vy = (bv) * Math.sin(angle);
		
		
	}

	@Override
	public void tick() {
		x+= vx;
		y+= vy;
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) handler.removeObject(this);
			}
			
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillOval((int)x, (int)y, 8, 8);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,8,8);
	}

}

package squaredungeon.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;
import squaredungeon.main.Weapon;

public class Bullet extends GameObject {
	private Handler handler;

	public Bullet(int x, int y, ID id, Handler handler, float mx, float my, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;

		int bv = Weapon.get().getBulletVelocity();
		double angle = Math.atan2(my - y, mx - x);
		vx = (float) ((bv) * Math.cos(angle));
		vy = (float) ((bv) * Math.sin(angle));

	}

	@Override
	public void tick() {
		x += vx;
		y += vy;

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Block) {
				if (getBounds().intersects(tempObject.getBounds()))
					handler.removeObject(this);
			}

		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillOval(x, y, 8, 8);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 8, 8);
	}

}

package squaredungeon.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;

public class Bullet extends Entity {
	private Handler handler;

	public Bullet(int x, int y, ID id, Handler handler, float mx, float my, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;

		int bv = Weapon.get().getBulletVelocity();
		double angle = Math.atan2(my - y, mx - x); //find the angle of the mouse 
		vx = (float) ((bv) * Math.cos(angle)); //zoom zoom towards that angle 
		vy = (float) ((bv) * Math.sin(angle));

	}

	@Override
	public void tick() {
		x += vx; //move every tick
		y += vy;

		for (int i = 0; i < handler.tile.size(); i++) {
			Tile tempTile = handler.tile.get(i);

			if (tempTile.getId() == ID.SOLIDTILE) {
				if (getBounds().intersects(tempTile.getBounds()))
					handler.removeEntity(this); //if it hits a block it DIES
			}

		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN); //TODO Make a sexy bullet sprite
		g.fillOval(x, y, 8, 8);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 8, 8);//TODO make this class an abstract and have different sized bullets
	}

}

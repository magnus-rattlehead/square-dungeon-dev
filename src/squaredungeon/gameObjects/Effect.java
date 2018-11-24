package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;

public abstract class Effect{


	protected Handler handler;
	protected double vx = 0; //velocity
	protected double vy = 0;
	protected int x, y;
	protected ID id;
	protected SpriteSheet ss;

	public Effect(int x, int y, ID id, SpriteSheet ss) {
		handler = new Handler();
		this.x = x;
		this.y = y;
		this.id = id;
		this.ss = ss;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;

public abstract class GameObject {

	protected int x, y;
	protected float vx = 0, vy = 0;
	protected ID id;
	protected SpriteSheet ss;
	protected boolean spottedPlayer;

	public GameObject(int x, int y, ID id, SpriteSheet ss) {
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

	public float getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

}

package squaredungeon;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected double x, y;
	protected float vx = 0, vy = 0;
	protected ID id;
	protected SpriteSheet ss;
	protected boolean spottedPlayer;
	
	public GameObject(double x2, double y2, ID id, SpriteSheet ss) {
		this.x = x2;
		this.y = y2;
		this.id = id;
		this.ss =ss;
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

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double  getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}
	
	
}

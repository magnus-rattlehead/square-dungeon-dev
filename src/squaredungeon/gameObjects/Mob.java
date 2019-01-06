package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;

public abstract class Mob{

	public int hp;
	protected double vx, vy;
	protected double fx, fy;
	protected boolean spottedPlayer = false;
	protected Handler handler;
	protected int y2;
	protected int x2;
	protected int x, y;
	protected ID id;
	protected SpriteSheet ss;
	
	public Mob(int x, int y, ID id, SpriteSheet ss, int hp) {
		handler = new Handler();
		this.hp = hp;
		this.x = x;
		this.y = y;
		this.id = id;
		this.ss = ss;
	}
	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract void renderBottomHalf(Graphics g);
		
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
	
	
	public double getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}




	
}

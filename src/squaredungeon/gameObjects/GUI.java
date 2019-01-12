package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;

public abstract class GUI {
	protected Handler handler;
	protected int x, y;
	protected ID id;
	protected SpriteSheet ss;

	public GUI(Player player, int x, int y, ID id, SpriteSheet ss) {
		handler = new Handler();
		this.x = x;
		this.y = y;
		this.id = id;
		this.ss = ss;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();



}

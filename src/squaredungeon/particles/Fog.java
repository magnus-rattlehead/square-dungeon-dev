package squaredungeon.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import squaredungeon.gameObjects.Effect;
import squaredungeon.gameObjects.ID;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Main;

public class Fog extends Effect {

	private int size;
	private int alpha;
	private Random random = new Random();
	private float vx = (random.nextInt(10 - (-10) + 1) - 10);
	private float vy = (random.nextInt(10 - (-10) + 1) - 10);
	private float x1 = (float) x;
	private float y1 = (float) y;

	public Fog(int x, int y, ID id, SpriteSheet ss, int size, int alpha) {
		super(x, y, id, ss);
		this.size = size;

		if (alpha == 0) {
			this.alpha = random.nextInt(7) + 1;
		} else {
			this.alpha = alpha;
		}
	}

	@Override
	public void tick() {

		x1 += vx / 100;
		y1 += vy / 100;

		if (y >= Main.level.getHeight() * 32)
			y = 0;
		if (x >= Main.level.getWidth() * 32)
			x = 0;
		if (y <= 0)
			y = Main.level.getHeight() * 32;
		if (x <= 0)
			x = Main.level.getWidth() * 32;
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {

		g.setColor(new Color(230, 230, 255, alpha));
		g.fillRect((int) x1, (int) y1, size, size);

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, size, size);
	}

}

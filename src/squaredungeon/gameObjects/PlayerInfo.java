package squaredungeon.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Main;

public class PlayerInfo extends GUI {

	private Main main;
	public PlayerInfo(int x, int y, ID id, SpriteSheet ss, Main main) {
		super(x, y, id, ss);
		this.main = main;
	}
	@Override
	public void tick() {
		
	}
	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int)main.camera.getX(), (int)main.camera.getY(), Main.WIDTH, Main.HEIGHT/16);
		// HUD//

		// Health Bar
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 100, 16);
		g.setColor(Color.GREEN);
		g.fillRect(5, 5, 100, 16);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, 100, 16);

		// Ammo Display

		g.setColor(Color.WHITE);
		// g.drawString("Ammo: " + Weapon.get().getMagSize() + "/" +
		// Weapon.get().getMaxAmmo(), 5, 64);
		////////////////////////////////////
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}

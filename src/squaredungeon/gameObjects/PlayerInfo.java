package squaredungeon.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Main;

public class PlayerInfo extends GUI {
	private Player player;
	private Main main;
	private int playerhpwidth;
	private BufferedImage hp_bar_img;
	private BufferedImage hp_bar_division_img;
	private BufferedImage hp_bar_outline_img;
	private BufferedImage hp_bar_background_img;
	public PlayerInfo(Player player, int x, int y, ID id, SpriteSheet ss, Main main) {
		super(player, x, y, id, ss);
		this.main = main;
		this.player = player;
		hp_bar_outline_img = ss.grabImage(2, 1, 64, 8);
		hp_bar_img = ss.grabImage(1, 1, 64, 8);
		hp_bar_background_img = ss.grabImage(1, 2, 64, 16);
		
	}
	@Override
	public void tick() {
		
	}
	@Override
	public void render(Graphics g) {
		
		playerhpwidth = (64*player.hp/100);
		
		
		if(playerhpwidth < 0)
			playerhpwidth = 0;
		if((playerhpwidth > 0)) {
			hp_bar_division_img = hp_bar_img.getSubimage(0, 0, playerhpwidth, 8);
		}
		else {
			hp_bar_division_img = null;
		}
		
		
		
		g.setColor(new Color(0,0,0,120));
		//g.fillRect(main.camera.getX() + ((Main.WIDTH/3)), main.camera.getY(), (int)(Main.WIDTH-Main.WIDTH/1.5), Main.HEIGHT/8);
		g.drawImage(hp_bar_background_img, main.camera.getX(), main.camera.getY(),118,24,null);
		g.drawImage(hp_bar_outline_img, main.camera.getX() + 16, main.camera.getY()+6, 96,12,null);
		if(hp_bar_division_img != null)
			g.drawImage(hp_bar_division_img, main.camera.getX() + 16, main.camera.getY()+6, (int)(playerhpwidth*1.5),12,null);
		g.drawImage(player.player_image_headshot,main.camera.getX() + 2, main.camera.getY()+6, 13,13,null);
	
		// HUD//

		// Health Bar
	/*	g.setColor(Color.GRAY);
		g.fillRect(5, 5, 100, 16);
		g.setColor(Color.GREEN);
		g.fillRect(5, 5, player.hp, 16);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, 100, 16);*/

		// Ammo Display

	
		// g.drawString("Ammo: " + Weapon.get().getMagSize() + "/" +
		// Weapon.get().getMaxAmmo(), 5, 64);
		////////////////////////////////////
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}

package squaredungeon.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class StartMenu {
	
	private Rectangle playButton;
	public boolean clicked = false;
	
	public StartMenu() {
		
		playButton = new Rectangle(Main.WIDTH/2-(Main.WIDTH/4/2), Main.HEIGHT/2-(Main.HEIGHT/4/2), Main.WIDTH/4, Main.HEIGHT/4);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(playButton.x, playButton.y, playButton.width, playButton.height);
	}
	
	public void clicked(int x, int y) {
		if(new Rectangle(x,y,1,1).intersects(playButton)) {
			Main.main.StartGame();
		}
	}
}

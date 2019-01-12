package squaredungeon.gameObjects;

import squaredungeon.main.Main;

public class Camera {
	private int x, y;

	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void tick(Mob mob) {
		x += ((mob.getX()+16 - x) - Main.WIDTH / 2) * 0.2f; // follows the player with an offset (the float at the end is the ACCELERATION)
		y += ((mob.getY()+16 - y) - Main.HEIGHT / 2) * 0.2f;
		
		if(x + Main.WIDTH > (Main.main.level_width+1)*32) {
			x = (Main.main.level_width+1)*32 - Main.WIDTH;
		}
		if(y + Main.HEIGHT > (Main.main.level_height+1)*32) {
			y = (Main.main.level_height+1)*32 - Main.HEIGHT;
		}
		if(x  < -32) {
			x = -32;
		}
		if(y + Main.HEIGHT < -32) {
			y = -32;
		}

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

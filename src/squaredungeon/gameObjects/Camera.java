package squaredungeon.gameObjects;

import squaredungeon.main.Main;

public class Camera {
	private float x, y;

	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void tick(Mob mob) {
		x += ((mob.getX()+16 - x) - Main.WIDTH / 2 / Main.SCALE) * 0.05f; // follows the player with an offset (the float at the end is the ACCELERATION)
		y += ((mob.getY()+16 - y) - Main.HEIGHT / 2 / Main.SCALE) * 0.05f;

	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}

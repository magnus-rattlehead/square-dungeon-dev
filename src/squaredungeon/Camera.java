package squaredungeon;

public class Camera {
	private float x,y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object) {
		x +=((object.getX()-x) - Main.WIDTH/2/Main.SCALE) * 0.05f;
		y +=((object.getY()-y) - Main.HEIGHT/2/Main.SCALE) * 0.05f;
		
		if(x <=0) x =0;
		if(x >= Main.WIDTH + 32) x = Main.WIDTH + 32;
		if(y <=0) y = 0;
		if(y >= Main.HEIGHT+ 48) y = Main.HEIGHT + 48;
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

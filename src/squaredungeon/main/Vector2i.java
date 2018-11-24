package squaredungeon.main;

public class Vector2i {
	
	private int x, y;
	
	public Vector2i() {
		set(0, 0);
	}
	
	public Vector2i(Vector2i vector2) {
		set(vector2.x, vector2.y);
	}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2i add(Vector2i vector2) {
		this.x += vector2.x;
		this.y += vector2.y;
		return this;
	}
	
	public Vector2i subtract(Vector2i vector2) {
		this.x -= vector2.x;
		this.y -= vector2.y;
		return this;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public boolean equals(Object object) {
		if(!(object instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) object;
		if(vec.getX() == this.getX() && vec.getY() == this.getY()) return true;
		return false;
	}

}

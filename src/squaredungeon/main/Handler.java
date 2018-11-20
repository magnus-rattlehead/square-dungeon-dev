package squaredungeon.main;

import java.awt.Graphics;
import java.util.ArrayList;

import squaredungeon.gameObjects.GameObject;

public class Handler {
	public ArrayList<GameObject> object = new ArrayList<GameObject>();
	private boolean up = false, down = false, right = false, left = false;

	public synchronized void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.render(g);
		}
	}

	public synchronized void addObject(GameObject tempObject) {
		object.add(tempObject);
	}

	public synchronized void removeObject(GameObject tempObject) {
		object.remove(tempObject);
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

}

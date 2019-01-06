package squaredungeon.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Mob;

public class KeyInput extends KeyAdapter {

	Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);
			//makes the player go left, right, or goodnight
			if (tempMob.getId() == ID.PLAYER) {
				if (key == KeyEvent.VK_W)
					handler.setUp(true);
				if (key == KeyEvent.VK_A)
					handler.setLeft(true);
				if (key == KeyEvent.VK_S)
					handler.setDown(true);
				if (key == KeyEvent.VK_D)
					handler.setRight(true);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);

			if (tempMob.getId() == ID.PLAYER) {
				if (key == KeyEvent.VK_W)
					handler.setUp(false);
				if (key == KeyEvent.VK_A)
					handler.setLeft(false);
				if (key == KeyEvent.VK_S)
					handler.setDown(false);
				if (key == KeyEvent.VK_D)
					handler.setRight(false);
			}
		}
	}
}

package squaredungeon.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import squaredungeon.gameObjects.Bullet;
import squaredungeon.gameObjects.Camera;
import squaredungeon.gameObjects.Entity;
import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Mob;
import squaredungeon.gameObjects.Weapon;
import squaredungeon.gfx.SpriteSheet;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;

	public MouseInput(Handler handler, Camera camera, Main main, SpriteSheet ss) {
		this.handler = handler;
		this.camera = camera;
		this.ss = ss;
	}

	public void mousePressed(MouseEvent e) {
		float mx = (e.getX() / Main.SCALE + camera.getX()); //get mouse relative to camera position
		float my = (e.getY() / Main.SCALE + camera.getY());

		for (int i = 0; i < handler.entity.size(); i++) {
			Entity tempEntity = handler.entity.get(i);
			if (tempEntity.getId() == ID.WEAPON && Weapon.get().getIsEquipped() && Weapon.get().getMagSize() >= 1) {
				handler.addEntity(new Bullet(tempEntity.getX(), tempEntity.getY(), ID.BULLET, handler, mx, my, ss));
				Weapon.get().minusOneBullet();
			} else if (tempEntity.getId() == ID.WEAPON && Weapon.get().getIsEquipped()
					&& Weapon.get().getMagSize() == 0) {
				// TODO add reload method
				break;
			}
		}
	}
}

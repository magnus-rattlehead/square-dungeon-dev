package squaredungeon.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;

import squaredungeon.gameObjects.Bullet;
import squaredungeon.gameObjects.Camera;
import squaredungeon.gameObjects.Entity;
import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Mob;
import squaredungeon.gameObjects.Weapon;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.net.GameClient;
import squaredungeon.net.GameServer;

public class MouseInput extends MouseAdapter implements MouseWheelListener{

	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;

	public MouseInput(Handler handler, Camera camera, Main main, SpriteSheet ss) {
		this.handler = handler;
		this.camera = camera;
		this.ss = ss;
		
		Main.main.addMouseWheelListener(this);
	}

	public void mousePressed(MouseEvent e) {
		int mx = (int)(e.getX() / Main.SCALE + camera.getX()); //get mouse relative to camera position
		int my = (int)(e.getY() / Main.SCALE + camera.getY());
		
		if(Main.main.state == Main.main.menu) {
			Main.main.startMenu.clicked(mx, my);
		}
		
		if(e != null && Main.main.state == Main.main.game && Main.main.camera != null && Main.main.player != null) {


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
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
            //zoom in (amount)
			if(Main.SCALE + 0.2 <= Main.ORIGINAL_SCALE*2)
				Main.SCALE += 0.2;
			
        } else if (e.getWheelRotation() > 0){
            //zoom out (amount)
        	if(Main.SCALE - 0.2 >= Main.ORIGINAL_SCALE/1.5)
        		Main.SCALE -= 0.2;
        }

	}
	
}

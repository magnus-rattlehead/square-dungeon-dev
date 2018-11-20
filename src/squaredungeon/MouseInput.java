package squaredungeon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	
	private Handler handler;
	private Camera camera;
	private Main main;
	private SpriteSheet ss;
	
	public MouseInput(Handler handler, Camera camera, Main main, SpriteSheet ss) {
		this.handler = handler;
		this.camera = camera;
		this.main = main;
		this.ss = ss;
	}
	
	public void mousePressed(MouseEvent e) {
		float mx = (e.getX()/Main.SCALE+ camera.getX());
		float my = (e.getY()/Main.SCALE+ camera.getY());
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Weapon && Weapon.get().getIsEquipped() && Weapon.get().getMagSize() >=1) {
					handler.addObject(new Bullet(tempObject.getX(), tempObject.getY(), ID.Bullet, handler, mx, my,ss)); 
					Weapon.get().minusOneBullet();
				}
			else if(tempObject.getId()== ID.Weapon && Weapon.get().getIsEquipped() && Weapon.get().getMagSize() == 0) {
				//TODO add reload method
				break;
			}
		}
	}
}

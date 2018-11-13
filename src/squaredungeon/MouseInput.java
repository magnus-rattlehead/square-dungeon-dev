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
		int mx = (int) (e.getX()+ camera.getX());
		int my = (int) (e.getY()+ camera.getY());
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player && main.ammo >=1) { 
					handler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+16, ID.Bullet, handler, mx, my, ss)); 
					main.ammo--;
				}
			
		}
	}
}

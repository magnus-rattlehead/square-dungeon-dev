package squaredungeon;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyPlayerCheck extends GameObject{
	private Handler handler;
    private int lifeTime = 13;
    
	public EnemyPlayerCheck(double x2, double y2, ID id, Handler handler, int x3, int y3, SpriteSheet ss) {
		super(x2, y2, id, ss);
		// TODO Auto-generated constructor stub
		 this.handler = handler;

	     float bv = 20f;
	     double angle = Math.atan2(y3 - y, x3 - x);
	     this.vx = (float) ((bv) * Math.cos(angle));
	     this.vy = (float) ((bv) * Math.sin(angle));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x+= this.vx;
        y+= this.vy;
        lifeTime--;
        if(lifeTime <= 0) handler.removeObject(this);

        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Block) {
                if(getBounds().intersects(tempObject.getBounds())) handler.removeObject(this);
            }
            if(tempObject.getId() == ID.Player) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    tempObject.spottedPlayer = true;
                }
            }

        }
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		 return new Rectangle((int)x,(int)y,1,1);
	}

}

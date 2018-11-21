package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;

public class EnemyPlayerCheck extends Entity
{
	
	private Handler handler;
	private int lifeTime = 13;
	public EnemyPlayerCheck(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		
		float bv = 20f;
		double angle = Math.atan2(my+16 - this.y, mx+16 - this.x);
        this.vx = ((bv) * Math.cos(angle));
        this.vy = ((bv) * Math.sin(angle));
 
	}
	@Override
	public synchronized void tick() {
		

		if(lifeTime <= 0) handler.removeEntity(this);
		this.x+= this.vx;
		this.y+= this.vy;
		lifeTime--;
		
		for(int i = 0; i < handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);
			
	
			if(tempMob.getId() == ID.Player) {
				if(getBounds().intersects(tempMob.getBounds())) {
					tempMob.spottedPlayer = true;
					handler.removeEntity(this);	
				}
			}
			
			
		}
		
		for(int i = 0; i < handler.tile.size(); i++) {
			Tile tempTile = handler.tile.get(i);
			if(tempTile.getId() == ID.Block) {
				
					if(getBounds().intersects(tempTile.getBounds())) handler.removeEntity(this);	
				
	
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(x, y, 2, 2);
	}
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x,(int)y,1,1);
	}


}
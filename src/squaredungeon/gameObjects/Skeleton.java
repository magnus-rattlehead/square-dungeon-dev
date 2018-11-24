package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import squaredungeon.gfx.Animation;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;

public class Skeleton extends Mob{
	protected double vx, vy;
	Random r = new Random();
	private int hp = 100;
	private int animNum = 1;//TEMP ANIMATION, REPLACING WITH NEW SYSTEM SOON.
	private int counter = 1;
	private int speedMultiplier = r.nextInt(5)+5;//temp, just so enemies dont mesh inside eachother
	private BufferedImage enemy1_image;
	private Handler handler;

	//Animation anim;
	public Skeleton(int x, int y, ID id, SpriteSheet ss, Handler handler) {
		super(x, y, id, ss);
		fx = x;
		fy = y;
		this.handler = handler;
		//anim = new Animation(y, enemy1_image, enemy1_image, enemy1_image);
	}

	
	

	@Override
	public void tick() {
	
		counter++;
		if(counter % 20 == 0) {
			
			animNum++; //TEMP
			
		}
		if(animNum == 5) animNum = 1;
	
	

		for(int j =0; j < handler.mob.size(); j++) {
			Mob tempMob = handler.mob.get(j);
			if(tempMob.getId() == id.Player)
				handler.addEntity(new EnemyPlayerCheck(this.x+16, this.y+16, ID.EnemyPlayerCheck, handler, tempMob.x, tempMob.y, ss));
				// makes a enemyplayercheck angled to the player(check the enemeyplayer check class for what that does.
			if (tempMob.spottedPlayer == true && Math.hypot(tempMob.x-x, tempMob.y-fy) < 350) {//go to the spotted player if hes 350 pixels or closer
				double angle = Math.atan2(tempMob.y+16 - fy, tempMob.x+16 - fx);// angle towards the player
		        vx = (10*Math.cos(angle));//set velocity to the angle
		        vy = (10*Math.sin(angle));
		       
				if(vx!=0||vy!=0){
			        Move(vx,0);//moves if velocity isnt 0
					Move(0,vy);
			        
			        }
			}
		//anim.runAnimation();
		}
		

	for(int i =0; i < handler.entity.size(); i++) {
			
			Entity tempEntity = handler.entity.get(i);
		if(tempEntity.getId() == ID.Bullet) {
			if(getBounds().intersects(tempEntity.getBounds())) {
				this.hp-=50;
				handler.removeEntity(tempEntity);//loses 50 hp from bullet then destroys the bullet
			}
		}
	}
		
		if(hp<=0) handler.removeMob(this);//death
		
		x = (int) this.fx;//set the x and y to the float x and y (lots of decimal calculations with angles)
		y = (int) this.fy;
     
		}
		
	
	public void Move(double vx, double vy) {
		 if(!collision(vx, vy)) {
			fx+=vx/5;//move
			fy+=vy/5;
		 }
		}
		
	
	public boolean collision(double vx, double vy) {

		for(int i = 0; i< handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);

			if(tempMob.getId() == ID.Player) {
			if(new Rectangle(x+(int)vx*2,y+(int)vy*2,32,32).intersects(tempMob.getBounds())) {
				//dont go inside of the player to hit him
				return true;
				
			}
			}
	
			}
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile tempTile = handler.tile.get(i);
			if (tempTile.getId() == ID.Block) {
				if (new Rectangle(x + (int) vx, y + (int) vy, 31, 31).intersects(tempTile.getBounds())) {
					return true;//wall collision
				}
			}
	
	
		}
		return false;
	}
	@Override
	public void render(Graphics g) {
	//	g.setColor(Color.YELLOW);
		//g.fillRect((int)x, (int)y, 32, 32);
	//	g.setColor(Color.RED);
		//g.fillRect((int)this.x+(int)vx,(int)this.y+(int)vy,32,32);
		//anim.drawAnimation(g, x, y, 0);
		enemy1_image = ss.grabImage(1, animNum, 32, 32);
		if(vx>0) {
		g.drawImage(enemy1_image, x, y, 32,32,null);
		}
		else {//flips the sprite on x axis if going left
		g.drawImage(enemy1_image, x+32,y, -32,32,null);
		}
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle((int)fx-32,(int)fy-32,64,64);
	}

}
	




package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import squaredungeon.gfx.Animation;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;
import squaredungeon.main.Main;
import squaredungeon.main.Node;
import squaredungeon.main.PathFinder;
import squaredungeon.main.Vector2i;

public class Skeleton extends Mob{
	private PathFinder pathfinder;
	protected double vx, vy;
	private List<Node> path = null;
	Random r = new Random();
	private int hp = 100;
	private int animNum = 1;//TEMP ANIMATION, REPLACING WITH NEW SYSTEM SOON.
	private int counter = 1;
	private int speedMultiplier = r.nextInt(5)+5;//temp, just so enemies dont mesh inside eachother
	private BufferedImage enemy1_image;
	private Handler handler;
	private Main main;
	//Animation anim;
	public Skeleton(int x, int y, ID id, SpriteSheet ss, Handler handler, Main main) {
		super(x, y, id, ss);
		pathfinder = new PathFinder(handler);
		fx = x;
		fy = y;
		this.handler = handler;
		this.main = main;
		//anim = new Animation(y, enemy1_image, enemy1_image, enemy1_image);
	}

	
	

	@Override
	public void tick() {
	
		counter++;
		if(counter % 20 == 0) {
			
			animNum++; //TEMP
			
		}
		if(animNum == 5) animNum = 1;
	
	


//				handler.addEntity(new EnemyPlayerCheck(this.x+16, this.y+16, ID.EnemyPlayerCheck, handler, tempMob.x, tempMob.y, ss));
//				// makes a enemyplayercheck angled to the player(check the enemeyplayer check class for what that does.
//			if (tempMob.spottedPlayer == true && Math.hypot(tempMob.x-x, tempMob.y-fy) < 350) {//go to the spotted player if hes 350 pixels or closer
//				double angle = Math.atan2(tempMob.y+16 - fy, tempMob.x+16 - fx);// angle towards the player
//		        vx = (10*Math.cos(angle));//set velocity to the angle
//		        vy = (10*Math.sin(angle));
//		       
				vx = 0;
				vy = 0;
				for (int i = 0; i < handler.mob.size(); i++) {
					Mob tempMob = handler.mob.get(i);
					if(tempMob.getId() == ID.Player) {
							int px = tempMob.x;
							int py = tempMob.y;
							Vector2i start = new Vector2i(x >> 5, y >> 5);
							Vector2i destination = new Vector2i(px >> 5, py >> 5);
							path = pathfinder.findPath(start, destination);
					}
					}
					
					
							if(path != null) {
								if(path.size() > 0) {
									Vector2i vec = path.get(path.size() - 1).tile;
									if(x < vec.getX() << 5) vx+=6;
									if(x > vec.getX() << 5) vx-=6;
									if(y < vec.getY() << 5) vy+=6;
									if(y > vec.getY() << 5) vy-=6;
								}
							}
				
				
				if(vx!=0||vy!=0){
			        Move(vx,0);//moves if velocity isnt 0
					Move(0,vy);    
			    }
//			}
//		//anim.runAnimation();
//		}
		

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
			if(new Rectangle(x+(int)vx,y+(int)vy,32,32).intersects(tempMob.getBounds())) {
				//dont go inside of the player to hit him
				main.hp--;
				return true;
				
			}
			}
	
			}
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile tempTile = handler.tile.get(i);
			if (tempTile.getId() == ID.Block) {
				if (new Rectangle(x + (int) vx, y + (int) vy, 24, 24).intersects(tempTile.getBounds())) {
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
		if(vx==6) {
		g.drawImage(enemy1_image, x, y, 32,32,null);
		}
		else{//flips the sprite on x axis if going left
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
	




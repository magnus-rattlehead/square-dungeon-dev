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
import squaredungeon.net.Packet02Movement;
import squaredungeon.net.Packet03MobMovement;

public class Skeleton extends Mob{
	private PathFinder pathfinder;
	protected double vx, vy;
	private List<Node> path = null;
	Random r = new Random();
	
	private int animNum = 1;//TEMP ANIMATION, REPLACING WITH NEW SYSTEM SOON.
	private int counter = 1;
	private int speedMultiplier = r.nextInt(5)+5;//temp, just so enemies dont mesh inside eachother
	private BufferedImage enemy1_image_top;
	private BufferedImage enemy1_image_bottom;
	private Handler handler;
	private Main main;
	
	private float cooldown = 0f;
	private float cooldownDuration = 5f;
	//Animation anim;
	public Skeleton(int x, int y, ID id, SpriteSheet ss, Handler handler, int hp, Main main) {
		super(x, y, id, ss, hp);
		this.main = main;
		pathfinder = new PathFinder(handler);
		fx = x;
		fy = y;
		this.handler = handler;
		mobID = (Integer.toString(x) + Integer.toString(y));
		
		//anim = new Animation(y, enemy1_image, enemy1_image, enemy1_image);
	}

	
	

	@Override
	public void tick() {
		if(main.socketS != null) {
		cooldown += 0.1;
		counter++;
		if(counter % 20 == 0) {
			
			animNum+=2; //TEMP
			
		}
		if(animNum == 5) animNum = 1;
	
	


	
//		       
				vx = 0;
				vy = 0;
				for(Mob tempMob : handler.mob) {
					if(tempMob.getId() == ID.PLAYER && Math.hypot(tempMob.x-fx, tempMob.y-fy) < 300) {
						
handler.addEntity(new EnemyPlayerCheck(this.x+16, this.y+16, ID.ENEMYPLAYERCHECK, handler, tempMob.x, tempMob.y, ss));
//	 makes a enemyplayercheck angled to the player(check the enemeyplayer check class for what that does.

	if (tempMob.spottedPlayer == true ) {//go to the spotted player if hes 350 pixels or closer
					int px = tempMob.x+16;
					int py = tempMob.y+16;
					Vector2i start = new Vector2i(x >> 5, y >> 5);
					Vector2i destination = new Vector2i(px >> 5, py >> 5);
					path = pathfinder.findPath(start, destination);
	}
	
				
					
					
							if(path != null) {
								if(path.size() > 0) {
									Vector2i vec = path.get(path.size() - 1).tile;
									if(x < vec.getX() << 5) {vx+=5; dir  = 1;}
									if(x > vec.getX() << 5) {vx-=5;  dir = 2;}
									if(y < vec.getY() << 5) {vy+=5;  }
									if(y > vec.getY() << 5) {vy-=5;  }
								}
							}
				
			
				if(vx!=0||vy!=0){
			        Move(vx,0);//moves if velocity isnt 0
					Move(0,vy);    
			    
				}
				break;}
//			}
//		//anim.runAnimation();
//		}
		

	for(int i =0; i < handler.entity.size(); i++) {
			
			Entity tempEntity = handler.entity.get(i);
		if(tempEntity.getId() == ID.BULLET) {
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
					Packet03MobMovement packet = new Packet03MobMovement(mobID, (int)fx, (int)fy, handler);
					packet.writeData(Main.main.socketC);
					
					}
		
		}
		
	
	public void Move(double vx, double vy) {
		 if(!collision(vx, vy)) {
			fx+=vx/4;//move
			fy+=vy/4;
		 }
			
	
		}
		
	
	public boolean collision(double vx, double vy) {

		for(int i = 0; i< handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);

			if(tempMob.getId() == ID.PLAYER) {
			if(new Rectangle(x+(int)vx,y+(int)vy,24,24).intersects(tempMob.getBounds())) {
				//dont go inside of the player to hit him
				if(cooldown > cooldownDuration) {
					hit(tempMob, 20, 10);
				}
				return true;
				
			}
			}
	
			}
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile tempTile = handler.tile.get(i);
			if (tempTile.getId() == ID.SOLIDTILE) {
				if (new Rectangle(x +  (int) vx, y + (int) vy, 24, 24).intersects(tempTile.getBounds())) {
					return true;//wall collision
				}
			}
	
	
		}
		return false;
	}
	
	public void hit(Mob targetMob, int damage, int knockback) {
		cooldown = 0;
		targetMob.hp -= damage;
	}
	@Override
	public void render(Graphics g) {
	//	g.setColor(Color.YELLOW);
		//g.fillRect((int)x, (int)y, 32, 32);
	//	g.setColor(Color.RED);
		//g.fillRect((int)this.x+(int)vx,(int)this.y+(int)vy,32,32);
		//anim.drawAnimation(g, x, y, 0);
		
		enemy1_image_top = ss.grabImage(1, animNum, 32, 16);
		if(Main.main.camera.getX() < x+32 && Main.main.camera.getX()+Main.WIDTH > x+32 && Main.main.camera.getY() < y+32 && Main.main.camera.getY()+Main.HEIGHT > y+32) {
		if(dir == 1) {
		g.drawImage(enemy1_image_top, x, y, 32,16,null);
		}
		else{//flips the sprite on x axis if going left
		g.drawImage(enemy1_image_top, x+32,y, -32,16,null);
		}
	
		}
	}
	
	@Override
	public void renderBottomHalf(Graphics g) {
	//	g.setColor(Color.YELLOW);
		//g.fillRect((int)x, (int)y, 32, 32);
	//	g.setColor(Color.RED);
		//g.fillRect((int)this.x+(int)vx,(int)this.y+(int)vy,32,32);
		//anim.drawAnimation(g, x, y, 0);
		enemy1_image_bottom = ss.grabImage(1, animNum+1, 32, 16);
		if(dir == 1) {
		g.drawImage(enemy1_image_bottom, x, y+16, 32,16,null);
		}
		else{//flips the sprite on x axis if going left
		g.drawImage(enemy1_image_bottom, x+32,y+16, -32,16,null);
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
	




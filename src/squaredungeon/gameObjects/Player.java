package squaredungeon.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import squaredungeon.gfx.Animation;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;
import squaredungeon.main.Main;
import squaredungeon.net.Packet02Movement;
import squaredungeon.net.Packet03MobMovement;
import squaredungeon.net.Packet04EntityRemove;

public class Player extends Mob {
	private int spottedTimer = 1000; //how long it takes to stop the aggro of enemies
	
	public BufferedImage[] player_image_top = new BufferedImage[8];
	private BufferedImage[] player_image_bottom = new BufferedImage[8];
	public boolean moving = false;
	public BufferedImage player_image_headshot;

	private int ammo;
	private Animation anim_top;
	private Animation anim_bottom;

	private BufferedImage player_image_standing_bottom;
	private BufferedImage player_image_standing_top;
	
	public Player(int x, int y, Handler handler, ID id, SpriteSheet ss, int hp, String name) {
		super(x, y, id, ss, hp);
		this.handler = handler;
		this.name = name;
		
		player_image_standing_top = ss.grabImage(2, 1, 32, 16);
		player_image_standing_bottom = ss.grabImage(2, 2, 32, 16);
		
		player_image_top[0] = ss.grabImage(2, 3, 32, 16); 
		player_image_bottom[0] = ss.grabImage(2, 4, 32, 16);
		
		player_image_top[1] = ss.grabImage(2, 5, 32, 16); 
		player_image_bottom[1] = ss.grabImage(2, 6, 32, 16);
		
		player_image_top[2] = ss.grabImage(2, 7, 32, 16); 
		player_image_bottom[2] = ss.grabImage(2,8, 32, 16);
		
		player_image_top[3] = ss.grabImage(2, 9, 32, 16); 
		player_image_bottom[3] = ss.grabImage(2, 10, 32, 16);
		
		player_image_top[4] = ss.grabImage(2, 11, 32, 16); 
		player_image_bottom[4] = ss.grabImage(2, 12, 32, 16);
		
		player_image_top[5] = ss.grabImage(2, 13, 32, 16); 
		player_image_bottom[5] = ss.grabImage(2, 14, 32, 16);
		
		player_image_top[6] = ss.grabImage(2, 15, 32, 16); 
		player_image_bottom[6] = ss.grabImage(2, 16, 32, 16);
		
		player_image_top[7] = ss.grabImage(2, 17, 32, 16); 
		player_image_bottom[7] = ss.grabImage(2, 18, 32, 16);
		
		player_image_headshot = ss.grabImage(7, 1, 13, 12);
		// player_image[1] = ss.grabImage(0, 0, 32, 32); //placeholder
		// player_image[2] = ss.grabImage(0, 0, 32, 32); //placeholder
		// ...
		anim_top = new Animation(8, player_image_top[0], player_image_top[1], player_image_top[2], player_image_top[3], player_image_top[4], player_image_top[5], player_image_top[6], player_image_top[7]);
		anim_bottom = new Animation(8, player_image_bottom[0], player_image_bottom[1], player_image_bottom[2], player_image_bottom[3], player_image_bottom[4], player_image_bottom[5], player_image_bottom[6], player_image_bottom[7]);
		// anim = new Animation(1, player_image[0], player_image[0]);
		
	}

	@Override
	public void tick() {

		 anim_top.runAnimation();
		 anim_bottom.runAnimation();
			
		if(hp <= 0)
			handler.removeMob(this);
		if (spottedTimer <= 0) { //this is just how long it takes to stop the aggro of enemies
		
			this.spottedPlayer = false;
			spottedTimer = 1000;
		}
		if (this.spottedPlayer == true)
			spottedTimer--;

		if(handler!=null) {
		collision(vx, vy); //check collisions 

		vx = 0; //reset velocity
		vy = 0;
		// movement
		
		if (handler.isUp()) {//setting velocity
			vy -= 3;
			
		}

		if (handler.isDown()) {
			vy +=3;
		
		}

		if (handler.isRight()) {
			vx += 3;
			dir = 1;
			
		}

		if (handler.isLeft()) {
			vx -= 3;
			dir = 2;
			
		}

		if (vx != 0 || vy != 0) {
	
			Move(vx, 0);
			Move(0, vy);
			moving = true;
	
		}
		else {
			moving = false;
		}
		
		
	
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity tempEntity = handler.entity.get(i);
		if (tempEntity.getId() == ID.CRATE) {
			if(Main.main.socketC != null || Main.main.socketS != null) {
			if (getBounds().intersects(tempEntity.getBounds())) {
				this.ammo += 50;
				handler.removeEntity(tempEntity); //increase ammo when player touches an ammo crate
				Packet04EntityRemove packet = new Packet04EntityRemove(tempEntity.getEntityID());
				
				if(Main.main.socketS != null)
					packet.writeData(Main.main.socketS);
				else
					packet.writeData(Main.main.socketC);
			}
			}
		}
		if (tempEntity.getId() == ID.ENEMYPLAYERCHECK) {
			if (getBounds().intersects(tempEntity.getBounds())) {
				spottedPlayer = true;
				handler.removeEntity(tempEntity); //player gets spotted if he gets hit by the enemy check object (check the EnemyPlayerCheck class for details)
			}
		}

		}
		}
		Packet02Movement packet = new Packet02Movement(this.getUsername(), this.x, this.y, moving);
		packet.writeData(Main.main.socketC);
		}
	

	public boolean collision(double vx, double vy) {
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile tempTile = handler.tile.get(i);
			if (tempTile.getId() == ID.SOLIDTILE) {
				if (new Rectangle(x + 10 +(int) vx, y +16 + (int) vy, 13, 1).intersects(tempTile.getBounds())) {
					return true; //works ahead of the next movement to make sure your next movement doesnt allow you to go inside of a wall
				}
			}
	
	
		}
		for (int i = 0; i < handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);
		if (tempMob.getId() == ID.ENEMY) {
			if (getBounds().intersects(tempMob.getBounds())) {
			//	main.hp--;// TODO make it so you cant get stuck in enemies and they drain all your health
				
			}
		}
		}
		
		return false;
	}
	public void Move(double vx, double vy) {
		
		if (!collision(vx, vy)) {
			
			this.x += vx; //if you arent touching a wall, move by the velocity
			this.y += vy;
		
		
		}

	}


	@Override
	public void render(Graphics g) {
		// g.setColor(Color.BLUE);
		// g.fillRect((int)x,(int)y, 32,32);
		if(Main.main.camera.getX() < x+32 && Main.main.camera.getX()+Main.WIDTH > x+32 && Main.main.camera.getY() < y+32 && Main.main.camera.getY()+Main.HEIGHT > y+32) {
		
		if(moving) {
		if(dir==1) {
			anim_top.drawAnimation(g, x, y, 32, 16, 0);
			//g.drawImage(player_image_top, x, y, 32, 16, null);
		}
		else {
			anim_top.drawAnimation(g, x+32, y, -32, 16, 0);
			//g.drawImage(player_image_top, x+32, y, -32, 16, null);
		}
		}
		else {
			if(dir==1) {
				g.drawImage(player_image_standing_top, x, y, 32, 16, null);
			}
			else {
				g.drawImage(player_image_standing_top, x+32, y, -32, 16, null);
			}
		}
		}
		// if(vx ==0 && vy ==0) g.drawImage(player_image[0], x, y, null);
		// anim.drawAnimation(g,x,y,1);
		
	}

	@Override
	public void renderBottomHalf(Graphics g) {
		// g.setColor(Color.BLUE);
		// g.fillRect((int)x,(int)y, 32,32);
		if(moving) {
		if(dir==1) {
			anim_bottom.drawAnimation(g, x, y+16, 32, 16, 0);
		//	g.drawImage(player_image_bottom, x, y+16, 32, 16, null);
		}
		else {
			anim_bottom.drawAnimation(g, x+32, y+16, -32, 16, 0);
		//	g.drawImage(player_image_bottom, x+32, y+16, -32, 16, null);
		}
	}
	else {
		if(dir==1) {
			g.drawImage(player_image_standing_bottom, x, y+16, 32, 16, null);
		}
		else {
			g.drawImage(player_image_standing_bottom, x+32, y+16, -32, 16, null);
		}
	}
		// if(vx ==0 && vy ==0) g.drawImage(player_image[0], x, y, null);
		// anim.drawAnimation(g,x,y,1);
	}


	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	public String getUsername() {
		return name;
	}

}

package squaredungeon.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import squaredungeon.gfx.Animation;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;
import squaredungeon.main.Main;

public class Player extends Mob {
	private int spottedTimer = 1000; //how long it takes to stop the aggro of enemies
	private BufferedImage player_image_top;// replace 0 with amount of player models
	private BufferedImage player_image_bottom;
	private int ammo;
	Animation anim;
	private int dir;
	
	public Player(int x, int y, Handler handler, ID id, SpriteSheet ss, int hp) {
		super(x, y, id, ss, hp);
		this.handler = handler;
		player_image_top = ss.grabImage(2, 1, 32, 16); // placeholder
		player_image_bottom = ss.grabImage(2, 2, 32, 16);
		// player_image[1] = ss.grabImage(0, 0, 32, 32); //placeholder
		// player_image[2] = ss.grabImage(0, 0, 32, 32); //placeholder
		// ...

		// anim = new Animation(1, player_image[0], player_image[0]);
	}

	@Override
	public void tick() {
		if (spottedTimer <= 0) { //this is just how long it takes to stop the aggro of enemies
		
			this.spottedPlayer = false;
			spottedTimer = 1000;
		}
		if (this.spottedPlayer == true)
			spottedTimer--;


		collision(vx, vy); //check collisions 

		vx = 0; //reset velocity
		vy = 0;
		// movement
		if (handler.isUp()) {//setting velocity
			vy -= 3;
			
		}
		// else if(!handler.isDown()) vy = 0;

		if (handler.isDown()) {
			vy +=3;
		}
		// else if(!handler.isUp()) vy = 0;

		if (handler.isRight()) {
			vx += 3;
			dir = 1;
		}
		// else if(!handler.isLeft()) vx = 0;

		if (handler.isLeft()) {
			vx -= 3;
			dir = 2;
		}
		// else if(!handler.isRight()) vx = 0;

		if (vx != 0 || vy != 0) {
			Move(vx, 0);
			Move(0, vy);
		}
		// anim.runAnimation();
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity tempEntity = handler.entity.get(i);
		if (tempEntity.getId() == ID.CRATE) {
			if (getBounds().intersects(tempEntity.getBounds())) {
				this.ammo += 50;
				handler.removeEntity(tempEntity); //increase ammo when player touches an ammo crate
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

	public boolean collision(double vx, double vy) {
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile tempTile = handler.tile.get(i);
			if (tempTile.getId() == ID.SOLIDTILE) {
				if (new Rectangle(x + 10 +(int) vx, y + 16 + (int) vy, 13, 1).intersects(tempTile.getBounds())) {
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
		if(dir==1) {
			g.drawImage(player_image_top, x, y, 32, 16, null);
		}
		else {
			g.drawImage(player_image_top, x+32, y, -32, 16, null);
		}
		
		// if(vx ==0 && vy ==0) g.drawImage(player_image[0], x, y, null);
		// anim.drawAnimation(g,x,y,1);
		
	}

	@Override
	public void renderBottomHalf(Graphics g) {
		// g.setColor(Color.BLUE);
		// g.fillRect((int)x,(int)y, 32,32);
		if(dir==1) {
			g.drawImage(player_image_bottom, x, y+16, 32, 16, null);
		}
		else {
			g.drawImage(player_image_bottom, x+32, y+16, -32, 16, null);
		}
		// if(vx ==0 && vy ==0) g.drawImage(player_image[0], x, y, null);
		// anim.drawAnimation(g,x,y,1);
	}


	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}

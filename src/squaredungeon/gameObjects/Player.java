package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import squaredungeon.gfx.Animation;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;
import squaredungeon.main.Main;

public class Player extends Mob {


	Main main;
	private int spottedTimer = 1000; //how long it takes to stop the aggro of enemies
	private BufferedImage player_image;// replace 0 with amount of player models
	Animation anim;
	
	public Player(int x, int y, Handler handler, ID id, Main main, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.main = main;
		player_image = ss.grabImage(2, 1, 32, 32); // placeholder
		
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
		if (handler.isUp()) //setting velocity
			vy -= 2;
		// else if(!handler.isDown()) vy = 0;

		if (handler.isDown())
			vy += 2;
		// else if(!handler.isUp()) vy = 0;

		if (handler.isRight())
			vx += 2;
		// else if(!handler.isLeft()) vx = 0;

		if (handler.isLeft())
			vx -= 2;
		// else if(!handler.isRight()) vx = 0;

		if (vx != 0 || vy != 0) {
			Move(vx, 0);
			Move(0, vy);
		}
		// anim.runAnimation();
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity tempEntity = handler.entity.get(i);
		if (tempEntity.getId() == ID.Crate) {
			if (getBounds().intersects(tempEntity.getBounds())) {
				main.ammo += 50;
				handler.removeEntity(tempEntity); //increase ammo when player touches an ammo crate
			}
		}
		if (tempEntity.getId() == ID.EnemyPlayerCheck) {
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
			if (tempTile.getId() == ID.Block) {
				if (new Rectangle(x + (int) vx, y + (int) vy, 31, 31).intersects(tempTile.getBounds())) {
					return true; //works ahead of the next movement to make sure your next movement doesnt allow you to go inside of a wall
				}
			}
	
	
		}
		for (int i = 0; i < handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);
		if (tempMob.getId() == ID.Enemy) {
			if (getBounds().intersects(tempMob.getBounds())) {
				main.hp--;// TODO make it so you cant get stuck in enemies and they drain all your health
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
		g.drawImage(player_image, x, y, 32, 32, null);
		// if(vx ==0 && vy ==0) g.drawImage(player_image[0], x, y, null);
		// anim.drawAnimation(g,x,y,1);
	}



	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}

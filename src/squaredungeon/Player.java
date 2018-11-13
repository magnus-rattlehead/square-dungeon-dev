package squaredungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject{

	Handler handler;
	Main main;
	
	private BufferedImage[] player_image = new BufferedImage[0]; // replace 0 with amount of player models

	Animation anim;
	
	public Player(int x, int y, ID id, Handler handler, Main main, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.main = main;
		
		//player_image[0] = ss.grabImage(0, 0, 32, 32); //placeholer
		//player_image[1] = ss.grabImage(0, 0, 32, 32); //placeholer
		//player_image[2] = ss.grabImage(0, 0, 32, 32); //placeholer
		//...
		
		//anim = new Animation(3, player_image[0], player_image[1] ...);
	}

	@Override
	public void tick() {
		x+=vx;
		y+=vy;
		
		collision();
		//movement
		if(handler.isUp()) vy = -5;
		else if(!handler.isDown()) vy = 0;
		
		if(handler.isDown()) vy = 5;
		else if(!handler.isUp()) vy = 0;
		
		if(handler.isRight()) vx = 5;
		else if(!handler.isLeft()) vx = 0;
		
		if(handler.isLeft()) vx = -5;
		else if(!handler.isRight()) vx = 0;
		
		//anim.runAnimation();
		
	}
	private void collision() {
		for(int i = 0; i< handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x +=-vx;
					y +=-vy;
				}
			
			}
			if(tempObject.getId() == ID.Crate) {
				if(getBounds().intersects(tempObject.getBounds())) {
					main.ammo+=50;
					handler.removeObject(tempObject);
				}
			}	
			if(tempObject.getId() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					main.hp--;//TODO make it so you cant get stuck in enemies and they drain all your health
				}
			}
		}
	}
	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)x,(int)y, 32,32);
		//if(vx ==0 && vy ==0) g.drawImage(player_image[0], x, y, null);
		//else anim.drawAnimation(g,x,y,0);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,32,32);
	}

}

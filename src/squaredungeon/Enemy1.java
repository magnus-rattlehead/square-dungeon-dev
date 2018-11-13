package squaredungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy1 extends GameObject{
	private Handler handler;
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	
	private BufferedImage[] enemy1_image = new BufferedImage[0];
	Animation anim;
	
	public Enemy1(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		
		//enemy1_image[0] = ss.grabImage(0, 0, 32, 32);
		//enemy1_image[1] = ss.grabImage(0, 0, 32, 32);
		//enemy1_image[2] = ss.grabImage(0, 0, 32, 32);
		//...
		
		//anim = new Animation(3, enemy1_image[0], enemy1_image[1]....);
	}

	@Override
	public void tick() {
		x+=vx;
		y+=vy;
		
		choose = r.nextInt(10);
		for(int i =0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBoundsBig().intersects(tempObject.getBounds())){
					x+=-(vx*2);
					y+=-(vy*2);
					vx*=-1;
					vy*=-1;
				}else if(choose ==0) {
					vx = (r.nextInt(4 - -4) + -4);
					vy = (r.nextInt(4 - -4) + -4);
				}
			}
			
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					hp-=50; handler.removeObject(tempObject);
				}
			}
		}
		//anim.runAnimation();
		if(hp<=0) handler.removeObject(this);
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 32, 32);
		//anim.drawAnimation(g, x, y, 0);
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y,32,32);
	}
	
	public Rectangle getBoundsBig() {
		// TODO Auto-generated method stub
		return new Rectangle(x-16,y-16,64,64);
	}

}

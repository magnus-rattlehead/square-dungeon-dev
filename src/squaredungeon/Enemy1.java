package squaredungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy1 extends GameObject{
	private Handler handler;
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
		/*
		x+=vx;
		y+=vy;*/
		
		for(int i =0; i < handler.object.size(); i++) {

            	GameObject tempObject = handler.object.get(i);
            	if(tempObject.getId() == ID.Player) {
            		handler.addObject(new EnemyPlayerCheck(this.x+16, this.y+16, ID.EnemyPlayerCheck, handler, (int)tempObject.x, (int)tempObject.y, ss));

            }
            	if(tempObject.getId() == ID.Bullet) {
                    if(getBounds().intersects(tempObject.getBounds())) {
                        hp-=50; handler.removeObject(tempObject);
                    }
                }
            if (tempObject.spottedPlayer == true && Math.hypot(tempObject.x-x, tempObject.y-y) < 500) {
                double angle = Math.atan2(tempObject.y - y, tempObject.x - x);
                this.vx = (float) (25*Math.cos(angle));
                this.vy = (float) (25*Math.sin(angle));

                if(this.vx!=0||this.vy!=0){
                	Move(this.vx,0);
                	Move(0,this.vy);
                }
            }
		}
		//anim.runAnimation();
		if(hp<=0) handler.removeObject(this);
		
	}
	
	public void Move(double vx, double vy) {
        if(!dirCollide(this.vx, this.vy)) {
           this.x+=this.vx/25;
           this.y+=this.vy/25;
        }
       }


   public boolean dirCollide(double vx, double vy) {

       for(int i = 0; i< handler.object.size(); i++) {
           GameObject tempObject = handler.object.get(i);
           
           if(tempObject.getId() == ID.Block) {
        	   if(new Rectangle((int)this.x+(int)vx*2,(int)this.y+(int)vy*2,32,32).intersects(tempObject.getBounds())) {
        		   return true;
        	   }


           } 
  
        }
       return false;
   }

	@Override
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)x, (int)y, 32, 32);
		//anim.drawAnimation(g, x, y, 0);
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x,(int)y,32,32);
	}
	
	public Rectangle getBoundsBig() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x-16,(int)y-16,64,64);
	}

}

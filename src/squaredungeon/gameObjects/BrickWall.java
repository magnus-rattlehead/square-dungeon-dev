package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;
import squaredungeon.main.Main;
import squaredungeon.particles.TorchLight;

public class BrickWall extends Tile {

	private Random r;
	private int counter = 97;
	private BufferedImage block_image;
	private BufferedImage block_image1, block_image2, block_image3, block_image4, block_image5, block_image6,
			block_image7, block_image8, block_image9, block_image10, block_image11, block_image12, block_image13, block_image14, block_image15;
	private Handler handler;
	private boolean checkedRight = false; // checks if there are blocks on right
	private boolean checkedLeft = false;
	private boolean checkedUp = false;
	private boolean checkedDown = false;
	private boolean checkedDown_walkway = false;
	private boolean checkedUp_walkway = false;
	private boolean checkedRight_walkway = false;
	private boolean checkedLeft_walkway = false;
	private Main main;
	private boolean flip = false;
	private boolean activated = false;;


	public BrickWall(int x, int y, ID id, SpriteSheet ss, Handler handler, Main main) {
		super(x, y, id, ss);
		this.main = main;
		this.handler = handler;
		this.WalkwayTile = false;
		r = new Random();
		
		block_image = ss.grabImage(30, 2, 32, 32); // all the images of the block depending on neighbouring blocks
		block_image1 = ss.grabImage(30, 1, 32, 32);
		block_image2 = ss.grabImage(29, 1, 32, 32);
		block_image3 = ss.grabImage(29, 3, 32, 32);
		block_image4 = ss.grabImage(29, 5, 32, 32);
		block_image5 = ss.grabImage(30, 2, 32, 32);
		block_image6 = ss.grabImage(30, 5, 32, 32);
		block_image7 = ss.grabImage(29, 2, 32, 32);
		block_image8 = ss.grabImage(30, 3, 32, 32);
		block_image9 = ss.grabImage(30, 4, 32, 32);
		block_image10 = ss.grabImage(29, 6, 32, 32);
		block_image11 = ss.grabImage(30, 4, 32, 32);
		block_image12 = ss.grabImage(30, 6, 32, 32);
		block_image13 = ss.grabImage(29, 4, 32, 32);
		block_image14 = ss.grabImage(29, 7, 32, 32);
		block_image15 = ss.grabImage(30, 7, 32, 32);

	}

	@Override
	public void tick() {

	}

	@Override
	public synchronized void render(Graphics g) {
		counter++;
		if(counter % 20 == 0) {//every 100 ticks, check if the neighbours are still gucci (just incase there was an error when loading the level if the user has a shitty pc)
			switch (this.checkForNeighbours(this.x, this.y)) { //loads different images for different neighbouring blocks
			case 1:
				block_image = block_image1;
				break;
			case 2:
				block_image = block_image2;
				flip = true;
				break;
			case 3:
				block_image = block_image3;
				this.WalkwayTile = true;
				flip = true;
				break;
			case 4:
				block_image = block_image3;
				this.WalkwayTile = true;
				break;
			case 5:
				this.WalkwayTile = true;
				block_image = block_image4;
				break;
			case 6:
				block_image = block_image5;
				if(y< (main.level_height*32)-32)
				{
				int r1 = r.nextInt(100);
				if(!activated  && (r1 <= 10)) {
					handler.addEntity(new TorchLight(x+16, y+16, ID.TORCH, main.ssEntity, 50));
					activated = true;
				}
				else if(!activated && r1 == 11) {
					block_image5 = ss.grabImage(29, 8, 32, 32);
					activated = true;
				}
				else if(!activated && r1 == 13) {
					block_image5 = ss.grabImage(30, 8, 32, 32);
					activated = true;
				}
				else {
					activated = true;
				}
			}
				break;
			case 7:
				this.WalkwayTile = true;
				block_image = block_image6;
				break;
			case 8:
				block_image = block_image7;
				this.WalkwayTile = true;
				flip = true;
				break;
			case 9:
				block_image = block_image8;
				flip = true;
				break;
			case 10:
				block_image = block_image7;
				this.WalkwayTile = true;
				break;
			case 11:
				block_image = block_image8;
				break;
			case 12:
				block_image = block_image9;
				break;
			case 13:
				this.WalkwayTile = true;
				block_image = block_image10;
				break;
			case 14:
				block_image = block_image11;
				this.WalkwayTile = true;
				flip = true;
				break;
			case 15:
				block_image = block_image11;
				this.WalkwayTile = true;
				break;
			case 16:
				block_image = block_image12;
				break;
			case 17:
				block_image = block_image2;
				break;
			case 18:
				block_image = block_image13;
				break;
			case 19:
				this.WalkwayTile = true;
				flip = true;
				block_image = block_image9;
				break;
			case 20:
				this.WalkwayTile = true;
				flip = true;
				block_image = block_image14;
				break;
			case 21:
				this.WalkwayTile = true;
				block_image = block_image14;
				break;
			case 22:
				this.WalkwayTile = true;
				block_image = block_image15;
				break;
			}
		}
		if(Main.main.camera.getX() < x+32 && Main.main.camera.getX()+Main.WIDTH > x && Main.main.camera.getY() < y+32 && Main.main.camera.getY()+Main.HEIGHT > y) {
			if (!flip) {
				g.drawImage(block_image, x, y, 32, 32, null); //draw that shit
			}
	
			else {
				g.drawImage(block_image, x + 32, y, -32, 32, null); //flips horizontally
			}
		}
	}
	// g.fillRect(rightRect.x,rightRect.y,32,32);

	public Rectangle getBounds() {
				return new Rectangle(x, y, 32, 32);
	}

	protected synchronized int checkForNeighbours(int x, int y) {
		int check = 0;
		for (int i = 0; i < handler.tile.size(); i++) {

			Tile tempTile0 = handler.tile.get(i);
			if (tempTile0.id == ID.SOLIDTILE) {
				if (new Rectangle(x + 32, y, 32, 32).intersects(tempTile0.getBounds())) { //all of this just checks for tiles neighbouring
					checkedRight = true;
					if(tempTile0.WalkwayTile == true) {
						checkedRight_walkway = true;
					}
				}
			}

			Tile tempTile1 = handler.tile.get(i);
			if (tempTile1.id == ID.SOLIDTILE) {
				if (new Rectangle(x - 32, y, 32, 32).intersects(tempTile1.getBounds())) {
					checkedLeft = true;
					if(tempTile1.WalkwayTile == true) {
						checkedLeft_walkway = true;
					}
				}
			}

			Tile tempTile2 = handler.tile.get(i);
			if (tempTile2.id == ID.SOLIDTILE) {
				if (new Rectangle(x, y + 32, 32, 32).intersects(tempTile2.getBounds())) {
					checkedDown = true;
					if(tempTile2.WalkwayTile == true) {
						checkedDown_walkway = true;
					}
				}

			}

			Tile tempTile3 = handler.tile.get(i);
			if (tempTile3.id == ID.SOLIDTILE) {
				if (new Rectangle(x, y - 32, 32, 32).intersects(tempTile3.getBounds())) {
					checkedUp = true;
					if(tempTile3.WalkwayTile == true) {
						checkedUp_walkway = true;
					}
				}
			}
		}
		//checks all possibilities using intersections with a new rectangle
		if (checkedRight && checkedLeft && checkedUp && checkedDown) {
			if(checkedUp_walkway && checkedDown_walkway && !checkedRight_walkway && !checkedLeft_walkway)
				return 7;
			if(!checkedUp_walkway && checkedDown_walkway && !checkedRight_walkway && !checkedLeft_walkway)
				return 7;
			if(!checkedUp_walkway && !checkedDown_walkway && !checkedRight_walkway && !checkedLeft_walkway)
				return 7;
			if(checkedUp_walkway && checkedRight_walkway && checkedLeft_walkway && checkedDown_walkway)
				return 2;
			if(checkedUp_walkway && checkedRight_walkway && checkedLeft_walkway && !checkedDown_walkway)
				return 22;
			return 1;
		}
		if (!checkedRight && !checkedLeft && !checkedUp && !checkedDown) {
			return 2;
		}
		if (!checkedRight && checkedLeft && checkedUp && checkedDown) {
			if(checkedUp_walkway && checkedDown_walkway && !checkedRight_walkway && !checkedLeft_walkway)
				return 7;
			if(checkedUp_walkway && checkedDown_walkway && !checkedRight_walkway && checkedLeft_walkway)
				return 14;
			if(checkedUp_walkway && !checkedDown_walkway && !checkedRight_walkway && checkedLeft_walkway)
				return 20;
			return 3;
		}
		if (checkedRight && !checkedLeft && checkedUp && checkedDown) {
			if(checkedUp_walkway && checkedDown_walkway && !checkedRight_walkway && !checkedLeft_walkway)
				return 7;
			if(checkedUp_walkway && checkedDown_walkway && checkedRight_walkway && !checkedLeft_walkway)
				return 15;
			if(checkedUp_walkway && !checkedDown_walkway && checkedRight_walkway && !checkedLeft_walkway)
				return 21;
			return 4;
		}
		if (checkedRight && checkedLeft && !checkedUp && checkedDown) {
			if(!checkedUp_walkway && checkedDown_walkway && checkedRight_walkway && checkedLeft_walkway)
				return 18;
			return 5;
		}
		if (checkedRight && checkedLeft && checkedUp && !checkedDown) {
			if(checkedUp_walkway && !checkedDown_walkway && checkedRight_walkway && checkedLeft_walkway)
				return 22;
			return 6;
		}
		if (!checkedRight && !checkedLeft && checkedUp && checkedDown) {
			return 7;
		}
		if (!checkedRight && checkedLeft && !checkedUp && checkedDown) {
			if(!checkedUp_walkway && checkedDown_walkway && !checkedRight_walkway && checkedLeft_walkway)
				return 3;
			return 8;
		}
		if (!checkedRight && checkedLeft && checkedUp && !checkedDown) {
			return 9;
		}
		if (checkedRight && !checkedLeft && !checkedUp && checkedDown) {
			if(!checkedUp_walkway && checkedDown_walkway && checkedRight_walkway && !checkedLeft_walkway)
				return 4;
			return 10;
		}
		if (checkedRight && !checkedLeft && checkedUp && !checkedDown) {
			return 11;
		}
		if (checkedRight && checkedLeft && !checkedUp && !checkedDown) {
			return 12;
		}
		if (!checkedRight && !checkedLeft && !checkedUp && checkedDown) {
			return 13;
		}
		if (!checkedRight && checkedLeft && !checkedUp && !checkedDown) {
			return 14;
		}
		if (checkedRight && !checkedLeft && !checkedUp && !checkedDown) {
			return 15;
		}
		if (!checkedRight && !checkedLeft && checkedUp && !checkedDown) {
			return 16;
		}

		return check;

	}

}

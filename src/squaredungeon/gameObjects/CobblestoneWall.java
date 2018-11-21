package squaredungeon.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;

public class CobblestoneWall extends Tile {

	private boolean activated = false;
	private BufferedImage block_image;
	private BufferedImage block_image1, block_image2, block_image3, block_image4, block_image5, block_image6,
			block_image7, block_image8, block_image9, block_image10, block_image11, block_image12, block_image13;
	private Handler handler;
	private boolean checkedRight = false;
	private boolean checkedLeft = false;
	private boolean checkedUp = false;
	private boolean checkedDown = false;

	private boolean flip = false;

	public CobblestoneWall(int x, int y, ID id, SpriteSheet ss, Handler handler) {
		super(x, y, id, ss);
		this.handler = handler;

		block_image = ss.grabImage(28, 2, 32, 32); // placeholer, replace later
		block_image1 = ss.grabImage(28, 1, 32, 32);
		block_image2 = ss.grabImage(27, 1, 32, 32);
		block_image3 = ss.grabImage(27, 3, 32, 32);
		block_image4 = ss.grabImage(27, 5, 32, 32);
		block_image5 = ss.grabImage(28, 2, 32, 32);
		block_image6 = ss.grabImage(28, 5, 32, 32);
		block_image7 = ss.grabImage(27, 2, 32, 32);
		block_image8 = ss.grabImage(28, 3, 32, 32);
		block_image9 = ss.grabImage(28, 2, 32, 32);
		block_image10 = ss.grabImage(28, 4, 32, 32);
		block_image11 = ss.grabImage(27, 6, 32, 32);
		block_image12 = ss.grabImage(27, 4, 32, 32);
		block_image13 = ss.grabImage(28, 6, 32, 32);

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void render(Graphics g) {
		// TODO Auto-generated method stub

		if (!this.activated && this.checkForNeighbours(this.x, this.y) == 1) {// all neighbours
			block_image = block_image1;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 2) {// no neighbours
			block_image = block_image2;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 3) {// done
			block_image = block_image3;
			flip = true;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 4) {// done
			block_image = block_image3;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 5) {
			block_image = block_image4;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 6) {
			block_image = block_image5;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 7) {
			block_image = block_image6;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 8) {
			flip = true;
			block_image = block_image7;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 9) {
			flip = true;
			block_image = block_image8;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 10) {
			block_image = block_image7;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 11) {
			block_image = block_image8;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 12) {
			block_image = block_image9;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 13) {
			block_image = block_image11;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 14) {
			flip = true;
			block_image = block_image12;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 15) {
			block_image = block_image12;
			this.activated = true;
		} else if (!this.activated && this.checkForNeighbours(this.x, this.y) == 16) {
			block_image = block_image13;
			this.activated = true;
		}

		if (!flip) {
			g.drawImage(block_image,   x,   y, 32, 32, null);
		}

		else {
			g.drawImage(block_image,   x + 32,   y, -32, 32, null);
		}
	}
	// g.fillRect(rightRect.x,rightRect.y,32,32);

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(  x,   y, 32, 32);
	}

	protected synchronized int checkForNeighbours(int x, int y) {
		for (int i = 0; i < handler.tile.size(); i++) {

			Tile tempTile0 = handler.tile.get(i);
			if (tempTile0.id == ID.Block) {
				if (new Rectangle(  x + 32,   y, 32, 32).intersects(tempTile0.getBounds())) {
					checkedRight = true;
				}
			}

			Tile tempTile1 = handler.tile.get(i);
			if (tempTile1.id == ID.Block) {
				if (new Rectangle(  x - 32,   y, 32, 32).intersects(tempTile1.getBounds())) {
					checkedLeft = true;
				}
			}

			Tile tempTile2 = handler.tile.get(i);
			if (tempTile2.id == ID.Block) {
				if (new Rectangle(  x,   y + 32, 32, 32).intersects(tempTile2.getBounds())) {
					checkedDown = true;
				}

			}

			Tile tempTile3 = handler.tile.get(i);
			if (tempTile3.id == ID.Block) {
				if (new Rectangle(  x,   y - 32, 32, 32).intersects(tempTile3.getBounds())) {
					checkedUp = true;
				}
			}
		}
		if (checkedRight && checkedLeft && checkedUp && checkedDown) {
			return 1;
		}
		if (!checkedRight && !checkedLeft && !checkedUp && !checkedDown) {
			return 2;
		}
		if (!checkedRight && checkedLeft && checkedUp && checkedDown) {
			return 3;
		}
		if (checkedRight && !checkedLeft && checkedUp && checkedDown) {
			return 4;
		}
		if (checkedRight && checkedLeft && !checkedUp && checkedDown) {
			return 5;
		}
		if (checkedRight && checkedLeft && checkedUp && !checkedDown) {
			return 6;
		}
		if (!checkedRight && !checkedLeft && checkedUp && checkedDown) {
			return 7;
		}
		if (!checkedRight && checkedLeft && !checkedUp && checkedDown) {
			return 8;
		}
		if (!checkedRight && checkedLeft && checkedUp && !checkedDown) {
			return 9;
		}
		if (checkedRight && !checkedLeft && !checkedUp && checkedDown) {
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

		return 0;

	}

}

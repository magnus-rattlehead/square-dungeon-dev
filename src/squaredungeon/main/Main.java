/**

Square Dungeon - by Stivi Guranjaku and Pablo Lafontaine
TODO:
1. Create Sprite sheet (Pablo)
2. Tie loose ends in code(bullets done, collision done)
3. Enemy AI (A* MUST DO)
4. Add win condition(DONE)
5. Add multiple levels (must add method to change levels)
6. Redesign levels? (Isometric angle - Nuclear throne)

Rest of the TODO is in the Weapon class
... to be continued

**/
package squaredungeon.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import squaredungeon.gameObjects.CobblestoneWall;
import squaredungeon.gameObjects.BrickWall;
import squaredungeon.gameObjects.Camera;
import squaredungeon.gameObjects.Crate;
import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Mob;
import squaredungeon.gameObjects.NetPlayer;
import squaredungeon.gameObjects.Player;
import squaredungeon.gameObjects.PlayerInfo;
import squaredungeon.gameObjects.Skeleton;
import squaredungeon.gameObjects.Weapon;
import squaredungeon.gfx.BufferedImageLoader;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.net.GameClient;
import squaredungeon.net.GameServer;
import squaredungeon.net.Packet00Join;
import squaredungeon.net.Packet03MobMovement;
import squaredungeon.particles.Fog;
import squaredungeon.particles.TorchLight;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Main main;
	//dimensions of the screen
	public static final float SCALE = 4.6f; //TODO a slider to increase/decrease this value, maybe bound to scroll wheel?
	public static final int WIDTH = (int) Math.ceil((screenSize.getWidth()/SCALE));
	public static final int HEIGHT = (int) (screenSize.getHeight()/SCALE);

	private boolean running = false; //game loop shit
	private Thread thread;
	
	public Game game;
	public Handler handler;
	public WindowHandler winHandler;
	private static BufferedImageLoader loader = new BufferedImageLoader();
	public static BufferedImage level = null;
	
	private static BufferedImage sprite_sheet_tiles = null; //use the correct sprite sheet for the sprites pls thx boss
	private static BufferedImage sprite_sheet_mob = null;
	private static BufferedImage sprite_sheet_entity = null;
	private static BufferedImage sprite_sheet_effects = null;
	
	public Camera camera;
	public SpriteSheet ssTile;
	public SpriteSheet ssMob;
	public SpriteSheet ssEntity;
	public SpriteSheet ssEffect;
	private BufferedImage grass1,grass2,grass3,grass4,grass5,fogHorizontal, fogVerticle = null;

	private final int LEVEL_1_NUM_OF_ENEMIES = 40; //stivi this shouldnt exist xd
	private final int LEVEL_2_NUM_OF_ENEMIES = 40;

	

	public int ammo = 100;
	public int hp = 100;
	public int currentLevel = 1;
	public static int numOfEnemies = 0;
	public static boolean levelComplete = false;
	public static boolean checkLoopDone = false;
	public int level_width, level_height;
	
	//network
	public GameClient socketC;
	public GameServer socketS;
	
	
	// weapons and other items taht will be initialized in initItems
	public static Weapon pistol;
	public static Weapon smg;
	public static Weapon shotgun;
	public static Weapon rifle;
	public static Weapon sniper;
	public JFrame frame;
	
	public NetPlayer player; 
	
	
	public Main() {
		main = this;
		
		frame = new JFrame();
		frame.setPreferredSize(new Dimension((int) (WIDTH*SCALE), (int) (HEIGHT*SCALE)));
		frame.setMaximumSize(new Dimension((int) (WIDTH*SCALE), (int) (HEIGHT*SCALE)));
		frame.setMinimumSize(new Dimension((int) (WIDTH*SCALE), (int) (HEIGHT*SCALE)));
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		start();

		handler = new Handler();
		winHandler = new WindowHandler(this);
		camera = new Camera(0, 0);
		this.addKeyListener(new KeyInput(handler));

		level = loader.loadImage("/level_" + currentLevel + ".png");
		level_width = level.getWidth();
		level_height = level.getHeight();
		
		sprite_sheet_tiles = loader.loadImage("/sprite_sheets/sprite_sheet_tiles.png");// tiles sprite_sheet 
		sprite_sheet_mob = loader.loadImage("/sprite_sheets/sprite_sheet_mobs.png"); // mob sprite_sheet
		sprite_sheet_entity = loader.loadImage("/sprite_sheets/sprite_sheet_entities.png");// tiles sprite_sheet 
		sprite_sheet_effects = loader.loadImage("/sprite_sheets/sprite_sheet_effects.png"); // mob sprite_sheet

		ssTile = new SpriteSheet(sprite_sheet_tiles);
		ssMob = new SpriteSheet(sprite_sheet_mob);
		ssEntity = new SpriteSheet(sprite_sheet_entity);
		ssEffect = new SpriteSheet(sprite_sheet_effects);

		grass1 = ssTile.grabImage(7, 1, 32, 32);
		grass2 = ssTile.grabImage(1, 2, 32, 32);
		grass3 = ssTile.grabImage(1, 3, 32, 32);
		grass4 = ssTile.grabImage(1, 4, 32, 32);
		grass5 = ssTile.grabImage(1, 5, 32, 32);
		fogHorizontal = ssTile.grabImage(2, 32, 64, 32);
		fogVerticle = ssTile.grabImage(1, 16, 32, 64);
		

		this.addMouseListener(new MouseInput(handler, camera, this, ssEntity));
		
		player = new NetPlayer(0, 0, handler, ID.PLAYER, ssMob, 100, JOptionPane.showInputDialog(this, "Enter a name"), null, -1,this);
		
		if(player != null) {
			PlayerInfo pinfo = new PlayerInfo(player, (int)camera.getX(), (int)camera.getY(), null, ssEffect, this);
			handler.addGUI(pinfo);
		}	
		
		loadLevel(level);
		
			
			Packet00Join joinPacket = new Packet00Join(player.getUsername(), player.x, player.y);

			
			if(socketS != null) {
				socketS.addConnection(player, joinPacket);
			}
			//socketC.sendData("ping".getBytes());
			joinPacket.writeData(socketC);
		
		

	}

	private void start() {
		running = true;
		
		if(JOptionPane.showConfirmDialog(this, "Do you want to run the server?") == 0) {
			socketS = new GameServer(this);
			socketS.start();
		}
		
		socketC = new GameClient(this, "localhost");
		socketC.start();
		new Thread(this).start();
		
	}

	private void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tick() {
		if(player != null)
			camera.tick(player); //move camera boy
		handler.tick();
		getNumEnemies();
		if (levelComplete)
			currentLevel++;
		levelComplete = false;

	}

	public synchronized void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		////////////////////////////////////

		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g2d.scale(SCALE, SCALE);
		if (level != null) { // once the level is found, add a tint
			g2d.translate(-camera.getX(), -camera.getY());
		}

		// TODO uncomment when sprite sheet complete
	
	
		for (int xx = -32; xx < 32 * (level_width + 1); xx += 32) {
			for (int yy = -32; yy < 32 * (level_height + 1); yy += 32) { //draw floor tiles and fog
			/*	if(((xx/32)*(yy/32) + 1) % 7 == 0)
					g.drawImage(grass1, xx, yy,null);
				else if(((xx/32)*(yy/32) + 1) % 6 == 0)
					g.drawImage(grass5, xx, yy,null);
				else if(((xx/32)*(yy/32) + 1) % 5 == 0)
					g.drawImage(grass3, xx, yy,null);
				else if(((xx/32)*(yy/32) + 1) % 4 == 0)
					g.drawImage(grass4, xx, yy,null);
				else {
					g.drawImage(grass2, xx, yy,null);
				}
				*/
				if(camera != null) {
				if(camera.getX() < xx+32 && camera.getX()+WIDTH > xx && camera.getY() < yy+32 && camera.getY()+HEIGHT > yy) {

				if(xx == -32) {
					g.drawImage(fogHorizontal, xx+32, yy,-64,32,null);
				}
				else if(yy == -32) {
					g.drawImage(fogVerticle, xx, yy-28,32,64,null);
				}
				else if(xx == 32 * (level_width) && yy != 32 * (level_height)) {
					g.drawImage(fogHorizontal, xx, yy,64, 32,null);
				}
				else if(xx != 32 * (level_width) && yy == 32 * (level_height)) {
					g.drawImage(fogVerticle, xx, yy+64,32,-64,null);
				}
				else if(xx > 0 && yy > 0 && xx < level_width * 32 && yy < level_height * 32){
				g.drawImage(grass1, xx, yy,null);
				}
				}
			}}
		}
		
		if(handler != null)
			handler.render(g);

		if (level != null) { // once the level is found, add a tint
		
			g.setColor(new Color(0, 0, 70, 15));
			g.fillRect(0, 0, level.getWidth() * 32, level.getHeight() * 32);
			handler.renderGUI(g);
			g2d.translate(camera.getX(), camera.getY());
		
		}
		
		

		
		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		//game loop
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}

			frames++;
			render();
			if (System.currentTimeMillis() - timer > 1000) {
				
				timer += 1000;
				frame.setTitle(Integer.toString(frames));
				frames = 0;
			}

		}
		stop();
	}

	// level loader

	private void loadLevel(BufferedImage image) { //reads the colours of the level.png, loads different objects
		int w = image.getWidth();
		int h = image.getHeight();
		Random r1 = new Random();
		for (int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				int fogX = r1.nextInt(w * 32);
				int fogY = r1.nextInt(h * 32);
				if (red == 255 && blue == 0 && green == 0)
					handler.addTile(new CobblestoneWall(xx * 32, yy * 32, ID.SOLIDTILE, ssTile, handler));
				else if (red == 255 && blue == 255 && green == 0)
					handler.addTile(new BrickWall(xx * 32, yy * 32, ID.SOLIDTILE, ssTile, handler, this));
				else if (blue == 255 && green == 0) {
					player.setX(xx*32);
					player.setY(yy*32);
					handler.addMob(player);
					}
				else if (green == 255 && blue == 0) {
					//Packet03MobMovement mobspawnpacket = new Packet03MobMovement(new Skeleton(xx * 32, yy * 32, ID.ENEMY, ssMob, handler, hp), xx*32, yy*32);
					handler.addMob(new Skeleton(xx * 32, yy * 32, ID.ENEMY, ssMob, handler, hp, this));
				}
				else if (blue == 255 && green == 255)
					handler.addEntity(new Crate(xx * 32, yy * 32, ID.CRATE, ssEntity));
				else if (r1.nextInt(2) == 1) {
					handler.addEffect(new Fog(fogX, fogY, ID.FOG, ssEffect, r1.nextInt(100) + 40, 0));
				}
				

			}
		}
		
	}
	
	
	private void getNumEnemies() {
		if (!checkLoopDone) {
			numOfEnemies = 0;
			for (int i = 0; i < handler.mob.size(); i++) {
				Mob tempMob = handler.mob.get(i);
				if (tempMob.getId() == ID.ENEMY) {
					numOfEnemies++;
					/*
					 * TODO Once multiple levels are added, make new constant variables for evey
					 * level
					 */
					if (currentLevel == 1) {
						if (numOfEnemies >= LEVEL_1_NUM_OF_ENEMIES)
							checkLoopDone = true;
					} else if (currentLevel == 2) {
						if (numOfEnemies >= LEVEL_2_NUM_OF_ENEMIES)
							checkLoopDone = true;
					}
				}
			}
		}
	}

	private static void levelComplete() {
		if (numOfEnemies <= 0)
			levelComplete = true;
		else
			levelComplete = false;
	}

	public static void main(String[] args) {
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				levelComplete();
			}
		}, 5, 1, TimeUnit.SECONDS);
		new Main();
	}

}

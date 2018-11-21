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
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import squaredungeon.gameObjects.CobblestoneWall;
import squaredungeon.gameObjects.Camera;
import squaredungeon.gameObjects.Crate;
import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Mob;
import squaredungeon.gameObjects.Player;
import squaredungeon.gameObjects.Skeleton;
import squaredungeon.gameObjects.Weapon;
import squaredungeon.gfx.BufferedImageLoader;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.particles.Fog;
import squaredungeon.particles.TorchLight;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	//dimensions of the screen
	public static final int WIDTH = (int) screenSize.getWidth();
	public static final int HEIGHT = (int) screenSize.getHeight();

	private boolean running = false;
	private Thread thread;
	private Handler handler;
	private BufferedImageLoader loader = new BufferedImageLoader();
	public static BufferedImage level = null;
	private BufferedImage sprite_sheet = null;
	private Camera camera;
	private SpriteSheet ss;
	private BufferedImage floor = null;

	private final int LEVEL_1_NUM_OF_ENEMIES = 40;
	private final int LEVEL_2_NUM_OF_ENEMIES = 40;

	public static final float SCALE = 3f;

	public int ammo = 100;
	public int hp = 100;
	public int currentLevel = 1;
	public static int numOfEnemies = 0;
	public static boolean levelComplete = false;
	public static boolean checkLoopDone = false;

	// weapons and other items taht will be initialized in initItems
	public static Weapon pistol;
	public static Weapon smg;
	public static Weapon shotgun;
	public static Weapon rifle;
	public static Weapon sniper;

	public Main() {
		new Game(WIDTH, HEIGHT, "Square Dungeon", this);
		start();

		handler = new Handler();
		camera = new Camera(0, 0);
		this.addKeyListener(new KeyInput(handler));

		level = loader.loadImage("/level_" + currentLevel + ".png");

		sprite_sheet = loader.loadImage("/sprite_sheets/sprite_sheet.png");// TODO add sprite sheet

		ss = new SpriteSheet(sprite_sheet);

		floor = ss.grabImage(7, 1, 32, 32);

		this.addMouseListener(new MouseInput(handler, camera, this, ss));

		loadLevel(level);

	}

	private void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
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
		if (hp <= 0)
			stop();

		for (int i = 0; i < handler.mob.size(); i++) {
			Mob tempMob = handler.mob.get(i);
			if (tempMob.getId() == ID.Player) {
				camera.tick(tempMob);
			}
		}

		handler.tick();
		getNumEnemies();
		if (levelComplete)
			currentLevel++;
		levelComplete = false;
	}

	public void render() {
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

		for (int xx = 0; xx < 30 * 72; xx += 32) {
			for (int yy = 0; yy < 30 * 72; yy += 32) {
				g.drawImage(floor, xx, yy, null);
			}
		}

		handler.render(g);

		if (level != null) { // once the level is found, add a tint
		
			g.setColor(new Color(5, 5, 100, 20));
			g.fillRect(0, 0, level.getWidth() * 32, level.getHeight() * 32);
		}
		g2d.translate(camera.getX(), camera.getY());

		// HUD//

		// Health Bar
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 200, 32);
		g.setColor(Color.GREEN);
		g.fillRect(5, 5, hp * 2, 32);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, 200, 32);

		// Ammo Display

		g.setColor(Color.WHITE);
		// g.drawString("Ammo: " + Weapon.get().getMagSize() + "/" +
		// Weapon.get().getMaxAmmo(), 5, 64);
		////////////////////////////////////
		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println(frames);
				timer += 1000;
				frames = 0;
			}

		}
		stop();
	}

	// level loader

	private void loadLevel(BufferedImage image) {
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
				if (red == 255)
					handler.addTile(new CobblestoneWall(xx * 32, yy * 32, ID.Block, ss, handler));
				if (blue == 255 && green == 0) {
					handler.addMob(new Player(xx * 32, yy * 32, handler, ID.Player, this, ss));
					handler.addEntity(new TorchLight(xx * 32, yy * 32, ID.TorchLight, ss, 150));
				}
				if (green == 255 && blue == 0)
					handler.addMob(new Skeleton(xx * 32, yy * 32, ID.Enemy, ss, handler));
				if (blue == 255 && green == 255)
					handler.addEntity(new Crate(xx * 32, yy * 32, ID.Crate, ss));
				if (r1.nextInt(3) == 1) {
					handler.addEffect(new Fog(fogX, fogY, ID.Fog, ss, r1.nextInt(100) + 40, 0));
				}
			}
		}
	}

	private void getNumEnemies() {
		if (!checkLoopDone) {
			numOfEnemies = 0;
			for (int i = 0; i < handler.mob.size(); i++) {
				Mob tempMob = handler.mob.get(i);
				if (tempMob.getId() == ID.Enemy) {
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
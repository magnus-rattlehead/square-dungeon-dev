package squaredungeon.gameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.Handler;
import squaredungeon.main.Main;

public class NetPlayer extends Player{
	public InetAddress ip;
	public int port;
	private Main main;
	
	public NetPlayer(int x, int y, Handler handler, ID id,SpriteSheet ss, int hp, String name, InetAddress ip, int port, Main main) {
		super(x, y, handler, id, ss, hp, name);
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.main = main;
	}
	public NetPlayer(int x, int y, ID id,SpriteSheet ss, int hp, String name, InetAddress ip, int port) {
		super(x, y, null, id, ss, hp, name);
		
		this.ip = ip;
		this.port = port;
		this.name = name;
	}
	
	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);

	}
	public String getUsername() {
		return name;
	}


}

package squaredungeon.main;

import java.awt.Graphics;
import java.util.ArrayList;

import squaredungeon.gameObjects.*;


public class Handler {
	
	public ArrayList<Entity> entity = new ArrayList<Entity>();
	public ArrayList<Mob> mob = new ArrayList<Mob>();
	public ArrayList<Effect> effect = new ArrayList<Effect>();
	public ArrayList<Tile> tile = new ArrayList<Tile>();
	
	private boolean up = false, down = false, right = false, left = false;

	public synchronized void tick() {



		for (int i = 0; i < effect.size(); i++) {
			Effect tempEffect = effect.get(i);
			tempEffect.tick();
		}
		for (int i = 0; i < mob.size(); i++) {
			Mob tempMob = mob.get(i);
			tempMob.tick();
		}
		for (int i = 0; i < entity.size(); i++) {
			Entity tempEntity = entity.get(i);
			tempEntity.tick();
		}
		for (int i = 0; i < tile.size(); i++) {
			Tile tempTile = tile.get(i);
			tempTile.tick();
		}
	}

	public synchronized void render(Graphics g) {
	

		for (int i = 0; i < mob.size(); i++) {
			Mob tempMob = mob.get(i);
			tempMob.render(g);
		}
		for (int i = 0; i < entity.size(); i++) {
			Entity tempEntity = entity.get(i);
			tempEntity.render(g);
		}
		for (int i = 0; i < tile.size(); i++) {
			Tile tempTile = tile.get(i);
			tempTile.render(g);
		}
		for (int i = 0; i < effect.size(); i++) {
			Effect tempEffect = effect.get(i);
			tempEffect.render(g);
		}
	}


	public synchronized void addMob(Mob tempMob) {
		mob.add(tempMob);
	}

	public synchronized void removeMob(Mob tempMob) {
		mob.remove(tempMob);
	}
	public synchronized void addEntity(Entity tempEntity) {
		entity.add(tempEntity);
	}

	public synchronized void removeEntity(Entity tempEntity) {
		entity.remove(tempEntity);
	}
	public synchronized void addEffect(Effect tempEffect) {
		effect.add(tempEffect);
	}

	public synchronized void removeEffect(Effect tempEffect) {
		effect.remove(tempEffect);
	}
	public synchronized void addTile(Tile tempTile) {
		tile.add(tempTile);
	}

	public synchronized void removeTile(Tile tempTile) {
		tile.remove(tempTile);
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

}
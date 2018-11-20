package squaredungeon;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/*
 * TODO 
 * 1. Inventory management system (including ammo, which will be handled by this class)
 * 2. Shop
 * 3. Coins
 * 4. XP System
 * 
 * How will Weapon Graphics be implemented?
 * 
 * The Idea is that using a weapon will be like terraria. While not shooting, 
 * the weapon is not visible. It is only visible when player is using the weapon. 
 * This makes animation much easier and allows the weapon to be an overlay.
 * 
 * However if graphics are redone (like nuclear throne), it will likely be that the player sprite will be dependent on the current weapon.
 * Will consult with Pablo
 * */


public class Weapon extends GameObject {
	
	public static final ArrayList<Weapon> arrayWeapon = new ArrayList<>();
	
	public static Weapon starting;
	private static Weapon current = null;
	public int price;
	public int level;
	public boolean owns;
	private String name;
	private boolean buyable;
	private int bulletDamage;
	private int bulletVelocity;
	private int magSize;
	private int maxAmmo;
	private int w;
	private int h;
	private boolean isEquipped = false;
	
	public Weapon(int x, int y, ID id, SpriteSheet ss, String name,boolean buyable, int price, int level,int damage, int bv, boolean owns, boolean firstInit, boolean startingWeapon, int magSize, int maxAmmo,int w, int h) {
		super(x, y, id, ss);
		this.name = name;
		this.buyable = buyable;
		this.price = price;
		this.level = level;
		this.bulletDamage = damage;
		this.bulletVelocity = bv;
		this.owns = owns;
		this.magSize = magSize;
		this.w = w;
		this.h = h;
		
		 arrayWeapon.add(this);
		 
		if (firstInit) {
            if (startingWeapon) {//If first init, see if player starts with this or not.
                this.owns = true;
                current = this;
                starting = this;
            } else {
                this.owns = false;
            }
        }
	}

	public int getBulletDamage() {
		return bulletDamage;
	}

	public void setBulletDamage(int bulletDamage) {
		this.bulletDamage = bulletDamage;
	}

	public int getBulletVelocity() {
		return bulletVelocity;
	}

	public void setBulletVelocity(int bulletVelocity) {
		this.bulletVelocity = bulletVelocity;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	public static Weapon get() {
        return current;
    }

    public String getName() {
        return name;
    }

    public boolean owns() {
        return owns;
    }

    public int getMagSize() {
		return magSize;
	}

	public void setMagSize(int magSize) {
		this.magSize = magSize;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public boolean isBuyable() {
        return this.buyable;
    }

	public boolean isPrimary() {
		return true;
	}
	
	public boolean getIsEquipped() {
		return isEquipped;
	}
	
	public void setIsEquipped() {
		isEquipped = true;
	}
	
    static int getIndex(Weapon i) {
        return arrayWeapon.indexOf(i);
    }

    public static void set(Weapon x) {
        current = x;
    }

    public static void set(int i) {
        current = arrayWeapon.get(i);
    }
    
    public void minusOneBullet() {
    	this.magSize--;
    }
}

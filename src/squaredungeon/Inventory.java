package squaredungeon;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Weapon> weapons;
    private final int maxPrimaryWeapons;
    private int currentItem;
    
    
    public Inventory(int maxPrimaryWeapons) {        
        this.maxPrimaryWeapons = maxPrimaryWeapons;
        this.weapons = new ArrayList<Weapon>();
        
        this.currentItem = 0;        
    }
    
    public boolean addItem(Weapon item) {
            if(numberOfPrimaryItems() < this.maxPrimaryWeapons || !item.isPrimary()) {
                this.weapons.add(item);
                return true;
            }             
        return false;
    }

    public boolean hasItem(Weapon item) {
        return getItem(item) != null;
    }
    

    public Weapon getItem(Weapon item) {
        for(Weapon w : weapons) {
            return w;
        }
        return null;
    }
    
    public void clear() {
        this.weapons.clear();
    }
    
    public void removeItem(Weapon item) {
        this.weapons.remove(item);
    }
    
    public Weapon currentItem() {
        return (this.weapons.isEmpty()) ? null :
            this.weapons.get(this.currentItem);
    }
    
    public Weapon nextItem() {
        currentItem += 1;
        if(currentItem >= weapons.size()) {
            currentItem = 0;
        }
        return currentItem();
    }
    
    public Weapon prevItem() {
        currentItem -= 1;
        if( currentItem < 0) {
            currentItem = Math.max(weapons.size() - 1, 0);
        }
        return currentItem();
    }
    

    public Weapon equipItem(Weapon item) {
        for(int i = 0; i < weapons.size(); i++) {
            if(weapons.get(i).equals(item)) {
                currentItem = i;
                break;
            }
        }
        
        return currentItem();
    }
    

    public int numberOfItems() {
        return this.weapons.size();
    }
    
    public int numberOfPrimaryItems() {
        int numberOfPrimary = 0;
        for(int i = 0; i < weapons.size(); i++) {
            if(weapons.get(i).isPrimary()) {
                numberOfPrimary++;
            }
        }
        return numberOfPrimary;
    }
    

    public boolean isPrimaryFull() {
        return numberOfPrimaryItems() >= this.maxPrimaryWeapons;
    }
    

    public List<Weapon> getItems() {
        return weapons;
    }
}

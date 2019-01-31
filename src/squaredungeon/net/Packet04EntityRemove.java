package squaredungeon.net;

import squaredungeon.gameObjects.Entity;
import squaredungeon.gameObjects.Mob;
import squaredungeon.main.Handler;
import squaredungeon.main.Main;

public class Packet04EntityRemove extends Packet{
	
	private String entityID;
	
	public Packet04EntityRemove(byte[] data) {
		super(04);
		String[]dataArray = readData(data).split(",");
		this.entityID = dataArray[0];
		
	}
	
	public Packet04EntityRemove(String entityID) {
		super(04);
		this.entityID = entityID;
	}
	
	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToEveryClient(getData());
	}

	@Override
	public byte[] getData() {
		return ("04" + this.entityID).getBytes();
	}
	

	public String getEntityID() {
		return entityID;
	}
	
	public Entity getEntity() {
		for(Entity e : Main.main.handler.entity) {
			if(e.getEntityID() == entityID) {
			
			return e;
			}
			else {
				return null;
			}
		}
		return null;
		
	}


}
